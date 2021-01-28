package com.felipesantacruz.productmanager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class Category
{
	@ApiModelProperty(value = "Category's ID", dataType = "long", example = "1", position = 1)
	@Id
	@GeneratedValue
	private Long id;
	@ApiModelProperty(value = "Category's name", dataType = "string", example = "Books", position = 2)
	private String name;
}
