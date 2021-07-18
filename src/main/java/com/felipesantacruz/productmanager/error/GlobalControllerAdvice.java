package com.felipesantacruz.productmanager.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.felipesantacruz.productmanager.error.exception.DatabaseConstraintViolationException;
import com.felipesantacruz.productmanager.error.exception.ItemNotFoundException;
import com.felipesantacruz.productmanager.error.exception.WriteDataNotValidException;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler
{
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request)
	{
		APIError apiError = new APIError(status, ex.getMessage());
		return ResponseEntity
				.status(status)
				.headers(headers)
				.body(apiError);
	}
	
	@ExceptionHandler(ItemNotFoundException.class)
	public ResponseEntity<APIError> handleItemNotFount(ItemNotFoundException e)
	{
		return createAccurateResponse(e, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(WriteDataNotValidException.class)
	public ResponseEntity<APIError> handleDataNotValidException(WriteDataNotValidException e)
	{
		return createAccurateResponse(e, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DatabaseConstraintViolationException.class)
	public ResponseEntity<APIError> handleDBContraintViolation(DatabaseConstraintViolationException e)
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
