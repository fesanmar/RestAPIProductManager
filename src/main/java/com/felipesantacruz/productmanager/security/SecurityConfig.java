package com.felipesantacruz.productmanager.security;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	private static final String USER = "USER";
	private static final String ADMIN = "ADMIN";
	private static final String ORDER_URL = "api/order/**";
	private static final String PRODUCT_URL = "/api/product/**";
	private final AuthenticationEntryPoint authEntryPoint;
	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;
	private final AccessDeniedHandler accessDeniedHandler;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.httpBasic()
			.authenticationEntryPoint(authEntryPoint)
			.and()
			.authorizeRequests()
				.antMatchers(GET, PRODUCT_URL).hasRole(USER)
				.antMatchers(POST, PRODUCT_URL).hasRole(ADMIN)
				.antMatchers(PUT, PRODUCT_URL).hasRole(ADMIN)
				.antMatchers(DELETE, PRODUCT_URL).hasRole(ADMIN)
				.antMatchers(POST, ORDER_URL).hasAnyRole(USER,ADMIN)
				.anyRequest().authenticated()
			.and()
			.csrf().disable()
			.exceptionHandling().accessDeniedHandler(accessDeniedHandler);				
	}
	
	
}
