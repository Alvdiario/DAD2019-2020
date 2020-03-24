package practica1;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;

public class WorkerVerticle extends AbstractVerticle {
	@Override
	public void start(Future<Void> startFuture) {
		/*
		 * vertx.executeBlocking(future -> { try {
		 * 
		 * System.out.println("Antes de dormir"); Thread.sleep(1000);
		 * System.out.println("Despues de dormir");
		 * future.complete("Ejecucion terminada");
		 * 
		 * 
		 * } catch (InterruptedException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); System.out.println(e.getMessage());
		 * future.complete("Ejecucion terminada con excepcion"); }
		 * 
		 * },res->{ System.out.println("El resultado ha sido: "+res.result());
		 * 
		 * });
		 */
		DeploymentOptions deploymentOptions = new DeploymentOptions().setWorker(true);
		vertx.deployVerticle(WorkerVerticle.class.getName(), deploymentOptions, res -> {

			if (res.succeeded()) {

				System.out.println("Código bloqueante ejecutado");

			} else {

				System.out.println("Error en la ejecución");

			}

		});

	}

	@Override

	public void stop(Future<Void> stopFuture) throws Exception {

		vertx.undeploy(WorkerVerticle.class.getName());

		super.stop(stopFuture);
	}
}
