package com.felipesantacruz.productmanager.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends RuntimeException
{
	private static final long serialVersionUID = -90675355892772025L;
	
	public ItemNotFoundException(String itemName, Long id)
	{
		super(String.format("Couldn't find any %s with id: %d ", itemName, id));
	}
	
	public ItemNotFoundException(String itemsName)
	{
		super(String.format("Couldn't find some %s ids", itemsName));
	}
}
