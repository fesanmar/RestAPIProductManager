package com.felipesantacruz.productmanager.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WriteOrderRowDto
{
	@ApiModelProperty(value = "Product's id", dataType = "long", example = "1", position = 1)
	private long productId;
	
	@ApiModelProperty(value = "Product's price", dataType = "float", example = "12.36", position = 2)
	private float price;
	
	@ApiModelProperty(value = "Product's quantity", dataType = "int", example = "2", position = 3)
	private int quantity;
}
