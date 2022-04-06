package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
<<<<<<< HEAD
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
=======
>>>>>>> 5600908b08ba7e91a3770db2efe8d91ab0b13fa7
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {

	private static final String MSG_CIDADE_NAO_ENCONTRADA = "Não existe cadastro de cidade com código %d";
<<<<<<< HEAD
=======
	private static final String MSG_ESTADO_NAO_ENCONTRADO = "Não existe um cadastro de estado com código %d";
>>>>>>> 5600908b08ba7e91a3770db2efe8d91ab0b13fa7
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public List<Cidade> listar() {
		return cidadeRepository.findAll();
	}
	
	public Cidade buscar(Long cidadeId) {
		return cidadeRepository.findById(cidadeId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(
						String.format(MSG_CIDADE_NAO_ENCONTRADA, cidadeId)));
	}
	
	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		Estado estado = estadoRepository.findById(estadoId)
<<<<<<< HEAD
				.orElseThrow(() -> new EstadoNaoEncontradoException(estadoId));
=======
				.orElseThrow(() -> new EntidadeNaoEncontradaException(
						String.format(MSG_ESTADO_NAO_ENCONTRADO, estadoId)));
>>>>>>> 5600908b08ba7e91a3770db2efe8d91ab0b13fa7
		cidade.setEstado(estado);
		return cidadeRepository.save(cidade);
	}
	
<<<<<<< HEAD
	public Cidade atualizar(Cidade cidade, Long cidadeId) {		
		try {
			Cidade cidadeAtual = buscar(cidadeId);
			BeanUtils.copyProperties(cidade, cidadeAtual, "id");
			Long estadoId = cidadeAtual.getEstado().getId();
			Estado estado = estadoRepository.findById(estadoId)
					.orElseThrow(() -> new EstadoNaoEncontradoException(estadoId));
			cidade.setEstado(estado);
			cidadeAtual.setEstado(estado);
			return cidadeRepository.save(cidadeAtual);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
=======
	public Cidade atualizar(Cidade cidade, Long cidadeId) {
		Cidade cidadeAtual = buscar(cidadeId);
		BeanUtils.copyProperties(cidade, cidadeAtual, "id");
		
		try {
			Long estadoId = cidadeAtual.getEstado().getId();
			Estado estado = estadoRepository.findById(estadoId)
					.orElseThrow(() -> new EntidadeNaoEncontradaException(
							String.format(MSG_ESTADO_NAO_ENCONTRADO, estadoId)));
			cidade.setEstado(estado);
			cidadeAtual.setEstado(estado);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
		return cidadeRepository.save(cidadeAtual);
>>>>>>> 5600908b08ba7e91a3770db2efe8d91ab0b13fa7
	}
	
	public void excluir(Long cidadeId) {
		try {
			cidadeRepository.deleteById(cidadeId);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format(MSG_CIDADE_NAO_ENCONTRADA, cidadeId));
		}
	}
}
