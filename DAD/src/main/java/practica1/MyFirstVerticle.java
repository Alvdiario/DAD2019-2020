package practica1;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;

public class MyFirstVerticle extends AbstractVerticle {
	@SuppressWarnings("deprecation")
	@Override

	public void start(Future<Void> startFuture) {
//		vertx.createHttpServer().requestHandler(r -> {
//			r.response().end("<h1>Bienvenidoa mi primeraaplicacionVert.x3</h1>"
//					+ "Alabado sea cristo que de alguna manera permite que esta patata en forma de portatil corra eclipse");
//		}).listen(8088, result -> {
//			if (result.succeeded()) {
//				System.out.println("Pillo sitio!!!");
//			} else {
//				System.out.println("Menudo fail shurmano");
//			}
//		});
		//vertx.deployVerticle(MySencondVerticle.class.getName());
		//vertx.deployVerticle(MyThirdVerticle.class.getName());
		//vertx.deployVerticle(DatabaseVerticle.class.getName());
		vertx.deployVerticle(MqttServerVerticle.class.getName());
		vertx.deployVerticle(MqttClientVerticle.class.getName());
		//vertx.deployVerticle(WorkerVerticle.class.getName());
//		EventBus eventBus = vertx.eventBus();
//		vertx.setPeriodic(4000, action -> {
//			eventBus.send("mensaje p2p", "¿hay alguien ahí?", reply -> {
//				if (reply.succeeded()) {
//					String replyMessage = (String) reply.result().body();
//					//System.out.println("Respuesta:" + replyMessage);
//				} else {
//					//System.out.println("No ha habido respuesta");
//				}
//			});
//		});
//		vertx.setPeriodic(4000, action->{
//			eventBus.publish("mensaje_broadcast", "Esto es un mensaje broadcast");
//		});
	}
}