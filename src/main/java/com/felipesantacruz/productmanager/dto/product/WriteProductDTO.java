package com.felipesantacruz.productmanager.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WriteProductDTO
{
	private String name;
	private float price;
	private long  categoryId;
}
