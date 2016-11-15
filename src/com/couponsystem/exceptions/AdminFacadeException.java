package com.couponsystem.exceptions;

@SuppressWarnings("serial")
public class AdminFacadeException extends Exception {

	public AdminFacadeException(String message) 
	{
		super(message);
	}

	public AdminFacadeException(DAOException e) 
	{
		super(e);
	}

	public AdminFacadeException(String s, DAOException e)
		{
			super(s,e);
		}
}
