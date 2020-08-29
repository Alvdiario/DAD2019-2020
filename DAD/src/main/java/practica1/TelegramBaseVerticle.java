package practica1;

import org.schors.vertx.telegram.bot.LongPollingReceiver;
import org.schors.vertx.telegram.bot.TelegramBot;
import org.schors.vertx.telegram.bot.TelegramOptions;
import org.schors.vertx.telegram.bot.api.methods.SendLocation;
import org.schors.vertx.telegram.bot.api.methods.SendMessage;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import kt.schors.vertx.telegram.bot.api.types.Update;

public class TelegramBaseVerticle extends AbstractVerticle{
	
	private TelegramBot bot;
	private Integer ejex;
	private String localizacion;
	@Override
	public void start(Promise<Void> future) {
		TelegramOptions telegramOptions = new TelegramOptions()
				.setBotName("Findtomotor_bot")
				.setBotToken("908697753:AAHqjdFUsLFZY5GOzcrz9IaSoZmkzV2gAHM");
		bot = TelegramBot.create(vertx, telegramOptions)
				.receiver(new LongPollingReceiver().onUpdate(handler -> {
					if (handler.getMessage().getText().toLowerCase().contains("hola")) {
						bot.sendMessage(new SendMessage()
								.setText("Hola " + handler.getMessage().getFrom().getFirstName() + " ¿en qué puedo ayudarte?")
								.setChatId(handler.getMessage().getChatId()));
					} else if (handler.getMessage().getText().toLowerCase().contains("inclinacion")) {
						WebClient client = WebClient.create(vertx);
						client.get(8081, "localhost", 
								"/api/mpu6050/1")
							.send(ar -> {
								if (ar.succeeded()) {
									
									HttpResponse<Buffer> response = ar.result();
									System.out.println(response);
									JsonArray list= response.bodyAsJsonArray();
									JsonObject sensor=list.getJsonObject(list.size()-1);
									System.out.println("\n"+sensor);
									System.out.println(sensor);
									ejex= sensor.getInteger("a_value_x");
									
									bot.sendMessage(new SendMessage()
											.setText("La inclinacion actual en la moto es de " + ejex + " grados")
											.setChatId(handler.getMessage().getChatId()));
								}else{
									bot.sendMessage(new SendMessage()
											.setText("Vaya, algo ha salido mal")
											.setChatId(handler.getMessage().getChatId()));
								}
							});
						
					}else if (handler.getMessage().getText().toLowerCase().contains("localizacion")) {
						WebClient client = WebClient.create(vertx);
						client.get(8081, "localhost", 
								"/api/sensor/values/gps/2")
							.send(ar -> {
								if (ar.succeeded()) {
									
									HttpResponse<Buffer> response = ar.result();
									System.out.println(response);
									JsonArray list= response.bodyAsJsonArray();
									JsonObject sensor1=list.getJsonObject(list.size()-1);
									
									System.out.println(sensor1);
									 localizacion = (String) sensor1.getValue("localizacion");
									 String []partes=localizacion.split(",");
										String Latitud=partes[3]+partes[4];
										String Longitud=partes[5]+partes[6];
									bot.sendMessage(new SendMessage()
											.setText("La localizacion actual de la moto es " +"Latitud: "+Latitud + " Longitud " +Longitud)
											.setChatId(handler.getMessage().getChatId()));

									
									
										
								}else{
									bot.sendMessage(new SendMessage()
											.setText("Vaya, algo ha salido mal")
											.setChatId(handler.getMessage().getChatId()));
								}
							});
						
					}
					
					
				
					
					
				}));
		
		bot.start();
	}

	
	
}
