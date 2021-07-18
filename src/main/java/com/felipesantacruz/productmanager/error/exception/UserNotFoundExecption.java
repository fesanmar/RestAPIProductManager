package com.felipesantacruz.productmanager.error.exception;

public class UserNotFoundExecption extends ItemNotFoundException
{
	private static final long serialVersionUID = -6696732719641268508L;

	public UserNotFoundExecption(Long id)
	{
		super("user", id);
	}

}
