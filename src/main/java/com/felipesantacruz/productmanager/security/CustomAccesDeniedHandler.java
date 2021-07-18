package com.felipesantacruz.productmanager.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.felipesantacruz.productmanager.error.APIError;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAccesDeniedHandler implements AccessDeniedHandler
{
	private final ObjectMapper mapper;
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException
	{
		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
		
		APIError apiError = new APIError(HttpStatus.FORBIDDEN, accessDeniedException.getMessage());
		String apiErrorStr = mapper.writeValueAsString(apiError);
		
		PrintWriter writer = response.getWriter();
		writer.println(apiErrorStr);
	}

}
