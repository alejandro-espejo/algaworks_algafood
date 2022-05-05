package com.algaworks.algafood.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile>{

	// Datasize: Representa um tamanho de dado
	private DataSize maxSize;
	
	// Recebe o numero especificado na anotação, nesse caso: 5
	@Override
	public void initialize(FileSize constraintAnnotation) {
		// Converte a String para DataSize
		this.maxSize = DataSize.parse(constraintAnnotation.max());
	}
	
	// Implementa a lógica de validação
	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		// Se for null ou menor/igual a 500KB retorna true.
		return value == null || value.getSize() <= this.maxSize.toBytes();
	}

}
