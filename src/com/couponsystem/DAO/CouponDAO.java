package com.couponsystem.DAO;

import java.sql.Date;
import java.util.Collection;

import com.couponsystem.beans.Coupon;
import com.couponsystem.exceptions.DAOException;

public interface CouponDAO extends GenericDaoClass<Coupon>
{
	
	public void create(Coupon t) throws DAOException; 

	public Coupon read(long t) throws DAOException; 

	public boolean update(long couponId, Date endDate, double price) throws DAOException; 

	public void delete(long t) throws DAOException; 

	public Collection<Coupon> getAll() throws DAOException; 

	public abstract Collection<Coupon> getCouponsByType(String couponType) throws DAOException;

	public void deleteCouponsOfCompanyFromCoupon(long companyId) throws DAOException;

	public boolean updateCouponAmount(long id) throws DAOException;

	public void deleteCouponFromCompanyCoupon(long couponId) throws DAOException;

	public void deleteCouponFromCustomerCoupon(long couponId) throws DAOException;

}
