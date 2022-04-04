package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pedido {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private BigDecimal subTotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataCriacao;
	
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataConfirmacao;
	
	@Column(columnDefinition = "datetime")
	private LocalDateTime dataCancelamento;
	
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataEntrega;
	
	private StatusPedido status;
	
	@Embedded
	private Endereco endereco;
	
	@ManyToOne
	@JoinColumn(name = "forma_pagamento_id", nullable = false)
	private FormaPagamento forma_pagamento;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Restaurante restaurante;
	
	@ManyToOne
	@JoinColumn(name = "usuario_cliente_id", nullable = false)
	private Usuario cliente;
	
	@OneToMany(mappedBy = "pedido")
	private List<ItemPedido> itens = new ArrayList<>();
}
