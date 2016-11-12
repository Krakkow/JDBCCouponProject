package com.couponsystem.DAO;

import java.sql.Date;
import java.util.Collection;

import com.couponsystem.beans.Coupon;
import com.couponsystem.exceptions.DAOException;

public interface CouponDAO extends GenericDaoClass<Coupon>
{
	
	void create(Coupon t) throws DAOException;

	 Coupon read(long t) throws DAOException;

	 boolean update(Coupon coupon) throws DAOException;

	 void delete(long t) throws DAOException;

	 Collection<Coupon> getAll() throws DAOException;

    Collection<Coupon> getCouponsByType(String couponType) throws DAOException;

	 void deleteCouponsOfCompanyFromCoupon(long companyId) throws DAOException;

	 boolean updateCouponAmount(long id) throws DAOException;

	 void deleteCouponFromCompanyCoupon(long couponId) throws DAOException;

	 void deleteCouponFromCustomerCoupon(long couponId) throws DAOException;

}
