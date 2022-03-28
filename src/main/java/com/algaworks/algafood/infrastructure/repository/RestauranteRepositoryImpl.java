package com.algaworks.algafood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Restaurante> find(String nome, 
			BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		
		// CRITERIABUILDER: CONSTROI ELEMENTOS QUE PRECISAMOS PARA FAZER A CONSULTA
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		// CRITERIAQUERIE: INTERFACE RESPONSAVEL POR MONTAR A ESTRUTURA DE UMA QUERY
		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
		Root<Restaurante> root = criteria.from(Restaurante.class); // from Restaurante (root) - jpql
		
		Predicate nomePredicate = builder.like(root.get("nome"), "%"+nome+"%");
		Predicate taxaInicialPredicate = builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial);
		Predicate taxaFinallPredicate = builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal);
		
		// AS TRES CONDÇÕES DEVEM SER VERDADEIRAS, COMO SE FOSSE UM AND NA CONSULTA SQL
		criteria.where(nomePredicate, taxaInicialPredicate, taxaFinallPredicate);
		
		TypedQuery<Restaurante> query = manager.createQuery(criteria);
		return query.getResultList();
	}
}
