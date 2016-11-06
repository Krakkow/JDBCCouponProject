package com.couponsystem.facadedbdao;

import java.util.Collection;
import java.util.Date;

import com.couponsystem.DAO.CouponDAO;
import com.couponsystem.DAO.CustomerDAO;
import com.couponsystem.beans.Coupon;
import com.couponsystem.beans.Coupon.CouponType;
import com.couponsystem.beans.Customer;
import com.couponsystem.dbdaolayer.CouponDBDAO;
import com.couponsystem.dbdaolayer.CustomerDBDAO;
import com.couponsystem.exceptions.CustomerFacadeException;
import com.couponsystem.exceptions.DAOException;
import com.couponsystem.facadedao.ClientType;
import com.couponsystem.facadedao.CouponClientFacade;

public class CustomerFacade implements CouponClientFacade
	{
		private long customerId;
		private CustomerDAO customerDao;
		private CouponDAO couponDao;

		private CustomerFacade(boolean validate)
			{
				if (validate)
					{
						this.customerDao = new CustomerDBDAO();
						this.couponDao = new CouponDBDAO();
					}
			}

		@Override
		public CouponClientFacade login(String username, String password, ClientType clientType)
				throws CustomerFacadeException
			{
				Customer customer = null;
				CustomerFacade facade = null;

				try
					{
						customer = new CustomerDBDAO().login(username, password);
					}
				catch (DAOException e)
					{
						throw new CustomerFacadeException(e);
					}

				if (customer == null || !clientType.equals(ClientType.CUSTOMER))
					{
						throw new CustomerFacadeException("Invalid Credentials");
					}
				System.out.println("Login Succesfull");
				facade = new CustomerFacade(true).setCustomerId(customerId);
				return facade;
			}

		public static CustomerFacade getInstance()
			{
				return new CustomerFacade(false);
			}

		public void purchaseCoupon(Coupon coupon) throws CustomerFacadeException
			{
				Coupon couponFound;
				try
					{
						couponFound = couponDao.read(coupon.getId());
					}
				catch (DAOException e)
					{
						throw new CustomerFacadeException(e);
					}

				// if coupon does exsist
				if (couponFound == null)
					throw new CustomerFacadeException("coupon doesnt exist");
				// the first condition , cannot purchase more then one of the
				// same coupon
				// Was take care of by the DataBaseHandler of the unique
				// constraint

				// the second condition expiration date
				if (couponFound.getEndDate().before(new Date()))
					{
						throw new CustomerFacadeException("coupon expired");
					}
				// the amount of the coupon must be greater then 0
				if (couponFound.getAmount() <= 0)
					{
						throw new CustomerFacadeException("Coupon out of stock");
					}

				// purchase coupon
				try
					{
						couponDao.updateCouponAmount(coupon.getId());
						customerDao.purchaseCouponToCustomer(coupon.getId(), getCustomerId());
					}
				catch (DAOException e)
					{
						throw new CustomerFacadeException(e);
					}
			}

		private long getCustomerId()
			{
				return customerId;
			}

		public CustomerFacade setCustomerId(long customerId)
			{
				this.customerId = customerId;
				return this;
			}

		public Collection<Coupon> getAllPurchasedCoupons() throws CustomerFacadeException
			{
				try
					{
						return customerDao.getAllCouponsOfCustomer(getCustomerId());
					}
				catch (DAOException e)
					{
						throw new CustomerFacadeException(e);
					}
			}

		public Collection<Coupon> getAllPurchasedCouponsByType(CouponType type) throws CustomerFacadeException
			{
				try
					{
						return customerDao.getAllCouponsByType(getCustomerId(),type);
					}
				catch (DAOException e)
					{
						throw new CustomerFacadeException(e);
					}
			}

		public Collection<Coupon> getAllPurchasedCouponsByPrice(double price) throws CustomerFacadeException
			{
				try
					{
						return customerDao.getAllCouponsByPrice(getCustomerId(), price);
					}
				catch (DAOException e)
					{
						throw new CustomerFacadeException(e);
					}
			}

	}
