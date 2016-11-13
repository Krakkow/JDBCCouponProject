package com.coupnsystem.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

public class ConnectionPool 
{
	private LinkedList<Connection> connectionList = new LinkedList<>();
	private final int maxConnection = 5;
	private static String dbName = "CouponSystemDB";
	public static String dbUrl = "jdbc:derby://localhost:1527/" + dbName + ";create=true";

	private static ConnectionPool instance = null;;

	private ConnectionPool() 
	{

		try 
		{
			for (int i = 0; i < maxConnection; i++) 
			{
				Class.forName("org.apache.derby.jdbc.ClientDriver");
				connectionList.add(DriverManager.getConnection(dbUrl));
			}
			System.out.println("All " + connectionList.size() + " connection has been established!");
		} catch (SQLException | ClassNotFoundException e)
		{
			System.out.println("Unable to establish connection. " + "Check to see if derby client is up & running");
		}

	}

	public static ConnectionPool getInstance() 
	{
		synchronized (ConnectionPool.class) 
		{
			if (instance == null) 
			{
				instance = new ConnectionPool();
				return instance;
			}
			else 
			{
				return instance;
			}

		}

	}

	public synchronized Connection getConnection() 
	{
		while (connectionList.isEmpty()) 
		{
			try 
			{
				wait();
			} 
			catch (InterruptedException e) 
			{
				System.out.println("Could not establish connection");
			}
		}
		try 
		{
			Connection connection = connectionList.getFirst();
			connectionList.removeFirst();
			return connection;
		} 
		catch (NullPointerException e) 
		{
			System.out.println("couldnt get connection pool instance will return null");
			return null;
		}

	};

	public synchronized void returnConnection(Connection con) 
	{
		connectionList.addFirst(con);
		notify();
	}

	public void closeAllCollection() 
	{
		for (Connection con : connectionList) 
		{
		   try 
		   {
		    con.close();
		   } 
		   catch (SQLException e) 
		   {
		    System.out.println("Could not close connection");
		   }
		}
  System.out.println("All " + connectionList.size() + " conncetion has been closed.");
 }
	
}