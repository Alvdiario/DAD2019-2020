package practica1;

import io.netty.handler.codec.http.HttpContentEncoder.Result;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import types.SensorValue;

public class DatabaseVerticle extends AbstractVerticle {
	MySQLPool mySQLPool;

	@Override
	public void start(Promise<Void> startPromise) {
		MySQLConnectOptions mySQLConnectOptions = new MySQLConnectOptions().setPort(3306).setHost("localhost")
				.setDatabase("dadproyecto").setUser("root").setPassword("Root1234");
		PoolOptions poolOptions = new PoolOptions().setMaxSize(50);
		mySQLPool = MySQLPool.pool(vertx, mySQLConnectOptions, poolOptions);

		Router router = Router.router(vertx);
		vertx.createHttpServer().requestHandler(router::handle).listen(8080, result -> {
			if (result.succeeded()) {
				startPromise.complete();
			} else {
				startPromise.fail(result.cause());
			}

		});
		router.get("/api/sensor/values/:idSensor").handler(this::getValueBySensor);
	}
private void getValueBySensor(RoutingContext routingContext) {
	mySQLPool.query("SELECT * FROM dadproyecto.sensor_valor_mpu6050 WHERE idsensor= "+ routingContext.request().getParam("idsensor"),
			res->{
				 if(res.succeeded()) {
					 RowSet<Row> resultSet = res.result();
					 System.out.println("El némero de elementos obtenidos es "+ resultSet.size());
					 JsonArray result = new JsonArray();
					 for (Row row : resultSet) {
						 result.add(JsonObject.mapFrom(new SensorValue(row.getInteger("idsensor_valor_mpu6050"),
								 row.getInteger("idsensor"),
								 row.getInteger("a_value_x"),
								 row.getInteger("a_value_y"),
								 row.getInteger("a_value_z"),
								 row.getInteger("g_value_x"),
								 row.getInteger("g_value_y"),
								 row.getInteger("g_value_z"),
								 row.getInteger("timestamp")) ));
					 }
					 routingContext.response().setStatusCode(200).putHeader("content-type", "application/json").end(result.encodePrettily());
				 }else {
					 routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
						.end((JsonObject.mapFrom(res.cause()).encodePrettily()));
				 }
		
	});
}
}
