package com.algaworks.algafood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries{

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired @Lazy
	private RestauranteRepository restauranteRepository;
	
	@Override
	public List<Restaurante> find(String nome, 
			BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		
		// CRITERIABUILDER: CONSTROI ELEMENTOS QUE PRECISAMOS PARA FAZER A CONSULTA
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		// CRITERIAQUERIE: INTERFACE RESPONSAVEL POR MONTAR A ESTRUTURA DE UMA QUERY
		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
		Root<Restaurante> root = criteria.from(Restaurante.class); // from Restaurante (root) - jpql
		
		var predicates = new ArrayList<Predicate>();
		
		if(StringUtils.hasText(nome)) {
			predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
		}
		
		if (taxaFreteInicial != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
		}
		
		if (taxaFreteFinal != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
		}
		
		criteria.where(predicates.toArray(new Predicate[0]));
		
		TypedQuery<Restaurante> query = manager.createQuery(criteria);
		return query.getResultList();
	}

//	@Override
//	public List<Restaurante> findComFreteGratis(String nome) {
//		return restauranteRepository.findAll(comFreteGratis().and(comNomeSemelhante(nome)));
//	}
//	
	
}
