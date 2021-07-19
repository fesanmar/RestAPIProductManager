package com.felipesantacruz.productmanager.security.jwt;

import static com.felipesantacruz.productmanager.security.jwt.JwtTokenProvider.TOKEN_PREFIX;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.felipesantacruz.productmanager.user.model.UserEntity;
import com.felipesantacruz.productmanager.user.service.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter
{
	private final JwtTokenProvider tokenProvider;
	private final CustomUserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		try
		{
			getJwtFromRequest(request)
				.ifPresent(token -> saveAuthenticationAtSecurityContext(token, request));
		}
		catch (Exception ex)
		{
			log.info("Authentication fails at setting user at security context.");
		}
		
		filterChain.doFilter(request, response);
	}

	private Optional<String> getJwtFromRequest(HttpServletRequest request)
	{
		String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX))
			return Optional.of(bearerToken.substring(TOKEN_PREFIX.length(), bearerToken.length()));
		else
			return Optional.empty();
	}
	
	private void saveAuthenticationAtSecurityContext(String token, HttpServletRequest request)
	{
		if (tokenProvider.validateToken(token))
		{
			Long userId = tokenProvider.getUserIdFromJWT(token);
			UserEntity userEntity = (UserEntity) userDetailsService.loadUserById(userId);
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(userEntity, userEntity.getRoles(), userEntity.getAuthorities());
			// Sets some other details, as remote address or session id
			authenticationToken.setDetails(new WebAuthenticationDetails(request));
			
			// Saves the authentication into the security context
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
	}
}
