package com.coupnsystem.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TableCreation
	{
		private static String dbName = "CouponSystemDB";
		private static Connection con = null;
		private static String dbUrl = "jdbc:derby://localhost:1527/" + dbName + ";create=true";

		public static void createTables()
			{
				boolean Company = true;
				boolean Customer = true;
				boolean Coupon = true;
				boolean Customer_coupon = true;
				boolean Company_coupon = true;
				try
					{
						Connection con = DriverManager.getConnection(dbUrl);
						Statement stmt = con.createStatement();
						String sqlCommand;

						if (Company)
							{
								sqlCommand = "CREATE TABLE COMPANY "
										+ "(id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY"
										+ "(START WITH 1, INCREMENT BY 1)," + " comp_name VARCHAR(25) UNIQUE NOT NULL, "
										+ " password VARCHAR(25) NOT NULL, " + " email VARCHAR(30) NOT NULL)";
								stmt.execute(sqlCommand);
								System.out.println("success: " + sqlCommand);
							}
						if (Customer)
							{
								sqlCommand = "CREATE TABLE CUSTOMER (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), cust_name VARCHAR (25) NOT NULL UNIQUE, password VARCHAR (25))";
								stmt.execute(sqlCommand);
								System.out.println("success: " + sqlCommand);
							}
						if (Coupon)
							{
								sqlCommand = "CREATE TABLE COUPON (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), coup_title VARCHAR (50) NOT NULL UNIQUE,"
										+ " start_date DATE, end_date DATE, amount INTEGER, type VARCHAR (25), message VARCHAR (60),"
										+ " price DOUBLE, image VARCHAR (50))";
								stmt.execute(sqlCommand);
								System.out.println("success: " + sqlCommand);
							}
						if (Customer_coupon)
							{
								sqlCommand = "CREATE TABLE CUSTOMER_COUPON (cust_id BIGINT , coup_id BIGINT, PRIMARY KEY(cust_id, coup_id))";
								stmt.execute(sqlCommand);
								System.out.println("success: " + sqlCommand);
							}
						if (Company_coupon)
							{
								sqlCommand = "CREATE TABLE COMPANY_COUPON (comp_id BIGINT, coup_id BIGINT, PRIMARY KEY(comp_id, coup_id))";
								stmt.execute(sqlCommand);
								System.out.println("success: " + sqlCommand);
							}
					}
				catch (SQLException e)
					{
						System.out.println("Could not create tables." + e.getMessage());
					}

				finally
					{
						try
							{
								if (con != null)
									{
										con.close();
									}
							}
						catch (SQLException e)
							{
								System.out.println("Could not close connection" + e.getMessage());
							}
					}
			}
	}
