package com.couponsystem.DAO;

import java.util.Collection;

import com.couponsystem.beans.Coupon;
import com.couponsystem.beans.Customer;
import com.couponsystem.beans.Coupon.CouponType;
import com.couponsystem.exceptions.DAOException;

public abstract interface CustomerDAO extends GenericDaoClass<Customer>
	{

		public void create(Customer t) throws DAOException;

		@Override
		public Customer read(long t) throws DAOException;

		@Override
		public boolean update(Customer t) throws DAOException;

		@Override
		public void delete(long customerID) throws DAOException;

		public void purchaseCouponToCustomer(long couponID, long customerID) throws DAOException;
		
		public void unPurchaseCouponToCustomer(long couponID, long customerID) throws DAOException;
		
		public void deleteCustomerFromJoin(long customerID) throws DAOException;

		@Override
		public Collection<Customer> getAll() throws DAOException;

		public Collection<Coupon> getCoupons(long id) throws DAOException;

		public Customer login(String cust_name, String password) throws DAOException;

		void removeCouponsOfCompanyFromCouponsOfCustomer(long companyId) throws DAOException;

		public void deleteCouponFromCustomers(long customerId) throws DAOException;

		public Collection<Coupon> getAllCouponsOfCustomer(long customerId) throws DAOException;

		public Collection<Coupon> getAllCouponsByType(long customerId, CouponType type)throws DAOException;

		public Collection<Coupon> getAllCouponsByPrice(long customerId, double price) throws DAOException;

	}
