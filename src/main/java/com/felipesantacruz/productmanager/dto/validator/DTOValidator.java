package com.felipesantacruz.productmanager.dto.validator;

public interface DTOValidator<T>
{
	boolean isValid(T dto);
}
