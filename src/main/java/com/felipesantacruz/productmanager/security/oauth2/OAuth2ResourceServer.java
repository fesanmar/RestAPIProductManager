package com.felipesantacruz.productmanager.security.oauth2;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
@SuppressWarnings("deprecation")
public class OAuth2ResourceServer extends ResourceServerConfigurerAdapter
{
	
	private static final String USER = "USER";
	private static final String ADMIN = "ADMIN";
	private static final String ORDER_URL = "api/order/**";
	private static final String PRODUCT_URL = "/api/product/**";

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception
	{
		resources.resourceId("oauth2-resource");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception
	{
		http
			.csrf().disable()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.headers().frameOptions().disable()
			.and()
			.authorizeRequests()
				.antMatchers("/h2-console/**").permitAll()
				.antMatchers(GET, PRODUCT_URL).hasRole(USER)
				.antMatchers(POST, PRODUCT_URL).hasRole(ADMIN)
				.antMatchers(PUT, PRODUCT_URL).hasRole(ADMIN)
				.antMatchers(DELETE, PRODUCT_URL).hasRole(ADMIN)
				.antMatchers(POST, ORDER_URL).hasAnyRole(USER,ADMIN)
				.anyRequest().authenticated();
	}
}
