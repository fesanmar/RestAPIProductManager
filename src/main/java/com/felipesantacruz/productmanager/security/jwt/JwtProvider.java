package com.felipesantacruz.productmanager.security.jwt;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.felipesantacruz.productmanager.user.model.UserEntity;
import com.felipesantacruz.productmanager.user.model.UserRole;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.java.Log;

@Log
@Component
public class JwtProvider
{
	public static final String TOKEN_HEADER = "Authorization"; 
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String TOKEN_TYPE = "JWT";
	
	@Value("${jwt.secret:EnUnLugarDeLaManchaDeCuyoNombreNoQuieroAcordarmeNoHaMuchoTiempoQueViviaUnHidalgo}")
	private String jwtSecret;
	
	@Value("${jwt.token-expiration:86400}")
	private int jwtDurationTokenInSeconds;
	
	public String generateToken(Authentication authentication)
	{
		UserEntity userEntity = (UserEntity) authentication.getPrincipal();
		Date tokenExpirationDate = new Date(System.currentTimeMillis() + (jwtDurationTokenInSeconds * 1000));
		return Jwts.builder()
				.signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
				.setHeaderParam("typ", TOKEN_TYPE)
				.setSubject(Long.toString(userEntity.getId()))
				.setIssuedAt(new Date())
				.setExpiration(tokenExpirationDate)
				.claim("fullname", userEntity.getFullName())
				.claim("roles", getUserRolesAsString(userEntity))
				.compact();
	}

	private String getUserRolesAsString(UserEntity userEntity)
	{
		return userEntity.getRoles().stream()
									.map(UserRole::name)
									.collect(Collectors.joining(", "));
	}
	
	public Long getUserIdFromJWT(String token)
	{
		Claims claims = Jwts.parser()
							.setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
							.parseClaimsJws(token)
							.getBody();
		return Long.parseLong(claims.getSubject());
	}
	
	public boolean validateToken(String authToken)
	{
		try
		{
			Jwts.parser()
				.setSigningKey(jwtSecret.getBytes())
				.parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex)
		{
			log.info("JWT sinature error: " + ex.getMessage());
		} catch (MalformedJwtException ex)
		{
			log.info("Malformed jwt: " + ex.getMessage());
		} catch (ExpiredJwtException ex)
		{
			log.info("jwt expired: " + ex.getMessage());
		} catch (UnsupportedJwtException ex)
		{
			log.info("Unsupported jwt: " + ex.getMessage());
		} catch (IllegalArgumentException ex)
		{
			log.info("jwt is void");
		}
		return false;
	}
}
