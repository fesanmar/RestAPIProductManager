package com.felipesantacruz.productmanager.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EditOrderDto
{
	@ApiModelProperty(value = "Customer's name", dataType = "string", example = "John Doe", position = 1)
	private String customer;
}
