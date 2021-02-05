package com.felipesantacruz.productmanager.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryDTO
{
	@ApiModelProperty(value = "Category's name", dataType = "string", example = "Books", position = 1)
	private String name;
}
