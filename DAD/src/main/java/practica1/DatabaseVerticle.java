package practica1;

import io.netty.handler.codec.http.HttpContentEncoder.Result;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.Tuple;
import types.SensorValue;
import types.Usuario;

public class DatabaseVerticle extends AbstractVerticle {
	private MySQLPool mySQLPool;

	@Override
	public void start(Promise<Void> startPromise) {
		MySQLConnectOptions mySQLConnectOptions = new MySQLConnectOptions().setPort(3306).setHost("localhost")
				.setDatabase("dadproyecto").setUser("root").setPassword("Root1234");
		PoolOptions poolOptions = new PoolOptions().setMaxSize(50);
		mySQLPool = MySQLPool.pool(vertx, mySQLConnectOptions, poolOptions);

		Router router = Router.router(vertx);
		vertx.createHttpServer().requestHandler(router::handle).listen(8081, result -> {
			if (result.succeeded()) {
				startPromise.complete();
			} else {
				startPromise.fail(result.cause());
			}

		});

//TODO		router.get("/api/sensor/values/dispositivo/:idsensor").handler(this::getDispositivo);
		// TODO
		// router.get("/api/sensor/values/girac/:idsensor").handler(this::getAcelerGirosc);
		// TODO router.get("/api/sensor/values/gps/:idsensor").handler(this::getGps);
		router.get("/api/sensor/values/mpu6050/:idsensor").handler(this::getValueBySensor);

		router.get("/api/usuario/:idusuario").handler(this::getUsuario);
		//router.get("/api/usuario/full/:idusuario").handler(this::getUsuarioFull);

		// router.get("/api/sensor/values/:idsensor").handler(this::getValueBySensor);
	}

	private void getValueBySensor(RoutingContext routingContext) {
		mySQLPool.query("SELECT * FROM dadproyecto.sensor_valor_mpu6050 WHERE idsensor = "
				+ routingContext.request().getParam("idsensor"), res -> {
					if (res.succeeded()) {
						RowSet<Row> resultSet = res.result();
						System.out.println("El numero de elementos obtenidos es " + resultSet.size());
						JsonArray result = new JsonArray();
						for (Row row : resultSet) {
							result.add(JsonObject.mapFrom(new SensorValue(row.getInteger("idsensor_valor_mpu6050"),
									row.getInteger("idsensor"), row.getInteger("a_value_x"),
									row.getInteger("a_value_y"), row.getInteger("a_value_z"),
									row.getInteger("g_value_x"), row.getInteger("g_value_y"),
									row.getInteger("g_value_z"), row.getLong("timesstamp"))));
						}
						routingContext.response().setStatusCode(200).putHeader("content-type", "application/json")
								.end(result.encodePrettily());
					} else {
						routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
								.end((JsonObject.mapFrom(res.cause()).encodePrettily()));
					}

				});
	}

	private void getUsuario(RoutingContext routingContext) {
		mySQLPool.query(
				"SELECT * FROM dadproyecto.usuario WHERE idusuario = " + routingContext.request().getParam("idusuario"),
				res -> {
					if (res.succeeded()) {
						RowSet<Row> resultSet = res.result();
						System.out.println("El numero de elementos obtenidos es " + resultSet.size());
						JsonArray result = new JsonArray();
						for (Row row : resultSet) {
							result.add(JsonObject.mapFrom(new Usuario(row.getInteger("idusuario"),
									row.getString("nombre"), row.getLong("tlf_usuario"), row.getString("correo_usuario"),
									row.getLong("tlf_emergencia"), row.getString("modelo_moto"))));
						}
						routingContext.response().setStatusCode(200).putHeader("content-type", "application/json")
								.end(result.encodePrettily());
					} else {
						routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
								.end((JsonObject.mapFrom(res.cause()).encodePrettily()));
					}

				});
	}
		/*private void getUsuario(RoutingContext routingContext) {
			mySQLPool.query(
					"SELECT * FROM dadproyecto.usuario WHERE idusuario = " + routingContext.request().getParam("idusuario"),
					res -> {
						if (res.succeeded()) {
							RowSet<Row> resultSet = res.result();
							System.out.println("El numero de elementos obtenidos es " + resultSet.size());
							JsonArray result = new JsonArray();
							for (Row row : resultSet) {
								result.add(JsonObject.mapFrom(new Usuario(row.getInteger("idusuario"),
										row.getString("nombre"), row.getString("telefonoU")/*, row.getString("telefonoU"),
										row.getString("telefonoE"), row.getString("modeloMoto"))));
							}
							routingContext.response().setStatusCode(200).putHeader("content-type", "application/json")
									.end(result.encodePrettily());
						} else {
							routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
									.end((JsonObject.mapFrom(res.cause()).encodePrettily()));
						}

					});
	}*/
	/*
	 * /*private void putValueForSensor(RoutingContext routingContext) { SensorValue
	 * sensorValue = Json.decodeValue(routingContext.getBodyAsString(),
	 * SensorValue.class); mySQLPool.
	 * preparedQuery("INSERT INTO sensor_value (idsensor, value, accuracy, timestamp) VALUES (?,?,?,?)"
	 * , Tuple.of(sensorValue.getIdsensor(), sensorValue.getValue(),
	 * sensorValue.getAccuracy(), sensorValue.getTimestamp()), handler -> { if
	 * (handler.succeeded()) { System.out.println(handler.result().rowCount());
	 * 
	 * long id = handler.result().property(MySQLClient.LAST_INSERTED_ID);
	 * sensorValue.setIdsensor_value((int) id);
	 * 
	 * routingContext.response().setStatusCode(200).putHeader("content-type",
	 * "application/json") .end(JsonObject.mapFrom(sensorValue).encodePrettily()); }
	 * else { System.out.println(handler.cause().toString());
	 * routingContext.response().setStatusCode(401).putHeader("content-type",
	 * "application/json")
	 * .end((JsonObject.mapFrom(handler.cause()).encodePrettily())); } }); }
	 */
}