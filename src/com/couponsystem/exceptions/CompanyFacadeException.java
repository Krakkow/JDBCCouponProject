package com.couponsystem.exceptions;

public class CompanyFacadeException extends Exception
	{
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		public CompanyFacadeException(String message) 
			{
				super(message);
			}

			public CompanyFacadeException(DAOException e) 
			{
				super(e);
			}

		public CompanyFacadeException(String s, DAOException e)
			{
				super(s,e);
			}
	}
