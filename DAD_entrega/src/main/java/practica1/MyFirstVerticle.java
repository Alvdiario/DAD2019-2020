package practica1;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;

public class MyFirstVerticle extends AbstractVerticle {
	@SuppressWarnings("deprecation")
	@Override

	public void start(Future<Void> startFuture) {
//		
	
		vertx.deployVerticle(DatabaseVerticle.class.getName());
		vertx.deployVerticle(TelegramBaseVerticle.class.getName());

	}
}