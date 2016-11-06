package com.couponsystem.DAO;

import java.util.Collection;

import com.couponsystem.beans.Company;
import com.couponsystem.beans.Coupon;
import com.couponsystem.exceptions.DAOException;

public interface CompanyDAO extends GenericDaoClass<Company>
	{

		void create(Company t) throws DAOException;

		Company read(long t) throws DAOException;

		boolean update(Company t) throws DAOException;

		void delete(long t) throws DAOException;

		Collection<Company> getAll() throws DAOException;

		Coupon getCoupon(long id) throws DAOException;

		Company login(String comp_name, String password) throws DAOException;

		void deleteCouponsFromCompanyFromJoin(long companyId) throws DAOException;

		void unPurchaseCouponToCompany(long companyID, long couponID) throws DAOException;

		void deleteCompanyFromJoin(long companyID) throws DAOException;

		void createCouponToCompany(long companyID, long couponID) throws DAOException;

		void deleteCouponFromCompany(long id) throws DAOException;

		Collection<Coupon> getCouponsFromCompanyCouponByPrice(long companyId, double price) throws DAOException;

		Collection<Coupon> getAllCouponsOfCompany(long companyId) throws DAOException;

		Company getCompanyByName(String company) throws DAOException; 
	}

// public abstract void createCompany(Company company);
//
// public abstract Company readCompany(long id);
//
// public abstract void updateCompany(Company company);
//
// public abstract void deleteCompany(Company company);
//
// public abstract Collection<Company> getAllCompanies();
//
//