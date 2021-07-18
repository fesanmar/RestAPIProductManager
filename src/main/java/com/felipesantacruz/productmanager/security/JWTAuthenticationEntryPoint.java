package com.felipesantacruz.productmanager.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.felipesantacruz.productmanager.error.APIError;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint
{
	private final ObjectMapper mapper;
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException
	{
		response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		
		APIError apiError = new APIError(HttpStatus.UNAUTHORIZED, authException.getMessage());
		String strApiError = mapper.writeValueAsString(apiError);
		
		PrintWriter writer = response.getWriter();
		writer.println(strApiError);
	}
}
