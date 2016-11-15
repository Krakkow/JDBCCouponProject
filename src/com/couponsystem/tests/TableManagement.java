package com.couponsystem.tests;

import com.coupnsystem.db.TableCreation;

import java.sql.*;

public class TableManagement 
{
	private static String dbName = "CouponSystemDB";
	private static String dbUrl = "jdbc:derby://localhost:1527/" + dbName + ";create=true";

	public static void startDb()
	{

        System.out.println("Creating tables, after the initial drop");
        boolean create = true;
		boolean print = true;
		boolean dropAll = false;
		
		if(create)
		{
			TableCreation.createTables();
		}
		
		if(print)
		{
			print("COMPANY");
			print("CUSTOMER");
			print("COUPON");
			print("CUSTOMER_COUPON");
			print("COMPANY_COUPON");
			
			System.out.println("---==============END==============---");
		}
		
		if(dropAll)
		{
			dropAllTables();
		}
	}

	public static void print(String tableName)
	{

		try
		{
			Connection con = DriverManager.getConnection(dbUrl);
			Statement stmt = con.createStatement();
			String sqlCommand = "SELECT * FROM " + tableName;
			ResultSet rs = stmt.executeQuery(sqlCommand);
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			int lineLength = 0;
			System.out.println("\n" + tableName + " table:");
			
			for (int i = 1; i <= numberOfColumns; i++) 
			{
				String columnName = rsmd.getColumnName(i);
				int colSize = rsmd.getColumnDisplaySize(i);
				lineLength += colSize > columnName.length() ? colSize : columnName.length();
				String pad = "";

				for (int j = 0; j < colSize - columnName.length(); j++) 
				{
					pad += " ";
				}

				System.out.print(columnName + pad + "|");
			}

			System.out.println();

			for (int i = 0; i < lineLength; i++) 
			{
				System.out.print("-");
			}
			System.out.println();

			while (rs.next()) 
			{

				for (int i = 1; i <= numberOfColumns; i++) 
				{
					String colVal = rs.getObject(i).toString();
					int colSize = rsmd.getColumnDisplaySize(i) > rsmd.getColumnName(i).length() ? rsmd
							.getColumnDisplaySize(i) : rsmd.getColumnName(i).length();
					String pad = "";

					for (int j = 0; j < colSize - colVal.length(); j++) 
					{
						pad += " ";
					}

					System.out.print(colVal + pad + "|");
				}

				System.out.println();
			}
		}
		catch (SQLException e) 
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private static void dropAllTables() 
	{
//		String[] tables = { "Company_coupon"};
		String[] tables = { "COMPANY", "CUSTOMER", "COUPON", "CUSTOMER_COUPON", "COMPANY_COUPON"};

		try 
		{
            System.out.println("getting connection from database");
            Connection con = DriverManager.getConnection(dbUrl);
            System.out.println("connection = "+con);
            Statement stmt = con.createStatement();
			String sql;
			
			for (String table : tables) 
			{
				sql = "DROP TABLE " + table;
				stmt.execute(sql);
				System.out.println("Success!" + sql);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

}
