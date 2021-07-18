package com.felipesantacruz.productmanager.security;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	private static final String USER = "USER";
	private static final String ADMIN = "ADMIN";
	private static final String ORDER_URL = "api/order/**";
	private static final String PRODUCT_URL = "/api/product/**";
	
	private final AuthenticationEntryPoint jwtAuthEntryPoint;
	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.csrf().disable()
			.exceptionHandling().authenticationEntryPoint(jwtAuthEntryPoint)
			.and()
			.addFilterBefore(null, UsernamePasswordAuthenticationFilter.class)
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
				.antMatchers(GET, PRODUCT_URL).hasRole(USER)
				.antMatchers(POST, PRODUCT_URL).hasRole(ADMIN)
				.antMatchers(PUT, PRODUCT_URL).hasRole(ADMIN)
				.antMatchers(DELETE, PRODUCT_URL).hasRole(ADMIN)
				.antMatchers(POST, ORDER_URL).hasAnyRole(USER,ADMIN)
				.anyRequest().authenticated();
	}
	
	// This method will expose our authentication manager as a bean so we can use it at filter's level
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception
	{
		return super.authenticationManager();
	}
	
	
}
