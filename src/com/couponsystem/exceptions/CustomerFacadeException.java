package com.couponsystem.exceptions;

@SuppressWarnings("serial")
public class CustomerFacadeException extends Exception 
{

	public CustomerFacadeException(String message) 
	{
		super(message);
	}

	public CustomerFacadeException(DAOException e)
		{
			super(e);
		}
	
}
