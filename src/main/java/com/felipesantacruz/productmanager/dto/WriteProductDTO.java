package com.felipesantacruz.productmanager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WriteProductDTO
{
	private String name;
	private float price;
	private long  categoryId;
}
