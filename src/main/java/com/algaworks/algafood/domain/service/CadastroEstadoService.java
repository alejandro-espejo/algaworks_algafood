package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	@Autowired
	private EstadoRepository estadoRepository;
	
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}
	
	public void excluir(Long estadoId) {
		try {
			estadoRepository.deleteById(estadoId);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de estado com código %d", estadoId));
		} catch (DataIntegrityViolationException e) {
			String.format("Estado de código %d não pode ser removida, pois está em uso", estadoId);
		}
	}
	
	public List<Estado> listar() {
		return estadoRepository.findAll();
	}
	
	public Estado buscar(Long estadoId) {
		Optional<Estado> estado = estadoRepository.findById(estadoId);
		if (estado.isEmpty()) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de estado com código %d", estadoId));
		}
		return estado.get();
	}
	
	public Estado atualizar(Estado estado, Long estadoId) {
		Optional<Estado> estadoAtual = estadoRepository.findById(estadoId);
		if(estadoAtual.isEmpty()) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de estado com código %d", estadoId));
		}
		BeanUtils.copyProperties(estado, estadoAtual.get(), "id");
		return estadoRepository.save(estadoAtual.get());
	}
}
