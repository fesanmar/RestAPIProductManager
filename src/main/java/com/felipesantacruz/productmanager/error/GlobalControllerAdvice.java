package com.felipesantacruz.productmanager.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.JsonMappingException;

@RestControllerAdvice
public class GlobalControllerAdvice
{
	@ExceptionHandler(ItemNotFoundException.class)
	public ResponseEntity<APIError> handleItemNotFount(ItemNotFoundException e)
	{
		return createAccurateResponse(e, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ JsonMappingException.class, WriteDataNotValidException.class })
	public ResponseEntity<APIError> handleJsonMappingException(RuntimeException e)
	{
		return createAccurateResponse(e, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DatabaseConstraintViolationException.class)
	ResponseEntity<APIError> handleDBContraintViolation(DatabaseConstraintViolationException e)
	{
		return createAccurateResponse(e, HttpStatus.CONFLICT);
	}
	
	private ResponseEntity<APIError> createAccurateResponse(RuntimeException e, HttpStatus status)
	{
		return ResponseEntity
				.status(status)
				.body(new APIError(status, e.getMessage()));
	}
	
}
