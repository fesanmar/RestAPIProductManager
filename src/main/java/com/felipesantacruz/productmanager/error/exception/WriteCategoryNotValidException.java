package com.felipesantacruz.productmanager.error.exception;

public class WriteCategoryNotValidException extends WriteDataNotValidException
{
	private static final long serialVersionUID = -3047726573989682628L;

	public WriteCategoryNotValidException()
	{
		super("Category");
	}

}
