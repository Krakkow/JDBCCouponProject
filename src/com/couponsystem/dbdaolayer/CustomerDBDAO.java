package com.couponsystem.dbdaolayer;

import java.util.Collection;
import java.util.List;

import com.coupnsystem.db.DataBaseHandler;
import com.couponsystem.DAO.CustomerDAO;
import com.couponsystem.beans.Coupon;
import com.couponsystem.beans.Customer;
import com.couponsystem.beans.Coupon.CouponType;
import com.couponsystem.exceptions.DAOException;
import com.couponsystem.exceptions.ErrorType;

@SuppressWarnings("unchecked")
public class CustomerDBDAO implements CustomerDAO
	{

		public CustomerDBDAO()
			{
				super();
			}

		@Override
		public void create(Customer customer) throws DAOException
			{

				String addCustomer = "INSERT INTO Customer (cust_name, password) VALUES (?,?)";
				String MessageAction = new Object()
					{
					}.getClass().getEnclosingMethod().getName();
				DataBaseHandler.actionOnDataBase(addCustomer, MessageAction, customer.getCustName(),customer.getPassword());
			}

		@Override
		public Customer read(long id) throws DAOException

			{
				String getCustomer = "SELECT * From Customer WHERE id =? ";
				String MessageAction = new Object()
					{
					}.getClass().getEnclosingMethod().getName();
				List<Customer> customers = (List<Customer>) DataBaseHandler.readInDataBase(getCustomer, MessageAction,
						"Customer", id);
				return customers.get(0);
			}

		@Override
		public boolean update(Customer customer) throws DAOException
			{

				String updateCustomer = "UPDATE Company SET password=? WHERE id=?";
				String MessageAction = new Object()
					{
					}.getClass().getEnclosingMethod().getName();
				boolean didUpdateCustomer = DataBaseHandler.actionOnDataBase(updateCustomer, MessageAction,
						customer.getPassword(), customer.getId());
				return didUpdateCustomer;
			}
			
		/*
		 * this method deletes the customer from customer table
		 * (non-Javadoc)
		 * @see com.couponsystem.DAO.CustomerDAO#delete(long)
		 */
		@Override
		public void delete(long customerID) throws DAOException
			{

				String deletCustomerById = "DELETE FROM Customer WHERE id= ?";
				String MessageAction = new Object(){}.getClass().getEnclosingMethod().getName();
				boolean company = DataBaseHandler.actionOnDataBase(deletCustomerById, MessageAction, customerID);
				if (company)
					MessageAction = "successfully deleted";
				System.out.println(MessageAction);
			}

		public Collection<Customer> getAll() throws DAOException
			{
				String getAllCustomer = "SELECT * FROM Customer";
				String messageAction = ErrorType.UNABLE_TO_GET_CUSTOMERS + " ";
				List<Customer> allCustomers = (List<Customer>) DataBaseHandler.readInDataBase(getAllCustomer,
						messageAction, "Customer");
				return allCustomers;
			}

		public List<Coupon> getCoupons(long id) throws DAOException
			{
				String getCouponsId = "SELECT coup_id FROM Company_Coupon WHERE id=?";
				String MessageAction = new Object(){}.getClass().getEnclosingMethod().getName();
				List<Coupon> coupons = (List<Coupon>) DataBaseHandler.readInDataBase(getCouponsId, MessageAction,"Coupon", id);
				return (List<Coupon>) coupons.get(0);
			}

		@Override
		public void purchaseCouponToCustomer(long couponID, long customerID) throws DAOException
			{
				String purchaseCoupon = "INSERT INTO Customer_coupon (cust_id, coup_id) " + "VALUES(?,?) ";
				DataBaseHandler.actionOnDataBase(purchaseCoupon, "Coupon", customerID, couponID);

			}

		@Override
		public void unPurchaseCouponToCustomer(long couponID, long customerID) throws DAOException
			{
				String unPurchaseCoupon = "DELETE FROM Customer_coupon WHERE cust_id = " + customerID + " and coup_id= "
						+ couponID;
				DataBaseHandler.actionOnDataBase(unPurchaseCoupon, "Coupon", customerID, couponID);

			}

		@Override
		public void deleteCustomerFromJoin(long customerID) throws DAOException
			{
				String deleteCustomerFromCustomerCouponTable = "DELETE FROM Customer_coupon WHERE cust_id = ?";
				DataBaseHandler.actionOnDataBase(deleteCustomerFromCustomerCouponTable, "Customer", customerID);
			}

		@Override
		public void deleteCouponFromCustomers(long couponId) throws DAOException
			{
				String deleteCouponFromCustomers = "DELETE FROM Customer_coupon WHERE coup_id=?";
				String MessageAction = new Object(){}.getClass().getEnclosingMethod().getName();
				DataBaseHandler.actionOnDataBase(deleteCouponFromCustomers, MessageAction, couponId);
				
			}
		
		/**
		 * <ul>
		 * <li>The method removes all the coupons of specific company from all
		 * customers in the DB
		 * <li>Using String prepared statement and executing with connection
		 * more on
		 * </ul>
		 * 
		 * @param companyId
		 *            id to remove coupons
		 * @see DataBaseDBDAO
		 * @see CustomerDAO
		 * @see Customer
		 * @throws DAOException
		 *             DAOException
		 * @since version 1.00
		 */
		@Override
		public void removeCouponsOfCompanyFromCouponsOfCustomer(long companyId) throws DAOException
			{
				String removeCompanySQLstatement = "DELETE FROM Customer_coupon WHERE "
						+ "Customer_coupon.coup_id IN ( SELECT coup_id FROM Company_coupon WHERE "
						+ "Company_coupon.comp_id=?)";
				String MassageAction = ErrorType.UNABLE_TO_REMOVE_COUPON + " : Coupons of Company id : " + companyId
						+ " from all customers that has been purchased";
				DataBaseHandler.actionOnDataBase(removeCompanySQLstatement, MassageAction, companyId);
			}

		public Customer login(String cust_name, String password) throws DAOException
			{
				
				String login = "SELECT * FROM Customer WHERE cust_name=? AND password=?";
				List<Customer> customers = (List<Customer>) DataBaseHandler.readInDataBase(login, "Logging in", "Customer",cust_name, password);
				return customers.get(0);
			}

		@Override
		public Collection<Coupon> getAllCouponsOfCustomer(long customerId) throws DAOException
			{
				String getAllCouponCustomerSQLstatement = "SELECT * FROM Coupon WHERE  "
		                + "Coupon.id IN ( SELECT coup_id FROM Customer_coupons WHERE "
		                + "Customer_coupons.cust_id=?)";
				String MessageAction = new Object(){}.getClass().getEnclosingMethod().getName();
				return (Collection<Coupon>) DataBaseHandler.readInDataBase(getAllCouponCustomerSQLstatement, MessageAction, "Customer", customerId);
			}

		@Override
		public Collection<Coupon> getAllCouponsByType(long customerId, CouponType type) throws DAOException
			{
				String MessageAction = new Object(){}.getClass().getEnclosingMethod().getName();
				String getAllCouponCompaniesByTypeSQLstatement = "SELECT * FROM Coupons WHERE  "
		                + "Coupons.id IN ( SELECT coup_id FROM Customer_coupons WHERE "
		                + "Customer_coupons.cust_id=?) AND TYPE=?";
				return (Collection<Coupon>) DataBaseHandler.readInDataBase(getAllCouponCompaniesByTypeSQLstatement, MessageAction, "Coupon", customerId, type);
			}

		@Override
		public Collection<Coupon> getAllCouponsByPrice(long customerId, double price) throws DAOException
			{
				String MessageAction = new Object(){}.getClass().getEnclosingMethod().getName();
				String getAllCouponCompaniesByPriceSQLstatement = "SELECT * FROM Coupons WHERE  "
		                + "Coupons.id IN ( SELECT coup_id FROM Customer_coupons WHERE "
		                + "Customer_coupons.id=?) AND price <=?";
				return (Collection<Coupon>) DataBaseHandler.readInDataBase(getAllCouponCompaniesByPriceSQLstatement, MessageAction, "Coupon", customerId, price);
				
			}

	}
