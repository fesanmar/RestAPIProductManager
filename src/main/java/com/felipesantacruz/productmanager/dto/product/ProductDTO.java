package com.felipesantacruz.productmanager.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductDTO
{	
	private long id;
	private String name;
	private double price;
	private String[] images;
	private String categoryName;
}
