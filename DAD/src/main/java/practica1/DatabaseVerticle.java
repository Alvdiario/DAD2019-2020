package practica1;

import io.netty.handler.codec.http.HttpContentEncoder.Result;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.mysqlclient.MySQLClient;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.Tuple;
import types.Dispositivo;
import types.Gps;
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
		router.route().handler(BodyHandler.create());
		router.route("/api/usuario*").handler(BodyHandler.create());
		router.route("/api/dispositivo*").handler(BodyHandler.create());

		//router.route("").handler(BodyHandler.create());



		vertx.createHttpServer().requestHandler(router::handle).listen(8081, result -> {
			if (result.succeeded()) {
				startPromise.complete();
			} else {
				startPromise.fail(result.cause());
			}

		});
//--------------------------------------------------------------------------------------------------------------------------------------------SENSORES
		router.get("/api/sensor/values/mpu6050/:idsensor").handler(this::getValueBySensor);
//------------------------------------------------------------------------------------------------------------------------------------------------USUARIOS
		router.get("/api/usuario/:idusuario").handler(this::getUsuario);
		router.put("/api/usuario").handler(this::putUsuario);
		router.post("/api/usuario").handler(this::postUsuarioActualiza);
		router.post("/api/usuario/:idusuario").handler(this::postUsuarioActualizaNombre);
		router.delete("/api/usuario/:idusuario").handler(this::deleteUsuario);
//-----------------------------------------------------------------------------------------------------------DISPOSITIVO
		router.get("/api/sensor/values/dispositivo/:iddispositivo").handler(this::getDispositivo);
		router.put("/api/dispositivo").handler(this::putDispositivo);
		router.delete("/api/dispositivo/:iddispositivo").handler(this::deleteDispositivo);

//-----------------------------------------------------------------------------------------------------------gps
        router.get("/api/sensor/values/gps/:idsensor").handler(this::getGps);

		
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
//---------------------------------------------------------------------------------------------------------------------------------gps

private void getGps(RoutingContext routingContext) {
	mySQLPool.query("SELECT * FROM dadproyecto.sensor_valor_gps WHERE idsensor = "+ routingContext.request().getParam("idsensor"),
			res->{
				 if(res.succeeded()) {
					 RowSet<Row> resultSet = res.result();
					 System.out.println("El numero de elementos obtenidos es "+ resultSet.size());
					 JsonArray result = new JsonArray();
					 for (Row row : resultSet) {
						 result.add(JsonObject.mapFrom(new Gps(
								 row.getInteger("idsensor_valor_gps"),
								 row.getInteger("idsensor"),
								 row.getString("value")
								 )));
					 }
					 routingContext.response().setStatusCode(200).putHeader("content-type", "application/json").end(result.encodePrettily());
				 }else {
					 routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
						.end((JsonObject.mapFrom(res.cause()).encodePrettily()));
				 }
		
	});
}
//---------------------------------------------------------------------------------------------------------------------------------DISPOSITIVO

