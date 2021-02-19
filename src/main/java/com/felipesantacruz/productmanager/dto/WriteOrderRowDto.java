package com.felipesantacruz.productmanager.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WriteOrderRowDto
{
	@ApiModelProperty(value = "Ordered product's id", dataType = "long", example = "1", position = 1)
	private long productId;
	
	@ApiModelProperty(value = "Ordered product's price", dataType = "float", example = "12.36", position = 2)
	private float price;
	
	@ApiModelProperty(value = "Ordered product's quantity", dataType = "int", example = "2", position = 3)
	private int quantity;
}
