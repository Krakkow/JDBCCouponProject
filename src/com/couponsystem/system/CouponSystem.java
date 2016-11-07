package com.couponsystem.system;

import com.couponsystem.DAO.CompanyDAO;
import com.couponsystem.DAO.CouponDAO;
import com.couponsystem.DAO.CustomerDAO;
import com.couponsystem.dbdaolayer.CompanyDBDAO;
import com.couponsystem.dbdaolayer.CouponDBDAO;
import com.couponsystem.dbdaolayer.CustomerDBDAO;
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
			
			try
				{
			switch (type)
				{
				case ADMIN:
						facade=AdminFacade.getInstance().login(name, password, type);
						break;
					
				case CUSTOMER:
						facade = CustomerFacade.getInstance().login(name, password, type);
						break;
					
				case COMPANY:
						facade = CompanyFacade.getInstance().login(name, password, type);
						break;
				}
			
				}
			catch (Exception e)
				{
					throw new CustomerFacadeException("Could not login " + e.getMessage());
				}
			return facade;
		}
		
		public void shutDown()
		{
			this.task.stopTask();
			System.exit(0);
		}
	}