private void getDispositivo(RoutingContext routingContext) {
	mySQLPool.query("SELECT * FROM dadproyecto.dispositivo WHERE iddispositivo = "+ routingContext.request().getParam("iddispositivo"),
			res->{
				 if(res.succeeded()) {
					 RowSet<Row> resultSet = res.result();
					 System.out.println("El numero de elementos obtenidos es "+ resultSet.size());
					 JsonArray result = new JsonArray();
					 for (Row row : resultSet) {
						 result.add(JsonObject.mapFrom(new Dispositivo(
								 row.getInteger("iddispositivo"),
								 row.getString("ip"),
								 row.getString("nombre"),
								 row.getInteger("idusuario"),
								 row.getLong("initialtimestamp")
								 )));
					 }
					 routingContext.response().setStatusCode(200).putHeader("content-type", "application/json").end(result.encodePrettily());
				 }else {
					 routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
						.end((JsonObject.mapFrom(res.cause()).encodePrettily()));
				 }
		
	});
}
private void putDispositivo(RoutingContext routingContext) {
	
	Dispositivo dispositivo = Json.decodeValue(routingContext.getBodyAsString(), Dispositivo.class);
	mySQLPool.preparedQuery("INSERT INTO dadproyecto.dispositivo (iddispositivo,ip,nombre,idusuario,initialtimestamp) VALUES (?,?,?,?,?)",
			Tuple.of(dispositivo.getIddispositivo(), dispositivo.getIp(), dispositivo.getNombre(),dispositivo.getIdusuario(),
					dispositivo.getInitialtimestamp()),
			handler -> {
				if (handler.succeeded()) {
					System.out.println(handler.result().rowCount());
					
				long id = handler.result().property(MySQLClient.LAST_INSERTED_ID);
					dispositivo.setIdusuario( (int)id);
					
					routingContext.response().setStatusCode(200).putHeader("content-type", "application/json")
							.end(JsonObject.mapFrom(dispositivo).encodePrettily());
				} else {
					System.out.println(handler.cause().toString());
					routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
							.end((JsonObject.mapFrom(handler.cause()).encodePrettily()));
				}
			});
}
private void deleteDispositivo(RoutingContext routingContext) {
	mySQLPool.query(
			"DELETE  FROM dadproyecto.dispositivo WHERE iddispositivo = " + routingContext.request().getParam("iddispostivo"),
			res -> {
				if (res.succeeded()) {
					RowSet<Row> resultSet = res.result();
					System.out.println("El numero de elementos obtenidos es " + resultSet.size());
					JsonArray result = new JsonArray();
					for (Row row : resultSet) {
						result.add(
								JsonObject.mapFrom(new Usuario(row.getInteger("idusuario"), row.getString("nombre"),
										row.getLong("tlf_usuario"), row.getString("correo_usuario"),
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
//---------------------------------------------------------------------------------------------------------------------------------USUARIOS


private void getUsuario(RoutingContext routingContext) {
		mySQLPool.query(
				"SELECT * FROM dadproyecto.usuario WHERE idusuario = " + routingContext.request().getParam("idusuario"),
				res -> {
					if (res.succeeded()) {
						RowSet<Row> resultSet = res.result();
						System.out.println("El numero de elementos obtenidos es " + resultSet.size());
						JsonArray result = new JsonArray();
						for (Row row : resultSet) {
							result.add(
									JsonObject.mapFrom(new Usuario(row.getInteger("idusuario"), row.getString("nombre"),
											row.getLong("tlf_usuario"), row.getString("correo_usuario"),
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
	

	private void putUsuario(RoutingContext routingContext) {
		Usuario Usuario = Json.decodeValue(routingContext.getBodyAsString(), Usuario.class);
		mySQLPool.preparedQuery("INSERT INTO dadproyecto.usuario (idusuario,nombre,tlf_usuario,correo_usuario,tlf_emergencia,modelo_moto) VALUES (?,?,?,?,?,?)",
				Tuple.of(Usuario.getIdusuario(), Usuario.getNombre(),Usuario.getTlf_usuario(), Usuario.getCorreo_usuario(),
						Usuario.getTlf_emergencia(),Usuario.getModelo_moto()),
				handler -> {
					if (handler.succeeded()) {
						System.out.println(handler.result().rowCount());

						long id = handler.result().property(MySQLClient.LAST_INSERTED_ID);
						Usuario.setIdusuario((int) id);

						routingContext.response().setStatusCode(200).putHeader("content-type", "application/json")
								.end(JsonObject.mapFrom(Usuario).encodePrettily());
					} else {
						System.out.println(handler.cause().toString());
						routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
								.end((JsonObject.mapFrom(handler.cause()).encodePrettily()));
					}
				});
	}
	/*
	private void postUsuarioActualiza(RoutingContext routingContext) {
		Usuario Usuario = Json.decodeValue(routingContext.getBodyAsString(), Usuario.class);
		mySQLPool.preparedQuery("UPDATE dadproyecto.usuario (idusuario,nombre,tlf_usuario,correo_usuario,tlf_emergencia,modelo_moto) SET (?,?,?,?,?,?)",
				Tuple.of(Usuario.getIdusuario(), Usuario.getNombre(),Usuario.getTlf_usuario(), Usuario.getCorreo_usuario(),
						Usuario.getTlf_emergencia(),Usuario.getModelo_moto()),
				handler -> {
					if (handler.succeeded()) {
						System.out.println(handler.result().rowCount());

						long id = handler.result().property(MySQLClient.LAST_INSERTED_ID);
						Usuario.setIdusuario((int) id);

						routingContext.response().setStatusCode(200).putHeader("content-type", "application/json")
								.end(JsonObject.mapFrom(Usuario).encodePrettily());
					} else {
						System.out.println(handler.cause().toString());
						routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
								.end((JsonObject.mapFrom(handler.cause()).encodePrettily()));
					}
				});
	}
	*/
	
	private void postUsuarioActualiza(RoutingContext routingContext) {
		Usuario Usuario = Json.decodeValue(routingContext.getBodyAsString(), Usuario.class);
		mySQLPool.query("UPDATE dadproyecto.usuario SET idusuario="+Usuario.getIdusuario()+
				",nombre="+Usuario.getNombre()+
				",tlf_usuario="+Usuario.getTlf_usuario()+
				",correo_usuario="+Usuario.getCorreo_usuario()+
				",tlf_emergencia="+Usuario.getTlf_emergencia()
				+",modelo_moto="+Usuario.getModelo_moto(),
				handler -> {
					if (handler.succeeded()) {
						System.out.println(handler.result().rowCount());

						long id = handler.result().property(MySQLClient.LAST_INSERTED_ID);
						Usuario.setIdusuario((int) id);

						routingContext.response().setStatusCode(200).putHeader("content-type", "application/json")
								.end(JsonObject.mapFrom(Usuario).encodePrettily());
					} else {
						System.out.println(handler.cause().toString());
						routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
								.end((JsonObject.mapFrom(handler.cause()).encodePrettily()));
					}
				});
		}
	private void postUsuarioActualizaNombre(RoutingContext routingContext) {
		Usuario Usuario = Json.decodeValue(routingContext.getBodyAsString(), Usuario.class);
		mySQLPool.query("UPDATE dadproyecto.usuario SET nombre = JOSE WHERE idusuario="+ routingContext.request().getParam("idusuario"),
				handler -> {
					if (handler.succeeded()) {
						System.out.println(handler.result().rowCount());

						long id = handler.result().property(MySQLClient.LAST_INSERTED_ID);
						Usuario.setIdusuario((int) id);

						routingContext.response().setStatusCode(200).putHeader("content-type", "application/json")
								.end(JsonObject.mapFrom(Usuario).encodePrettily());
					} else {
						System.out.println(handler.cause().toString());
						routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
								.end((JsonObject.mapFrom(handler.cause()).encodePrettily()));
					}
				});
		}
	//(idusuario,nombre,tlf_usuario,correo_usuario,tlf_emergencia,modelo_moto) SET (?,?,?,?,?,?)",
	//Tuple.of(Usuario.getIdusuario(), Usuario.getNombre(),Usuario.getTlf_usuario(), Usuario.getCorreo_usuario(),
	//Usuario.getTlf_emergencia(),Usuario.getModelo_moto()),
	private void deleteUsuario(RoutingContext routingContext) {
		mySQLPool.query(
				"DELETE  FROM dadproyecto.usuario WHERE idusuario = " + routingContext.request().getParam("idusuario"),
				res -> {
					if (res.succeeded()) {
						RowSet<Row> resultSet = res.result();
						System.out.println("El numero de elementos obtenidos es " + resultSet.size());
						JsonArray result = new JsonArray();
						for (Row row : resultSet) {
							result.add(
									JsonObject.mapFrom(new Usuario(row.getInteger("idusuario"), row.getString("nombre"),
											row.getLong("tlf_usuario"), row.getString("correo_usuario"),
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
	}