package com.algaworks.algafood.infrastructure.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.algaworks.algafood.domain.repository.CustomJpaRepository;

public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID>
	implements CustomJpaRepository<T, ID>{

		private EntityManager manager;
	
	// CONSTRUTOR 
	public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager); // REPASSA PARA A CLASSE PAI
		this.manager = entityManager;
	}

	@Override
	public Optional<T> buscarPrimeiro() {
		var jpql = "from " + getDomainClass().getName();
		T entity = manager.createQuery(jpql, getDomainClass())
			.setMaxResults(1)
			.getSingleResult();
		return Optional.ofNullable(entity); // Pode ser um Optional com valor null
	}

	@Override
	public void detach(T entity) {
		manager.detach(entity);
		
	}

}
