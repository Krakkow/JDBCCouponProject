package com.couponsystem.facadedao;

import com.couponsystem.exceptions.AdminFacadeException;
import com.couponsystem.exceptions.CompanyFacadeException;
import com.couponsystem.exceptions.CustomerFacadeException;

public interface CouponClientFacade 
{
	public CouponClientFacade login(String username, String password, ClientType clientType) throws AdminFacadeException, CompanyFacadeException, CustomerFacadeException;
}
