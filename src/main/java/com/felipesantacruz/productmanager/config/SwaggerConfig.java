package com.felipesantacruz.productmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig
{
	@Bean
	public Docket api()
	{
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.felipesantacruz.productmanager.controller"))
				.paths(PathSelectors.any())
				.build().apiInfo(apiInfo());
	}
	
	@Bean
	public ApiInfo apiInfo()
	{
		return new ApiInfoBuilder()
				.title("Product Manager API")
				.description("API for managing the products of a store. Just an excercise with Spring Boot")
				.version("1.0")
				.contact(new Contact(
						"Felipe Santa-Cruz", 
						"https://www.linkedin.com/in/felipe-santa-cruz-mart%C3%ADnez-alcal%C3%A1-74810259/", 
						"fesanmar@gmail.com"))
				.build();
	}
}
