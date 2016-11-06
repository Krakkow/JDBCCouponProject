package com.couponsystem.exceptions;

public class DAOException extends Exception
	{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DAOException()
		{
			super();
			// TODO Auto-generated constructor stub
		}

	public DAOException(String message, Throwable cause)
		{
			super(message, cause);
			// TODO Auto-generated constructor stub
		}
	public DAOException(ErrorType error, Throwable cause)
		{
			this(error.toString(), cause);
			// TODO Auto-generated constructor stub
		}

	public DAOException(String message)
		{
			super(message);
			// TODO Auto-generated constructor stub
		}

	public DAOException(Throwable cause)
		{
			super(cause);
			// TODO Auto-generated constructor stub
		}
	}
