package com.felipesantacruz.productmanager.dto.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WriteProductDTO
{
	@ApiModelProperty(value = "Product's name", dataType = "String", example = "Very fat book", position = 1)
	private String name;
	@ApiModelProperty(value = "Product's price", dataType = "float", example = "12.36", position = 2)
	private float price;
	@ApiModelProperty(value = "Product's category ID", dataType = "long", example = "1", position = 3)
	private long  categoryId;
}
