package com.felipesantacruz.productmanager.error;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class APIError
{
	private HttpStatus status;
	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
	private LocalDateTime date;
	private String message;
	
	public static APIError with(HttpStatus s, RuntimeException e)
	{
		return new APIErrorBuilder()
				.date(LocalDateTime.now())
				.message(e.getMessage())
				.status(s)
				.build();
	}
}
