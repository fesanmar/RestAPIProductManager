package com.felipesantacruz.productmanager.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DatabaseConstraintViolationException extends RuntimeException
{
	private static final long serialVersionUID = -5090190624355230966L;
	
	public DatabaseConstraintViolationException(String msg)
	{
		super(msg);
	}
}
