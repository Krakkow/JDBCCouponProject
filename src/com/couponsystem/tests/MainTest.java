package com.couponsystem.tests;

import java.util.Date;
import java.util.Scanner;

import com.couponsystem.exceptions.AdminFacadeException;
import com.couponsystem.exceptions.CompanyFacadeException;
import com.couponsystem.exceptions.CustomerFacadeException;
import com.couponsystem.facadedao.ClientType;
import com.couponsystem.facadedbdao.AdminFacade;
import com.couponsystem.facadedbdao.CompanyFacade;
import com.couponsystem.facadedbdao.CustomerFacade;


@SuppressWarnings("unused")
public class MainTest
	{
		
		AdminFacade admin;
		CompanyFacade company;
		CustomerFacade customer;
		public static java.sql.Date getDate()
			{
				java.util.Date utilDate = new java.util.Date();
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				return sqlDate;
			}
		

		public static void main(String[] args) throws AdminFacadeException, CompanyFacadeException, CustomerFacadeException
			{
				//Starting data base tables 
				TableManagement.startDb();
				
				
				System.out.println("Welcome to my coupon system!");
				
				Scanner reader = new Scanner(System.in);
				
				System.out.println("To begin, please select your login type: \n"
						+	"1 for admin, \n"
						+ "2 for company, \n"
						+ "3 for customer");
				
				System.out.println("Please enter a number, and press 'Enter'");
				int n = reader.nextInt();
				
				switch (n)
					{
					case 1:
						adminLogin();
						System.out.println("You have successfully logged in as an Admin, please continue.");
						break;
					
					case 2:
						companyLogin();
						System.out.println("You have successfully logged in as a company, please continue.");

					case 3:
						customerLogin();
						System.out.println("You have successfully logged in as a customer, please continue.");
						break;
					}
				
				reader.close();
				
				if(adminLogin())
					{	
						System.out.println("Hello admin! these are your list of abiliteies. Enter a number to perform one of these actions: \n"
								+ "1. Create company \n"
								+ "2. Get company's ID \n"
								+ "3. Get comapny's name \n"
								+ "4. Get all companies \n"
								+ "5. Update a company \n"
								+ "6. Delete comapny \n"
								+ "7. Create customer \n"
								+ "8. Get customer \n"
								+ "9. Get all customers \n"
								+ "10. Update a customer \n"
								+ "11. Delete a customer");
						switch (n)
							{
							case 1:
								
								break;

							default:
								break;
							}
					}
				
				
				
				
				
				
				
				
				
				
				
				
//				printToConsole("Initizliaing login to admin");
//				AdminFacade admin = (AdminFacade) AdminFacade.getInstance().login("admin", "1234", ClientType.ADMIN);
//				printToConsole("Getiing company by name instead of ID");
//				//				admin.getCompanyName(1);
//				System.out.println(admin.getCompanyName("Company1"));
//				admin.getCompany(1);
//				System.out.println(admin.getCompany(1));
////				admin.createCustomer(new Customer(999, "cust1", "1234"));
//				printToConsole("updating customer");
//				Customer customerToUpdate=admin.getCustomer(1);
//				customerToUpdate.setPassword("ilya hamelech");
//				customerToUpdate.setCustName("Beerus-Sama");
//				admin.updateCustomer(customerToUpdate);
//				printToConsole("creating company");
//				admin.createCompany(new Company(222, "Company1", "1234", "email@me.com"));
//				printToConsole("updating company");
//				Company companyToUpdate = admin.getCompany(1);
//				companyToUpdate.setEmail("boom@bolenat.subkesat");
//				companyToUpdate.setPassword("pass");
//				admin.updateCompany(companyToUpdate);
//				printToConsole("Initializing Company login");
//				try
//					{
//						CompanyFacade companyFacade = (CompanyFacade) CompanyFacade.getInstance().login("Company1", "ilya hamelech", ClientType.COMPANY);
//					}
//				catch (Exception e)
//					{
//						System.out.println(e.getMessage());
//					}
//				
//				admin.createCompany(new Company(444, "company1", "1234", "email@me.com"));
//				TableManagement tm = new TableManagement();
//				admin.deleteCompany(101);
//				admin.createCustomer(new Customer(999, "cust2", "1234"));
//				admin.createCustomer(new Customer(999, "cust3", "1234"));
//				admin.createCustomer(new Customer(999, "cust4", "1234"));
//				printToConsole("Initializing Customer login");
//				try
//					{
//						CustomerFacade customerFacade = (CustomerFacade) CustomerFacade.getInstance().login("cust1", "1234", ClientType.CUSTOMER);
//					}
//				catch (Exception e)
//					{
//						System.out.println(e.getMessage());
//					}



			}
		
		private static boolean adminLogin()
		{
			printToConsole("Initializing Admin login");
			try
				{
					AdminFacade adminFacade = (AdminFacade) AdminFacade.getInstance().login("admin", "1234", ClientType.ADMIN);
				}
			catch (AdminFacadeException e)
				{
					System.out.println(e.getMessage());
				}
			return false;
		}


		private static boolean companyLogin()
		{
			printToConsole("Initializing Company login");
			try
				{
					CompanyFacade companyFacade = (CompanyFacade) CompanyFacade.getInstance().login("Company1", "ilya hamelech", ClientType.COMPANY);
				}
			catch (Exception e)
				{
					System.out.println(e.getMessage());
				}
			return false;				
		}


		private static boolean customerLogin()
			{
				printToConsole("Initializing Customer login");
				try
					{
						CustomerFacade customerFacade = (CustomerFacade) CustomerFacade.getInstance().login("cust1", "1234", ClientType.CUSTOMER);
					}
				catch (Exception e)
					{
						System.out.println(e.getMessage());
					}
				return false;
				
			}


		static void printToConsole(String string){
			System.out.println("---------------------------------------------------------------------------");
			System.out.println(new Date()+"INFO : "+string);
			System.out.println("---------------------------------------------------------------------------");
		}
	}
