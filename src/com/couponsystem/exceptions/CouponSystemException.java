package com.couponsystem.exceptions;

public class CouponSystemException extends Exception
{
	private static final long serialVersionUID = 1L;

	public CouponSystemException(String message) 
		{
			super(message);
		}

		public CouponSystemException(Throwable throwable)
		{
			super(throwable);

		}
}
