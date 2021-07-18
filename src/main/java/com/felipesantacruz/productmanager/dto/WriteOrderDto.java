package com.felipesantacruz.productmanager.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WriteOrderDto
{	
	@ApiModelProperty(value = "Order rows", dataType = "WriteOrderRowDto[]" , position = 2)
	private WriteOrderRowDto[] orderRows;
}
