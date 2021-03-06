package com.couponsystem.facadedbdao;

import java.sql.Date;
import java.util.Collection;

import com.couponsystem.DAO.CompanyDAO;
import com.couponsystem.DAO.CouponDAO;
import com.couponsystem.DAO.CustomerDAO;
import com.couponsystem.beans.Company;
import com.couponsystem.beans.Coupon;
import com.couponsystem.dbdaolayer.CompanyDBDAO;
import com.couponsystem.dbdaolayer.CouponDBDAO;
import com.couponsystem.dbdaolayer.CustomerDBDAO;
import com.couponsystem.exceptions.CompanyFacadeException;
import com.couponsystem.exceptions.DAOException;
import com.couponsystem.facadedao.ClientType;
import com.couponsystem.facadedao.CouponClientFacade;

public class CompanyFacade implements CouponClientFacade 
{

	private long companyId;
	private CompanyDAO companyDao;
	private CouponDAO couponDao;
	private CustomerDAO customerDao; 
	
	public long getCompanyId(){
		return companyId;
	}
	private CompanyFacade(boolean validate)
		{
			super();
			if (validate)
				{
					companyDao = new CompanyDBDAO();
					couponDao = new CouponDBDAO();
					customerDao = new CustomerDBDAO();
				}
		}
//fuck you kowalsky
	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) throws CompanyFacadeException
		{
			Company company = null;
			CompanyFacade facade =null;
			try
				{
					company = new CompanyDBDAO().login(name, password);
				}
			catch (DAOException e)
				{
					throw new CompanyFacadeException("Cannot log in please check password or username",e);
				}
			if (!name.equals(company.getCompName()) || !password.equals(company.getPassword()) || !clientType.equals(ClientType.COMPANY))
				{
					throw new CompanyFacadeException("Wrong name or password or client type ");
				}
			System.out.println("Login Successful!");
			 facade=new CompanyFacade(true);
			 facade.setCompanyId(company.getId());
			 return facade;
		}

	private void setCompanyId(long id)
		{
			this.companyId=id;
			
		}

	public static CompanyFacade getInstance()
		{
			return new CompanyFacade(false);
		}

	public void createCoupon(Coupon coupon) throws CompanyFacadeException 
	{
		try
			{
				couponDao.create(coupon);
				System.out.println("***Creating coupon = "+coupon.getId()+" and company = "+getCompanyId());
				companyDao.createCouponToCompany(getCompanyId(),coupon.getId());
			}
		catch (DAOException e)
			{
				throw new CompanyFacadeException(e);
			}
	}

	public Coupon getCoupon(long id) throws CompanyFacadeException 
	{
		Coupon coupDetails = null;
		try
			{
				coupDetails = couponDao.read(id);
			}
		catch (DAOException e)
			{
				throw new CompanyFacadeException(e);
				
			}
		if (coupDetails == null)
			{
				throw new CompanyFacadeException("No such coupon");
			}
		return coupDetails;
	}

	public Collection<Coupon> getAllCoupons() throws CompanyFacadeException 
	{
		//Returns all the coupons from that company
		try
			{
				return companyDao.getAllCouponsOfCompany(companyId);
			}
		catch (DAOException e)
			{
				throw new CompanyFacadeException(e);
			}

	}

	public Collection<Coupon> getCouponsByPrice(double couponPrice) throws CompanyFacadeException 
	{
		try
			{
				return companyDao.getCouponsFromCompanyCouponByPrice(getCompanyId(),couponPrice);
			}
		catch (DAOException e)
			{
				throw new CompanyFacadeException("No coupon were found matching the value you've put in." , e);
			}
	}

	public Collection<Coupon> getCouponByDate (Date endDate) throws CompanyFacadeException
	{
		try
		{
			return companyDao.getCouponByDate(companyId, endDate);
		} catch (DAOException e)
		{
			throw new CompanyFacadeException("No coupon were found matching the value you've put in. " , e);
		}

	}

	public Collection<Coupon> getCouponsByType (Coupon.CouponType couponType) throws CompanyFacadeException
	{
		try
		{
			return companyDao.getCouponsByType(couponType);
		} catch (DAOException e)
		{
			throw new CompanyFacadeException("No coupon were found matching the value you've put in." ,e);
		}
	}

	public void updateCoupon(long couponId, Date endDate, double price) throws CompanyFacadeException
	{
		Coupon couponToUpdate = new Coupon(couponId,null,null,endDate,0,null,null,price,null);
		try
			{
//				couponToUpdate.getId();
//				couponToUpdate.setEndDate(endDate);
//				couponToUpdate.setPrice(price);
			//	System.out.println("coupon to update from CompanyFacade() "+couponToUpdate);
				couponDao.update(couponToUpdate);
			}
		catch (DAOException e)
			{
				throw new CompanyFacadeException(e);
			}

	}

	public void deleteCoupon(Coupon coupon) throws CompanyFacadeException 
	{
		try
			{
				//Delete coupon from the join table
				companyDao.deleteCouponFromCompany(coupon.getId());
				//Delete all the coupons purchased by the customers
				customerDao.deleteCouponFromCustomers(coupon.getId());
				//Finally deletes the coupon from the coupon table
				couponDao.delete(coupon.getId());
			}
		catch (DAOException e)
			{
				throw new CompanyFacadeException(e);
			}

	}



}
