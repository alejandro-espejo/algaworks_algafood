package com.algaworks.algafood.core.squiggly;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.web.RequestSquigglyContextProvider;
import com.github.bohnman.squiggly.web.SquigglyRequestFilter;

@Configuration
public class SquigglyConfig {

	// FilterRegistrationBean - um Filter do servlet
	// Adicionamos um filtro nas requisições HTTP, qualquer requisição, irá passar por aqui
	@Bean
	public FilterRegistrationBean<SquigglyRequestFilter> squigglyRequestFilter(ObjectMapper objectMapper) {
		
		// Por default o campo é fields, mas podemos personalizar
		// Aqui, iremos usar o campos
		Squiggly.init(objectMapper, new RequestSquigglyContextProvider("campos", null));
		
		var urlPatterns = Arrays.asList("/pedidos/*", "/restaurantes/*");

		var filterRegistration = new FilterRegistrationBean<SquigglyRequestFilter>();
		filterRegistration.setFilter(new SquigglyRequestFilter());
		filterRegistration.setOrder(1);
		
		// Método usado para Filtro personalizado somente para alguns endpoints, 
		// necessário passar uma Collection
		filterRegistration.setUrlPatterns(urlPatterns);
		
		return filterRegistration;
	}
}