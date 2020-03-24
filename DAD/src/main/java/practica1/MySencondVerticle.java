package practica1;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
public class MySencondVerticle extends AbstractVerticle {
	@Override
	public void start(Future<Void> startFuture) {
		vertx.eventBus().consumer("mensaje p2p",message->{
			String stringMessage=(String) message.body();
			System.out.println("Mensaje recibido"+stringMessage);
			message.reply("Sí, he recibido el mensaje");
		})
	;
		vertx.eventBus().consumer("mensaje p2p",message->{
			String stringMessage=(String) message.body();
			System.out.println("Mensaje broadcast2"+stringMessage);
			
		});
}
}