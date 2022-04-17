package com.algaworks.algafood;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
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
import com.algaworks.algafood.util.ResourceUtils;

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
	
	private static final int COZINHA_ID_INEXISTENTE  = 100;
	
	private Cozinha cozinhaAmericana;
	private int quantidadeCozinhasCadastradas;
	private String jsonCorretoCozinhaChinesa;

	@BeforeEach
	public void setUp() {
		// Habilita os logs quando há falha no teste
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";

		databaseCleaner.clearTables();
		prepararDados();
		
		jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource(
				"/json/correto/cozinha-chinesa.json");
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		given().accept(ContentType.JSON).when().get().then().statusCode(HttpStatus.OK.value());
	}

//	@Test
//	public void deveConter4Cozinhas_QuandoConsultarCozinhas() {
//		given().accept(ContentType.JSON).when().get().then().body("", hasSize(2)).body("nome",
//				hasItems("Indiana", "Tailandesa"));
//	}

//	@Test
//	public void testRetornarStatus201_QuandoCadastrarCozinha() {
//		// given().body("{ \"nome\": \"Chinesa\"
//		// }").contentType(ContentType.JSON).accept(ContentType.JSON).when().post()
//		// .then().statusCode(HttpStatus.CREATED.value());
//	}

	private void prepararDados() {
		Cozinha cozinhaTailandesa = new Cozinha();
		cozinhaTailandesa.setNome("Tailandesa");
		cozinhaRepository.save(cozinhaTailandesa);

		cozinhaAmericana = new Cozinha();
		cozinhaAmericana.setNome("Indiana");
		cozinhaRepository.save(cozinhaAmericana);
		
		quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();
	}

	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
		// Irá efetuar a busca de cozinha com Id 2 com o nome Indiana e se o status de retorno é 200.
		given().pathParam("cozinhaId", cozinhaAmericana.getId()).accept(ContentType.JSON).when().get("/{cozinhaId}").then()
		.statusCode(HttpStatus.OK.value()).body("nome", equalTo(cozinhaAmericana.getNome()));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
		// Irá efetuar a busca de cozinha com Id 2 com o nome Indiana e se o status de retorno é 200.
		given().pathParam("cozinhaId", COZINHA_ID_INEXISTENTE).accept(ContentType.JSON).when().get("/{cozinhaId}").then()
		.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void deveRetornarQuantidadeCorretaDeCozinhas_QuandoConsultarCozinhas() {
	    given()
	        .accept(ContentType.JSON)
	    .when()
	        .get()
	    .then()
	        .body("", hasSize(quantidadeCozinhasCadastradas));
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinha() {
	    given()
	        .body(jsonCorretoCozinhaChinesa)
	        .contentType(ContentType.JSON)
	        .accept(ContentType.JSON)
	    .when()
	        .post()
	    .then()
	        .statusCode(HttpStatus.CREATED.value());
	}
}
