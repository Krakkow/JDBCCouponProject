package com.couponsystem.facadedbdao;

import java.util.Collection;

import com.couponsystem.DAO.CompanyDAO;
import com.couponsystem.DAO.CouponDAO;
import com.couponsystem.DAO.CustomerDAO;
import com.couponsystem.beans.Company;
import com.couponsystem.beans.Customer;
import com.couponsystem.dbdaolayer.CompanyDBDAO;
import com.couponsystem.dbdaolayer.CouponDBDAO;
import com.couponsystem.dbdaolayer.CustomerDBDAO;
import com.couponsystem.exceptions.AdminFacadeException;
import com.couponsystem.exceptions.DAOException;
import com.couponsystem.facadedao.ClientType;
import com.couponsystem.facadedao.CouponClientFacade;

public class AdminFacade implements CouponClientFacade
	{
		private CustomerDAO customerDao;
		private CompanyDAO companyDao;
		private CouponDAO couponDao;

		private AdminFacade(boolean validate)
			{
				super();
				if (validate)
					{
						companyDao = new CompanyDBDAO();
						customerDao = new CustomerDBDAO();
						couponDao = new CouponDBDAO();
					}
			}

		@Override
		public CouponClientFacade login(String name, String password, ClientType clientType) throws AdminFacadeException
			{
				// validation from the database via DAO
				if (!name.equals("admin") || !password.equals("1234") || !clientType.equals(ClientType.ADMIN))
					{
						throw new AdminFacadeException("Wrong name or password or client type ");
					}
				System.out.println("Login Successful!");
				return new AdminFacade(true);
			}

		public static AdminFacade getInstance()
			{
				return new AdminFacade(false);
			}

		public void createCompany(Company company) throws AdminFacadeException
			{
				try
					{
						companyDao.create(company);
					}
				catch (DAOException e)
					{
						throw new AdminFacadeException(e);
					}
			}

		public Company getCompany(long id) throws AdminFacadeException
			{
				Company compDetails = null;
				try
					{
						compDetails = companyDao.read(id);
						if (compDetails == null)
							{
								throw new AdminFacadeException(
										"Company was not found. Either it doesn't exist or the ID is invalid"); // TODO fix message
							}
					}
				catch (DAOException e)
					{
						throw new AdminFacadeException(e);
					}
				return compDetails;

			}

		public Company getCompanyName(String companyName) throws AdminFacadeException
			{
				Company compName = null;
				try
					{
						compName= companyDao.getCompanyByName(companyName);
						if (compName == null)
							{
								throw new AdminFacadeException("Company name: " + companyName + " is INCORRECT");

							}
					}
				catch (DAOException e)
					{
						throw new AdminFacadeException("Unable to find the requested company " + e);
					}
				return compName;

			}

		public Collection<Company> getAllCompanies() throws AdminFacadeException
			{
				Collection<Company> allCompanies;
				try
					{
						allCompanies = companyDao.getAll();
					}
				catch (DAOException e)
					{

						throw new AdminFacadeException(e);
					}

				return allCompanies;
			}

		public void updateCompany(Company company) throws AdminFacadeException
			{
				try
					{
						companyDao.update(company);
					}
				catch (DAOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

			}

		public void deleteCompany(long companyId) throws AdminFacadeException
			{
				try
					{
						// deletes the coupons of company that already purchased
						// from customers
						customerDao.removeCouponsOfCompanyFromCouponsOfCustomer(companyId);
						// deletes the coupons from the company itself that
						// located in the join table
						companyDao.deleteCouponsFromCompanyFromJoin(companyId);
						// deletes the coupons that contains the same id of the
						// coupons of company from the coupons table
						couponDao.deleteCouponsOfCompanyFromCoupon(companyId);
						// finally deletes the company itself from the company
						// table
						companyDao.delete(companyId);
					}
				catch (DAOException e)
					{
						throw new AdminFacadeException(e);
					}

			}

		public void createCustomer(Customer customer) throws AdminFacadeException
			{

				try
					{
						customerDao.create(customer);
					}
				catch (DAOException e)
					{
						throw new AdminFacadeException(e);
					}

			}

		public Customer getCustomer(long id) throws AdminFacadeException
			{
				try
					{
						return customerDao.read(id);
					}
				catch (DAOException e)
					{
						throw new AdminFacadeException(e);
					}
			}

		public Collection<Customer> getAllCustomers() throws AdminFacadeException
			{
				try
					{
						return customerDao.getAll();
					}
				catch (DAOException e)
					{
						throw new AdminFacadeException(e);
					}
			}

		public void updateCustomer(Customer customer) throws AdminFacadeException
			{

				try
					{
						customerDao.update(customer);
					}
				catch (DAOException e)
					{
						throw new AdminFacadeException(e);
					}
			}

		public void deleteCustomer(long customerId) throws AdminFacadeException
			{
				try
					{
						// deletes everything from the coupon purchased history
						customerDao.deleteCustomerFromJoin(customerId);
						// deletes customer from the customer DB
						customerDao.delete(customerId);
					}
				catch (DAOException e)
					{
						throw new AdminFacadeException(e);
					}
			}

	}
