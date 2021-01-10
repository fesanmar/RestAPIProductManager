package com.felipesantacruz.productmanager.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WriterProductDTONotValidException extends RuntimeException
{
	private static final long serialVersionUID = 4041277767482242176L;

	public WriterProductDTONotValidException()
	{
		super("Product parameter can't be null or invalid");
	}
	
	

}