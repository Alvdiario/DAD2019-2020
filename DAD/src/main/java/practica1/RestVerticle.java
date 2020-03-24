package practica1;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import types.Temperature;

public class RestVerticle extends AbstractVerticle {
	@Override
	public void start(Future<Void> startFuture) {
		Router router = Router.router(vertx);
		vertx.createHttpServer().requestHandler(router::accept).listen(8090, result -> {

		});
		router.route("/api/elements").handler(BodyHandler.create());
		router.get("/api/elements/temperature").handler(this::getTemperature);
		router.post("/api/elements/temperature").handler(this::getTemperature);
		router.get("/api/elements/humidity").handler(this::getHumidity);
		router.post("/api/elements/humidity").handler(this::getHumidity);
	}

	public void getTemperature(RoutingContext routingContext) {

		Temperature temperature = new Temperature();
		temperature.setValue(37.8f);
		temperature.setAccuracy(8);
		temperature.setLocation("Kitchen");
		routingContext.response().setStatusCode(200).putHeader("content-type", "applicaction/json")
				.end(Json.encodePrettily(temperature));

	}

	public void getHumidity(RoutingContext routingContext) {

		Temperature temperature = new Temperature();
		temperature.setValue(37.8f);
		temperature.setAccuracy(8);
		temperature.setLocation("Kitchen");
		routingContext.response().setStatusCode(200).putHeader("content-type", "applicaction/json")
				.end(Json.encodePrettily(temperature));

	}
}
