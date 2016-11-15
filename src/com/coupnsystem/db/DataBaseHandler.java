package com.coupnsystem.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;

import com.couponsystem.beans.Company;
import com.couponsystem.beans.Coupon;
import com.couponsystem.beans.Coupon.CouponType;
import com.couponsystem.beans.Customer;
import com.couponsystem.dbdaolayer.CompanyDBDAO;
import com.couponsystem.dbdaolayer.CouponDBDAO;
import com.couponsystem.dbdaolayer.CustomerDBDAO;
import com.couponsystem.exceptions.DAOException;
import com.couponsystem.exceptions.ErrorType;

/**
 * <ul>
 * <li>This class handles with ALL DBDAO requests for getting Beans
 * (Company,Coupon,Customer) using ConnectionPool connections , Prepared
 * statements and ResultSet Most used 2 methods of Getting Or Posting to the
 * database
 * </ul>
 *
 * @see ConnectionPool
 * @see CompanyDBDAO
 * @see CouponDBDAO
 * @see CustomerDBDAO
 * @since version 1.00
 */
public class DataBaseHandler
	{

		/**
		 *
		 * @return always false method to over ride for DBDAO's
		 */
		public boolean isTableCreated()
			{
				return false;
			}

		/**
		 * No argument constructor
		 */
		public DataBaseHandler()
			{
			}

		/**
		 * <ul>
		 * <li>Constructor for initialization of Tables for Each DBDAO
		 * </ul>
		 *
		 * @param init
		 *            value boolean
		 * @param dataBase
		 *            dataBase table
		 * @param createTable
		 *            createTable statement
		 * @param joinTable
		 *            joinTable statement
		 * @param dbDAO
		 *            dbDAO instance
		 * @throws DAOException
		 *             DAOException
		 */
		// public DataBaseHandler(boolean init, String dataBase, String
		// createTable, String joinTable,
		// DataBaseHandler dbDAO) throws Exception {
		// if (init) {
		// try {
		// Connection connection = ConnectionPool.getInstance().getConnection();
		// Statement stmt = connection.createStatement();
		// if (dbDAO.isTableCreated()) {
		// System.out.println("Executing statement...creating table");
		// stmt.executeUpdate(createTable);
		// System.out.println(dataBase + " table created successfully!");
		// if (joinTable != null) {
		// System.out.println("Executing statement...of creating join table");
		// stmt.executeUpdate(joinTable);
		// System.out.println("Join " + dataBase + " table created
		// successfully!");
		// }
		// }
		// stmt.close();
		// ConnectionPool.getInstance().returnConnection(connection);
		// } catch (Exception e) {
		// throw new DAOException(ErrorType.DATABASE_TABLES, e);
		// }
		// }
		// }
		
		/**
		 * <ul>
		 * <li>The Method handle un valid objects to put inside a prepared
		 * statement
		 * </ul>
		 *
		 * @param action
		 *            detail about some action try invoking on database
		 * @param objects
		 *            to check if they are valid if exists
		 * @throws DAOException
		 */
		private static boolean checkNull(String action, Object... objects) throws DAOException
			{
				if (objects != null)
					{
						for (Object object : objects)
							{
								if (object == null)
									{
										throw new DAOException("Inavlid values of " + action);
									}
							}
					}
				return true;
			}

		public static ErrorType getDuplicateError(String client)
			{
				switch (client)
					{
					case "Company":
						return ErrorType.DUPLICATE_COMPANY;
					case "Customer":
						return ErrorType.DUPLICATE_CUSTOMER;
					case "Coupon":
						return ErrorType.DUPLICATE_COUPON;
					default:
						break;
					}
				return ErrorType.UNKNOWN_ERROR;
			}

		/**
		 * <ul>
		 * <li>The method setting a prepared statement from object array options
		 * </ul>
		 * (Long,String,Date,Integer,Double)
		 *
		 * @param objects
		 *            objects to set the prepared statement
		 * @param preparedStatement
		 *            statement to set object values
		 * @throws SQLException
		 * @throws DAOException
		 * @since version 1.00
		 */
		private static PreparedStatement settingPreStmt(PreparedStatement preparedStatement, Object... objects)
				throws DAOException, SQLException
			{
				if (objects != null)
					{
						for (int i = 0; i < objects.length; i++)
							{
								try
									{
										String className = objects[i].getClass().getSimpleName();
										int j = i + 1;
										switch (className)
											{
											case "Long":
												preparedStatement.setLong(j, (Long) objects[i]);
												break;
											case "String":
												preparedStatement.setString(j, (String) objects[i]);
												break;
											case "Date":
												preparedStatement.setDate(j, (Date) objects[i]);
												break;
											case "Integer":
												preparedStatement.setInt(j, (Integer) objects[i]);
												break;
											case "Double":
												preparedStatement.setDouble(j, (Double) objects[i]);
												break;
											default:
												break;
											}
									}
								catch (NullPointerException e)
									{
										throw new DAOException(ErrorType.VALUES_ARE_NOT_VALID, e);
									}

							}
					}
				return preparedStatement;
			}

		/**
		 * <ul>
		 * <li>Post method This Method is using a connection from a
		 * ConnectionPool to get a prepared statement and setting it and finally
		 * executing an update
		 * <li>This method might throw exception if some of constraint are not
		 * valid of the request
		 * </ul>
		 * 
		 * @param action
		 *            action
		 * @param MassageAction
		 *            MassageAction
		 * @param objects
		 *            objects array
		 * @see ConnectionPool
		 * @return successfully or throws exception
		 * @throws DAOException
		 *             DAOException
		 */
		public static boolean actionOnDataBase(String action, String MassageAction, Object... objects)
				throws DAOException
			{
				Connection connection = null;
				try
					{
						connection = ConnectionPool.getInstance().getConnection();
						PreparedStatement preparedStatement = connection.prepareStatement(action);
						preparedStatement = settingPreStmt(preparedStatement, objects);
						preparedStatement.executeUpdate();
						preparedStatement.close();
					}
				catch (DerbySQLIntegrityConstraintViolationException e)
					{
						String message="";
						if(!checkNull(MassageAction, objects)){ message=" with Wrong values in to update! (null values)";}
						throw new DAOException("Unable to Create"+message + getDuplicateError(MassageAction), e);

					}
				catch (Exception e)
					{
						throw new DAOException(MassageAction, e);
					}
				finally
					{
						try
							{
								ConnectionPool.getInstance().returnConnection(connection);
							}
						catch (Exception e)
							{
								throw new DAOException(ErrorType.RETURN_CONNECTION_ERROR, e);
							}
					}
				return true;
			}

		/**
		 * <ul>
		 * <li>Post method This Method is using a connection from a
		 * ConnectionPool to get a prepared statement and setting it, executing
		 * query and gets a result set by the result set it returns a
		 * Collection/List of beans (Company/Coupon/Customer)
		 * objects array is reffering to the question marks at the sql statement (if there are any, if there aren't you can omit it)
		 * </ul>
		 * 
		 * @see ConnectionPool
		 * @param action
		 *            action
		 * @param MassageAction
		 *            MassageAction
		 * @param beanName
		 *            beanName
		 * @param objects
		 *            objects
		 * @return List queryList
		 * @throws DAOException
		 *             DAOException
		 */
		public static List<? extends Object> readInDataBase(String action, String MassageAction, String beanName,
				Object... objects) throws DAOException
			{
				List<Object> query = new ArrayList<>();
				Connection connection = null;
				try
					{
						connection = ConnectionPool.getInstance().getConnection();
						PreparedStatement preparedStatement = connection.prepareStatement(action);
						preparedStatement = settingPreStmt(preparedStatement, objects);
						ResultSet rs = preparedStatement.executeQuery();
						switch (beanName)
							{
							case "Coupon":
								while (rs.next())
									{
										query.add(instanceOfCoupon(rs));
									}
								break;
							case "Company":
								while (rs.next())
									{
										query.add(instanceOfCompany(rs));
									}
								break;
							case "Customer":
								while (rs.next())
									{
										query.add(instanceOfCustomer(rs));
									}
								break;
							case "Long":
								while (rs.next())
									{
										query.add(idOfCoupon(rs));
									}
								break;
							default:
								;
								break;
							}
						preparedStatement.close();
						ConnectionPool.getInstance().returnConnection(connection);

					}
				catch (Exception e)
					{
						throw new DAOException(MassageAction, e);
					}
				if (query.isEmpty())
					{
						query.add(null);
					}
				return query;
			}

		/**
		 * <ul>
		 * <li>This method return a customer from a single result set row
		 * <li>customer.setCoupons Takes a lot of resources Should not be
		 * invoked from customer field the Collection of Coupon only when it is
		 * asked!
		 * </ul>
		 * 
		 * @param rs
		 *            ResultSet instance
		 * @return Customer
		 * @see Customer
		 * @throws DAOException
		 */
		private static Customer instanceOfCustomer(ResultSet rs) throws DAOException
			{
				Customer customer = null;
				try
					{
						customer = new Customer(rs.getLong("id"), rs.getString("cust_name"), rs.getString("password"));
						// ===>>> customer.setCoupons(new CustomerDBDAO().
						// getCouponsByCustomerID(customer.getId()));
						// Takes a lot of resources(Using connection each
						// company created) shouldnt be invoked from company
						// field the Collection of Coupon
					}
				catch (SQLException e)
					{
						throw new DAOException(ErrorType.UNABLE_TO_PROCESS_RESULT_SET_OF_CUSTOMER, e);
					}
				return customer;
			}

		/**
		 * <ul>
		 * <li>This method return a Coupon from a single result set row
		 * </ul>
		 *
		 * @param rs
		 *            ResultSet instance
		 * @return Coupon
		 * @see Coupon
		 * @throws DAOException
		 *             DAOException
		 */
		private static Coupon instanceOfCoupon(ResultSet rs) throws DAOException
			{
				Coupon coupon = null;
				try
					{
						coupon = new Coupon(rs.getLong("id"),rs.getString("coup_title"), rs.getDate("start_date"),
								rs.getDate("end_date"), rs.getInt("amount"), CouponType.valueOf(rs.getString("type")),
								rs.getString("message"), rs.getDouble("price"), rs.getString("image"));
					}
				catch (SQLException e)
					{
						throw new DAOException(ErrorType.UNABLE_TO_PROCESS_RESULT_SET_OF_COUPON, e);
					}
				return coupon;
			}

		/**
		 * <ul>
		 * <li>This method return a Company from a single result set row
		 * <li>company.setCoupons Takes a lot of resources Should not be invoked
		 * from company field the Collection of Coupon only when it is asked!
		 * </ul>
		 * 
		 * @param rs
		 *            ResultSet instance
		 * @return Company
		 * @see Company
		 * @throws DAOException
		 *             DAOException
		 */
		public static Company instanceOfCompany(ResultSet rs) throws Exception
			{
				Company company = null;
				try
					{
						company = new Company(rs.getLong("id"), rs.getString("comp_name"), rs.getString("password"),
								rs.getString("email"));
						// ===>>>company.setCoupons((new
						// CompanyDBDAO().getCouponsByCompanyID(company.getId())));
						// Takes a lot of resources(Using connection each
						// company created) shouldnt be invoked from company
						// field the Collection of Coupon
					}
				catch (SQLException e)
					{
						throw new DAOException(ErrorType.UNABLE_TO_PROCESS_RESULT_SET_OF_COMPANY, e);
					}
				return company;
			}

		/**
		 * <ul>
		 * <li>This method return a Coupon Id from a single result set row
		 * </ul>
		 *
		 * @param rs
		 *            result set
		 * @return long
		 * @see Long
		 * @throws DAOException
		 *             DAOException
		 */
		public static Long idOfCoupon(ResultSet rs) throws Exception
			{
				Long id = 0L;
				try
					{
						id = rs.getLong("coupon_id");
					}
				catch (SQLException e)
					{
						throw new DAOException(ErrorType.UNABLE_TO_PROCESS_RESULT_SET_OF_ID, e);
					}
				return id;
			}

		/**
		 * <ul>
		 * <li>This method login used from an AdminFacade which always
		 * username="admin" password="1234"
		 * </ul>
		 * 
		 * @param username
		 *            username
		 * @param password
		 *            password
		 * @return boolean value
		 * @throws DAOException
		 *             DAOException
		 */
		public boolean login(String username, String password) throws Exception
			{
				return username.equals("admin") && password.equals("1234");
			}

	}
