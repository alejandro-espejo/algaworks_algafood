package com.algaworks.algafood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@JsonInclude(Include.NON_NULL)
@Getter
@Builder // Padrão de projeto para construir objeto em uma linguagem mais fluente.
public class Problem {
	
	private Integer status;
	private String type;
	private String title;
	private String detail;
	
	private String userMessage;
	private OffsetDateTime timestamp;
	private List<Object> objects;
	
	@Getter
	@Builder
	public static class Object {
		private String name;
		private String userMessage;
	}
}
