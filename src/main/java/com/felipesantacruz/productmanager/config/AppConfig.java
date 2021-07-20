package com.felipesantacruz.productmanager.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // Will allow to insert current time stamp at insertions
public class AppConfig
{
	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}
}
