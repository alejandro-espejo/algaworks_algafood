package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public NegocioException(String mensagem) {
		super(mensagem);
	}
<<<<<<< HEAD
	
	public NegocioException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
=======
>>>>>>> 5600908b08ba7e91a3770db2efe8d91ab0b13fa7
}