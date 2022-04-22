package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Restaurante toDomainObject(RestauranteInput restauranteInput) {
		return modelMapper.map(restauranteInput, Restaurante.class);
	}
	
	public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
		// Para não ocorrer erro. Ao colocar new Cozinha() isso não será gerenciado pelo JPA, 
		// assim, será possível fazer a atualização de Cozinha
		// Para eveitar org.hibernate.HibernateException: identifier of an instance of
		// com.algaworks.algafood.domain.model.Cozinha was altered 1 to 2
		restaurante.setCozinha(new Cozinha());
		
		if (restaurante.getEndereco() != null) {
			restaurante.getEndereco().setCidade(new Cidade());
		}
		// Irá obter o restaurante que for passado e passar as informações para o RestauranteInput
		modelMapper.map(restauranteInput, restaurante);
	} 
}
