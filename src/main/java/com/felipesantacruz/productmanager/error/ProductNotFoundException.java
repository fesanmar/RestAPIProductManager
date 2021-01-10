package com.felipesantacruz.productmanager.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException
{
	private static final long serialVersionUID = 4115791469465294349L;
	
	public ProductNotFoundException(Long id)
	{
		super("Couldn't find any product with id: " + id);
	}
}
