package com.felipesantacruz.productmanager.error;

public class ProductNotFoundException extends ItemNotFoundException
{
	private static final long serialVersionUID = 4115791469465294349L;
	
	public ProductNotFoundException(Long id)
	{
		super("product", id);
	}
}
