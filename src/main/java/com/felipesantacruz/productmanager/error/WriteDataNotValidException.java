package com.felipesantacruz.productmanager.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WriteDataNotValidException extends RuntimeException
{
	private static final long serialVersionUID = -2875080728415139630L;

	public WriteDataNotValidException(String itemName)
	{
		super(String.format("%s parameters can't be null or invalid", itemName));
	}
}
