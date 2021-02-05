package com.felipesantacruz.productmanager.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.felipesantacruz.productmanager.dto.view.ProductViews;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO
{
	@ApiModelProperty(value = "Product's ID", dataType = "long", example = "1", position = 1)
	@JsonView(ProductViews.Dto.class)
	private long id;
	
	@ApiModelProperty(value = "Product's name", dataType = "string", example = "Very fat book", position = 2)
	@JsonView(ProductViews.Dto.class)
	private String name;
	
	@ApiModelProperty(value = "Product's price", dataType = "float", example = "12.36", position = 3)
	@JsonView(ProductViews.DtoWithPrice.class)
	private double price;
	
	@ApiModelProperty(value = "Product's image", dataType = "string[]", example = "[\"http://www.mydomain.com/files/12345-image.jpg\"]", position = 4)
	@JsonView(ProductViews.Dto.class)
	private String[] images;
	
	@ApiModelProperty(value = "Product's category name", dataType = "string", position = 5, example = "Books")
	@JsonView(ProductViews.Dto.class)
	private String categoryName;
}
