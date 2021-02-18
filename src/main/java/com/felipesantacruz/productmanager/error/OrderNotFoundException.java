package com.felipesantacruz.productmanager.error;

public class OrderNotFoundException extends ItemNotFoundException
{
	private static final long serialVersionUID = -4292837982767928499L;

	public OrderNotFoundException(Long id)
	{
		super("order", id);
	}

}
