package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nome;

	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;
	
	@JsonIgnore
	@Embedded
	private Endereco endereco;
	
//	@JsonIgnore
	@JsonIgnoreProperties("hibernateLazyInitializer") // IGNORAR UMA PROPRIEDADE DE COZINHA
	@ManyToOne //(fetch = FetchType.LAZY) // MUITOS RESTAURANTES PARA UMA COZINHA
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Cozinha cozinha;
	
	@JsonIgnore
	@CreationTimestamp // Informa que a propriedade deve ser atribuida com a data atual no momento que a entidade for cadastrada.
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataCadastro;
	
	@JsonIgnore
	@UpdateTimestamp // Deve ser atributo a data sempre que a entidade for atualizada.
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataAtualizacao;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER) // CRIA A TABELA DE RELACIONAMENTO DE RESTAURANTE E FORMA_PAGAMENTO
	@JoinTable(name = "restaurante_forma_pagamento",
		joinColumns = @JoinColumn(name = "restaurante_id"), 
		inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private List<FormaPagamento> formasPagamento = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos;
}
