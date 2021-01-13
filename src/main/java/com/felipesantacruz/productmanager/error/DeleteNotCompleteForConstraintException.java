package com.felipesantacruz.productmanager.error;

public class DeleteNotCompleteForConstraintException extends DatabaseConstraintViolationException
{
	private static final long serialVersionUID = -7881548312379506329L;
	
	public DeleteNotCompleteForConstraintException()
	{
		super("Item deletion could'n be completed due Foreign Key contraint.");
	}

}
