package com.felipesantacruz.productmanager.error.exception;

public class CategoryNotFoundException extends ItemNotFoundException
{
	private static final long serialVersionUID = -750177605978161042L;

	public CategoryNotFoundException(Long id)
	{
		super("category", id);
	}

}
