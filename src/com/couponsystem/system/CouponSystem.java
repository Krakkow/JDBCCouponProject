package com.couponsystem.system;

import com.couponsystem.DAO.CompanyDAO;
import com.couponsystem.DAO.CouponDAO;
import com.couponsystem.DAO.CustomerDAO;
import com.couponsystem.dbdaolayer.CompanyDBDAO;
import com.couponsystem.dbdaolayer.CouponDBDAO;
import com.couponsystem.dbdaolayer.CustomerDBDAO;
import com.couponsystem.exceptions.AdminFacadeException;
import com.couponsystem.exceptions.CompanyFacadeException;
import com.couponsystem.exceptions.CustomerFacadeException;
import com.couponsystem.facadedao.ClientType;
import com.couponsystem.facadedao.CouponClientFacade;
import com.couponsystem.facadedbdao.AdminFacade;
import com.couponsystem.facadedbdao.CompanyFacade;
import com.couponsystem.facadedbdao.CustomerFacade;

public class CouponSystem
	{
		private static CouponSystem instance;
		private CompanyDAO comapnyDao;
		private CustomerDAO customerDao;
		private CouponDAO couponDao;
		private DailyCouponExpirationTask task;

		private CouponSystem()
			{
				super();
				this.comapnyDao = new CompanyDBDAO();
				this.customerDao = new CustomerDBDAO();
				this.couponDao = new CouponDBDAO();
				this.task = new DailyCouponExpirationTask(5);
				this.task.start();//starts the scheduler 
			}

		public static CouponSystem getInstance()
			{
				if (instance == null)
					{
						synchronized (CouponSystem.class)
							{
								if (instance == null)
									{
										instance = new CouponSystem();
									}
							}
					}
				return instance;
			}
		
		public CouponClientFacade login(String name, String password, ClientType type) throws Exception
		{
			CouponClientFacade facade=null;
			
			switch (type)
				{
				case ADMIN:
				try
					{
						facade=AdminFacade.getInstance().login(name, password, type);
					}
				catch (Exception e)
					{
						throw new AdminFacadeException("Could not login " + e.getMessage());
					}
					break;
					
				case CUSTOMER:
				try
					{
						facade = CustomerFacade.getInstance().login(name, password, type);
					}
				catch (Exception e)
					{
						throw new CustomerFacadeException("Could not login " + e.getMessage());
					}
				break;
					
				case COMPANY:
				try
					{
						facade = CompanyFacade.getInstance().login(name, password, type);
					}
				catch (Exception e)
					{
						throw new CompanyFacadeException("Could not login " + e.getMessage());
					}

					break;
				}
			return facade;
		}
		
		public void shutDown()
		{
		}

	}
