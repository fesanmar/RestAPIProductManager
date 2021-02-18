package com.felipesantacruz.productmanager.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReadOrderRowDto
{
	@ApiModelProperty(value = "Product's name", dataType = "string", example = "Very fat book", position = 1)
	private String productName;
	
	@ApiModelProperty(value = "Product's price", dataType = "float", example = "12.36", position = 2)
	private float price;
	
	@ApiModelProperty(value = "Product's quantity", dataType = "int", example = "2", position = 3)
	private int quantity;
	
	@ApiModelProperty(value = "Order row subtotal", dataType = "float", example = "24.72", position = 4)
	private float subTotal;
}
