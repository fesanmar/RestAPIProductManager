package com.felipesantacruz.productmanager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.felipesantacruz.productmanager.upload.StorageService;

@SpringBootApplication
public class RestApiProductManagerApplication 
{

	public static void main(String[] args) 
	{
		SpringApplication.run(RestApiProductManagerApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner init(StorageService storageService)
	{
		return args ->
		{
			storageService.deleteAll();
			storageService.init();
		};
	}

}
