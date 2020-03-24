package practica1;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class MyTestVerticle extends AbstractVerticle {
	@Override
	public void start(Future<Void> startFuture) {
		vertx.executeBlocking(future -> {
			try {
				while (true) {
					System.out.println("Antes de dormir");
					Thread.sleep(1000);
					System.out.println("Despues de dormir");
					future.complete("Ejecucion terminada");
					
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getMessage());
				future.complete("Ejecucion terminada con excepcion");
			}

		}, res -> {
			System.out.println("el resultado ha sido "+res.result().toString());


		});

	}

}