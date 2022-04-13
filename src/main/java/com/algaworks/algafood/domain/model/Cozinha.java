package com.algaworks.algafood.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algaworks.algafood.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonRootName("cozinha")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cozinha {

	@NotNull(groups = Groups.CozinhaId.class)
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String nome;
	
//	@Column(name = "observacao")
//	private String descricao;
	
	@JsonIgnore // IGNORAR A PROPRIEDADE POR CONTA DO ERRO DE LOOP DE SERIALIZAÇÃO
	@OneToMany(mappedBy = "cozinha") // UMA COZINHA ESTA EM MUITOS RESTAURANTES. MANY = COLEÇÃO(LIST) - IRÁ MOSTRAR QUAIS RESTAURANTES ESTÃO COM DETERMINADO ID DE COZINHA
	private List<Restaurante> restaurantes = new ArrayList<>();
	
}