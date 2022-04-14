package com.algaworks.algafood;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AlgafoodApiIT {

	@LocalServerPort
	private int port;
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		given()
		.basePath("/cozinhas")
		.port(port)
		.accept(ContentType.JSON)
		.when()
		.get()
		.then()
		.statusCode(HttpStatus.OK.value());
	}
}
