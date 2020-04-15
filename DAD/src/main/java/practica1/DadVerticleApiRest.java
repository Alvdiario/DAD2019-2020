package practica1;

import java.util.LinkedHashMap;
import java.util.Map;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import types.Gps;

public class DadVerticleApiRest extends AbstractVerticle {

	private Map<Integer, Gps> elements = new LinkedHashMap<>();

	@Override
	public void start(Future<Void> startFuture) {
		createSomeData();
		Router router = Router.router(vertx);
		vertx.createHttpServer().requestHandler(router::accept).listen(8080, result -> {
			if (result.succeeded()) {
				startFuture.complete();
			} else {
				startFuture.fail(result.cause());
			}
		});
		router.route("/api/elements*").handler(BodyHandler.create());
		router.get("/api/elements").handler(this::getAll);
		router.put("/api/elements").handler(this::addOne);
		router.delete("/api/elements").handler(this::deleteOne);
		router.post("/api/elements/:elementid").handler(this::postOne);
	}

	private void createSomeData() {
		Gps temp1 = new Gps(37.8f, 8, "cocina",10);
		elements.put(temp1.getId(), temp1);
		Gps temp2 = new Gps(37.8f, 8, "cocina",10);
		elements.put(temp2.getId(), temp2);
		Gps temp3 = new Gps(37.8f, 8, "cocina",10);
		elements.put(temp3.getId(), temp3);
	}

	private void getAll(RoutingContext routingContext) {
		routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
				.end(Json.encodePrettily(elements.values()));
	}

	private void addOne(RoutingContext routingContext) {
		final Gps element = Json.decodeValue(routingContext.getBodyAsString(), Gps.class);
		elements.put(element.getId(), element);
		routingContext.response().setStatusCode(201).putHeader("content-type", "application/json; charset=utf-8")
				.end(Json.encodePrettily(element));
	}
	
	private void postOne(RoutingContext routingContext) {
		int id = Integer.parseInt(routingContext.request().getParam("elementid"));
		Gps ds = elements.get(id);
		final Gps element = Json.decodeValue(routingContext.getBodyAsString(), Gps.class);
		ds.setValue(element.getValue());
		ds.setTimestamp(element.getTimestamp());
		ds.setAccuracy(element.getAccuracy());
		elements.put(ds.getId(), ds);
		routingContext.response().setStatusCode(201).putHeader("content-type", "application/json; charset=utf-8")
				.end(Json.encode(element));
	}

	private void deleteOne(RoutingContext routingContext) {
		final Gps element = Json.decodeValue(routingContext.getBodyAsString(), Gps.class);
		elements.remove(element.getId());
		routingContext.response().setStatusCode(201).putHeader("content-type", "application/json; charset=utf-8")
				.end(Json.encodePrettily(element));
	}

	@Override
	public void stop(Future<Void> stopFuture) throws Exception {
		super.stop(stopFuture);
	}

}