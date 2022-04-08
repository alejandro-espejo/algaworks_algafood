package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder // Padrão de projeto para construir objeto em uma linguagem mais fluente.
public class Problema {
	
	private LocalDateTime dataHora;
	private String mensagem;
}
