package com.couponsystem.tests;

import java.util.Date;

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

		public static java.sql.Date getDate()
			{
				java.util.Date utilDate = new java.util.Date();
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				return sqlDate;
			}

		public static void main(String[] args) throws AdminFacadeException, CompanyFacadeException, CustomerFacadeException
			{
				
				
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
				printToConsole("Initializing Company login");
				try
					{
						CompanyFacade companyFacade = (CompanyFacade) CompanyFacade.getInstance().login("Company1", "ilya hamelech", ClientType.COMPANY);
					}
				catch (Exception e)
					{
						System.out.println(e.getMessage());
					}
				
//				admin.createCompany(new Company(444, "company1", "1234", "email@me.com"));
//				TableManagement tm = new TableManagement();
//				admin.deleteCompany(101);
//				admin.createCustomer(new Customer(999, "cust2", "1234"));
//				admin.createCustomer(new Customer(999, "cust3", "1234"));
//				admin.createCustomer(new Customer(999, "cust4", "1234"));
				printToConsole("Initializing Customer login");
				try
					{
						CustomerFacade customerFacade = (CustomerFacade) CustomerFacade.getInstance().login("cust1", "1234", ClientType.CUSTOMER);
					}
				catch (Exception e)
					{
						System.out.println(e.getMessage());
					}



			}
		
		
		static void printToConsole(String string){
			System.out.println("---------------------------------------------------------------------------");
			System.out.println(new Date()+"INFO : "+string);
			System.out.println("---------------------------------------------------------------------------");
		}
	}
