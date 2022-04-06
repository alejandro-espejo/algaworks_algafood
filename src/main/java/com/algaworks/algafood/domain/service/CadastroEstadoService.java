package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
<<<<<<< HEAD
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
=======
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
>>>>>>> 5600908b08ba7e91a3770db2efe8d91ab0b13fa7
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	private static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removida, pois está em uso";
<<<<<<< HEAD
=======
	private static final String MSG_ESTADO_NAO_ENCONTRADO = "Não existe um cadastro de estado com código %d";
>>>>>>> 5600908b08ba7e91a3770db2efe8d91ab0b13fa7
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}
	
	public void excluir(Long estadoId) {
		try {
			estadoRepository.deleteById(estadoId);
		} catch (EmptyResultDataAccessException e) {
<<<<<<< HEAD
			throw new EstadoNaoEncontradoException(estadoId);
=======
			throw new EntidadeNaoEncontradaException(
				String.format(MSG_ESTADO_NAO_ENCONTRADO, estadoId));
>>>>>>> 5600908b08ba7e91a3770db2efe8d91ab0b13fa7
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format(MSG_ESTADO_EM_USO, estadoId));
		}
	}
	
	public List<Estado> listar() {
		return estadoRepository.findAll();
	}
	
	public Estado buscar(Long estadoId) {
		return estadoRepository.findById(estadoId)
<<<<<<< HEAD
			.orElseThrow(() -> new EstadoNaoEncontradoException(estadoId));
=======
			.orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format(MSG_ESTADO_NAO_ENCONTRADO, estadoId)));
>>>>>>> 5600908b08ba7e91a3770db2efe8d91ab0b13fa7
	}
	
	public Estado atualizar(Estado estado, Long estadoId) {
		Estado estadoAtual = buscar(estadoId);
		BeanUtils.copyProperties(estado, estadoAtual, "id");
		return estadoRepository.save(estadoAtual);
	}
}
