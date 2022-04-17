package com.algaworks.algafood;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties") // Usado para configurar o arquivo de application-test.properties
public class AlgafoodApiIT {

	@LocalServerPort
	private int port;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@BeforeEach
	public void setUp() {
		// Habilita os logs quando há falha no teste
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";

		databaseCleaner.clearTables();
		prepararDados();
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		given().accept(ContentType.JSON).when().get().then().statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveConter4Cozinhas_QuandoConsultarCozinhas() {
		given().accept(ContentType.JSON).when().get().then().body("", hasSize(2)).body("nome",
				hasItems("Indiana", "Tailandesa"));
	}

	@Test
	public void testRetornarStatus201_QuandoCadastrarCozinha() {
		// given().body("{ \"nome\": \"Chinesa\"
		// }").contentType(ContentType.JSON).accept(ContentType.JSON).when().post()
		// .then().statusCode(HttpStatus.CREATED.value());
	}

	private void prepararDados() {
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Tailandesa");
		cozinhaRepository.save(cozinha1);

		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Indiana");
		cozinhaRepository.save(cozinha2);
	}

	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
		// Irá efetuar a busca de cozinha com Id 2 com o nome Indiana e se o status de retorno é 200.
		given().pathParam("cozinhaId", 2).accept(ContentType.JSON).when().get("/{cozinhaId}").then()
		.statusCode(HttpStatus.OK.value()).body("nome", equalTo("Indiana"));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
		// Irá efetuar a busca de cozinha com Id 2 com o nome Indiana e se o status de retorno é 200.
		given().pathParam("cozinhaId", 100).accept(ContentType.JSON).when().get("/{cozinhaId}").then()
		.statusCode(HttpStatus.NOT_FOUND.value());
	}
}
