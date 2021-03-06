package com.felipesantacruz.productmanager.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableJpaAuditing // Will allow to insert current time stamp at insertions
public class AppConfig
{
	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer()
	{
		return new WebMvcConfigurer()
		{
			@Override
			public void addCorsMappings(CorsRegistry registry)
			{
				// registry.addMapping("/**"); Allow any origin
				registry.addMapping("/api/**")
					.allowedOrigins("http://localhost:8081")
					.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
					.maxAge(3600);
			}
		};
	}
}
