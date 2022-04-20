package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	public List<Restaurante> listar() {
		return restauranteRepository.findAll();
	}

	public Restaurante buscar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncontradaException(restauranteId));
	}

	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
		restaurante.setCozinha(cozinha);
		return restauranteRepository.save(restaurante);
	}

//	@Transactional
//	public Restaurante atualizar(RestauranteInput restauranteInput, Long restauranteId) {
//		Restaurante restauranteAtual = buscar(restauranteId);
//		// restauranteInputDisassembler.copyToDomainObject(restauranteInput,
//		// restauranteAtual);
//
//		// BeanUtils.copyProperties(restauranteInput, restauranteAtual, "id",
//		// "formaPagamento", "endereco", "dataCadastro", "produtos");
//
//		try {
//			return salvar(restauranteAtual);
//		} catch (EntidadeNaoEncontradaException e) {
//			throw new NegocioException(e.getMessage());
//		}
//	}

	@Transactional
	public void excluir(Long restauranteId) {
		try {
			restauranteRepository.deleteById(restauranteId);
			// Descarrega todas as mudanças pendentes do banco de dados
			restauranteRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncontradaException(restauranteId);
		}
	}
}
