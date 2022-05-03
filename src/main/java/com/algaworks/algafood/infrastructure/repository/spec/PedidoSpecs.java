package com.algaworks.algafood.infrastructure.repository.spec;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;

public class PedidoSpecs {

	public static Specification<Pedido> usandoFiltro(PedidoFilter filtro) {
		return (root, query, builder) -> {
			//
			if (Pedido.class.equals(query.getResultType())) {
				// para efetuar uma só consulta, como se fosse um JOIN, agora, usando Criteria
				// Seria o: from Pedido p join fetch p.cliente
				
				root.fetch("restaurante").fetch("cozinha");
				root.fetch("cliente");
			}
			var predicates = new ArrayList<Predicate>();
			
			// adicionar predicates no arrayList
			if (filtro.getClienteId() != null) {
				predicates.add(builder.equal(root.get("cliente"), filtro.getClienteId()));
			}
			
			if (filtro.getRestauranteId() != null) {
				predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));
			}
			
			if (filtro.getDataCriacaoInicio() != null) {
				// Maior ou igual à dataCriacao
				predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"),
						filtro.getDataCriacaoInicio()));
			}
			
			if (filtro.getDataCriacaoFim() != null) {
				// Menor ou igual à dataCriacao
				predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"),
						filtro.getDataCriacaoFim()));	
			}
			
			// Transformar uma collection em um array
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
}
