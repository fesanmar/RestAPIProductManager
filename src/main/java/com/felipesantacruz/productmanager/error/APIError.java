package com.felipesantacruz.productmanager.error;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter @RequiredArgsConstructor
public class APIError
{
	@NonNull
	private HttpStatus status;
	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
	private LocalDateTime date = LocalDateTime.now();
	@NonNull
	private String message;
}
