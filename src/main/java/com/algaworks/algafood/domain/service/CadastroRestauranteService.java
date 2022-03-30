package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public List<Restaurante> listar() {
		return restauranteRepository.findAll();
	}
	
	public Restaurante buscar(Long restauranteId) {
		Optional<Restaurante> restaurante = restauranteRepository.findById(restauranteId);
		if(restaurante.isEmpty()) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de restaurante com código %d", restauranteId));
		}
		return restaurante.get();
	}
	
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(
						String.format("Não existe cadastro de cozinha com código %d", cozinhaId)));
		
		restaurante.setCozinha(cozinha);
		return restauranteRepository.save(restaurante);
	}
	
	public Restaurante atualizar(Restaurante restaurante, Long restauranteId) {
		Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);
		if (restauranteAtual.isEmpty()) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de restaurante com código %d", restauranteId));
		}
		BeanUtils.copyProperties(restaurante, restauranteAtual.get(), "id", "formaPagamento", "endereco", "dataCadastro");
		Long cozinhaId = restauranteAtual.get().getCozinha().getId();
		Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);
		
		if (cozinha.isEmpty()) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de cozinha com código %d", cozinhaId));
		}
		restauranteAtual.get().setCozinha(cozinha.get());
		return restauranteRepository.save(restauranteAtual.get());
	}
	
}
