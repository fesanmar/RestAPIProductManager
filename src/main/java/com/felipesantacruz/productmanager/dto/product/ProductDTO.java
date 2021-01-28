package com.felipesantacruz.productmanager.dto.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO
{
	@ApiModelProperty(value = "Product's ID", dataType = "long", example = "1", position = 1)
	private long id;
	@ApiModelProperty(value = "Product's name", dataType = "string", example = "Very fat book", position = 2)
	private String name;
	@ApiModelProperty(value = "Product's price", dataType = "float", example = "12.36", position = 3)
	private double price;
	@ApiModelProperty(value = "Product's image", dataType = "string[]", example = "[\"http://www.mydomain.com/files/12345-image.jpg\"]", position = 4)
	private String[] images;
	@ApiModelProperty(value = "Product's category name", dataType = "string", position = 5, example = "Books")
	private String categoryName;
}
