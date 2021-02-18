package com.felipesantacruz.productmanager.error;

public class WriterOrderDTONotValidException extends WriteDataNotValidException
{
	private static final long serialVersionUID = 8188099789307399986L;

	public WriterOrderDTONotValidException()
	{
		super("Order");
	}

}
