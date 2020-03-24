package practica1;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
public class MyThirdVerticle extends AbstractVerticle {
	@Override
	public void start(Future<Void> startFuture) {
		vertx.eventBus().consumer("mensaje p2p",message->{
			String stringMessage=(String) message.body();
			System.out.println("Mensaje recibido"+stringMessage);
			message.reply("S�, he recibido el mensaje");
		})
	;
		vertx.eventBus().consumer("mensaje p2p",message->{
			String stringMessage=(String) message.body();
			System.out.println("Mensaje broadcast3"+stringMessage);
			
		});
}
}