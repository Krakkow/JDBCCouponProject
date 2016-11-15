package com.couponsystem.tests;

import java.sql.Date;

import com.coupnsystem.db.TableCreation;
import com.couponsystem.beans.Company;
import com.couponsystem.beans.Coupon;
import com.couponsystem.beans.Coupon.CouponType;
import com.couponsystem.beans.Customer;
import com.couponsystem.exceptions.*;
import com.couponsystem.facadedao.ClientType;
import com.couponsystem.facadedbdao.AdminFacade;
import com.couponsystem.facadedbdao.CompanyFacade;
import com.couponsystem.facadedbdao.CustomerFacade;
import com.couponsystem.system.CouponSystem;

/**
 * @author Or Kowalsky
 */
public class MainTest
	{

		/**
		 *
		 */
		public static int allCoupons;
		/**
		 * @param couponSys
		 *            Coupon System
		 * @throws CouponSystemException
		 *             CouponSystemException
		 * @throws DAOException
		 */
		public static CouponSystem couponSys;

		public static void main(String[] args)
			{
				try
					{
						couponSys = CouponSystem.getInstance();
						System.out.println("get instance is initiated: couponSys = "+couponSys);
						mainTest();
					}
				catch (Exception e)
					{
						System.out.println(e.getMessage());
					}
			}

		public static void mainTest() throws Exception
			{
				System.out.println("Starting db");
				TableManagement.startDb();
				System.out.println("Finished db");
//				try
//					{
						System.out.println("***Testing CompanyDAO***");
						InitiateCompanyDAO();
						System.out.println("***Testing CouponDAO***");
						InitiateCouponDAO();
						System.out.println("***Testing CustomerDAO*** ");
						InitiateCustomerDAO();
						System.out.println("***Testing facade's***");
						initiateFacades();

						// Testing Facades
						System.out.println("total Coupons" + CouponSystem.getInstance().couponDao.getAll());

			}

		/**
		 *            Coupon System
		 * @throws CouponSystemException
		 *             CouponSystemException
		 */
		private static void initiateFacades() throws CouponSystemException
			{
				System.out.println("*********************************" + "******************************************"
						+ "**************************");
				System.out.println("***Testing Company facade******" + "\n");
				System.out.println("***Executing Company login : ***" + "\n");
				try
					{
						System.out.println(
								couponSys.comapnyDao.login("SigmaDesigns", "1234AbC") + " Successfully logged");
					}
				catch (DAOException e)
					{
                    	System.out.println(e.getMessage());
					}
				CompanyFacade companyTest = null;
				try
					{
						companyTest = (CompanyFacade) couponSys.login("SigmaDesigns", "1234AbC", ClientType.COMPANY);
					}
				catch (Exception e)
					{
                    	System.out.println(e.getMessage());
					}

				System.out.println("*****Creating standard Coupons*****" + "\n");
				try
					{
						companyTest.createCoupon(new Coupon(6,"Holiday and Spa", Date.valueOf("2016-1-1"),
								Date.valueOf("2017-1-1"), 300, CouponType.TRAVELLING, "random coupon message", 49.99,
								"pathToImage/image.jpeg"));
					}
				catch (CompanyFacadeException e)
					{
						System.out.println(e.getMessage());
					}
				Coupon randomCoupon = new Coupon(7,"CinemaCity1", Date.valueOf("2016-1-1"), Date.valueOf("2017-1-1"),
						300, CouponType.MOVIES, "random coupon message", 99.99, "pathToImage/image.jpeg");
				Coupon expiredCoupon = new Coupon(8,"KitanPlus", Date.valueOf("2016-1-1"), Date.valueOf("2017-1-1"), 300,
						CouponType.HOMEDESIGN, "random coupon message", 109.99, "pathToImage/image.jpeg");
				Coupon reallyExpiredCoupon = new Coupon(9,"expired", Date.valueOf("2015-1-1"), Date.valueOf("2016-1-1"), 300,
						CouponType.HOMEDESIGN, "random coupon message", 109.99, "pathToImage/image.jpeg");
				Coupon newCoupon1 = new Coupon(10,"Casino Trip", Date.valueOf("2016-1-1"), Date.valueOf("2017-1-1"),
						300, CouponType.TRAVELLING, "random coupon message", 149.99, "pathToImage/image.jpeg");
				Coupon newCoupon2 = new Coupon(11,"Discover Cambodia", Date.valueOf("2016-1-1"),
						Date.valueOf("2017-1-1"), 300, CouponType.TRAVELLING, "random coupon message", 149.99,
						"pathToImage/image.jpeg");
				Coupon noCouponsLeft = new Coupon(12,"noCouponsLeft", Date.valueOf("2016-1-1"),
						Date.valueOf("2017-1-1"), 300, CouponType.TRAVELLING, "random coupon message", 149.99,
						"pathToImage/image.jpeg");

				System.out.println("\n" + "***Getting all company's Coupons***" + "\n");
				System.out.println("companyTest id = "+companyTest.getCompanyId());

				System.out.println("\n");
				System.out.println("\n" + "***Creating Coupons***" + "\n");

				try
					{
						companyTest.createCoupon(randomCoupon);
						companyTest.createCoupon(expiredCoupon);
						companyTest.createCoupon(reallyExpiredCoupon);
						companyTest.createCoupon(newCoupon1);
						companyTest.createCoupon(newCoupon2);
						companyTest.createCoupon(noCouponsLeft);
						TableManagement.print("COUPON");

					}
				catch (CompanyFacadeException e)
					{
						System.out.println(e.getMessage());
						
					}
				try
					{
						System.out.println(companyTest.getAllCoupons());
					}
				catch (CompanyFacadeException e)
					{
						System.out.println(e.getMessage());
					}

				System.out.println("\n" + "***Updating Coupon***");
				try
					{
						companyTest.updateCoupon(7, Date.valueOf("2017-1-1"), 43.45);
					}
				catch (CompanyFacadeException e)
					{
						System.out.println(e.getMessage());
						
					}

				try
					{
						System.out.println(couponSys.couponDao.getAll());
					}
				catch (DAOException e)
					{
						System.out.println(e.getMessage());
						
					}
				System.out.println("\n" + "***Getting Coupons of Company by Date***" + "\n");
				try
					{
						System.out.println(companyTest.getCouponByDate(Date.valueOf("2017-1-1")));
					}
				catch (CompanyFacadeException e)
					{
						System.out.println(e.getMessage());
						
					}
				System.out.println("\n" + "***Getting Coupons of Company up to price 199.99***" + "\n");
				try
					{
						System.out.println(companyTest.getCouponsByPrice(199.99));
					}
				catch (CompanyFacadeException e)
					{
						System.out.println(e.getMessage());
						
					}
				System.out.println("\n" + "***Getting Coupons of Company by type TRAVELING***" + "\n");
				try
					{
						System.out.println(companyTest.getCouponsByType(CouponType.TRAVELLING));
					}
				catch (CompanyFacadeException e)
					{
						System.out.println(e.getMessage());
						
					}
				System.out.println("*********************************" + "******************************************"
						+ "**************************");
				// *************************************
				System.out.println("\n" + "***Testing Customer facade***" + "\n");
				System.out.println("\n" + "***Executing Company login : ***" + "\n");
				CustomerFacade myCustomer = null;
				try
					{
						myCustomer = (CustomerFacade) couponSys.login("Greg", "12345", ClientType.CUSTOMER);
					}
				catch (Exception e)
					{
						System.out.println(e.getMessage());
						
					}
				System.out.println("login Successfully");
				System.out.println("\n" + "***Purchasing coupons ***");
				System.out.println(
						"\n" + "is coupons with the name randomCOupon and noCouponsleft purchasing done successfully?");
				try
					{
						myCustomer.purchaseCoupon(newCoupon2);
						myCustomer.purchaseCoupon(randomCoupon);
					}
				catch (CustomerFacadeException e)
					{
						System.out.println(e.getMessage());
						
					}
				try
					{
						System.out.println("\n" + " Coupons " + myCustomer.getAllPurchasedCoupons());
					}
				catch (CustomerFacadeException e)
					{
						System.out.println(e.getMessage());
						
					}
				System.out.println("\n" + "***Trying to purchase coupon that out of amount***" + "\n");
				try
					{
						myCustomer.purchaseCoupon(noCouponsLeft);
						System.out.println(myCustomer.getAllPurchasedCoupons());
					}
				catch (CustomerFacadeException e)
					{
						System.out.println(e.getMessage());
						
					}
				try
					{
						System.out.println(myCustomer.getAllPurchasedCoupons());
					}
				catch (CustomerFacadeException e)
					{
						System.out.println(e.getMessage());
						
					}
				System.out.println("***Trying to delete coupon of company ***");
				try
					{
						companyTest.deleteCoupon(noCouponsLeft);
					}
				catch (CompanyFacadeException e)
					{
						System.out.println(e.getMessage());
						
					}
				System.out.println("\n" + "***Trying to purchase coupon that has expired***" + "\n");
				try
					{
						myCustomer.purchaseCoupon(reallyExpiredCoupon);
					}
				catch (CustomerFacadeException e)
					{
						System.out.println(e.getMessage());
					}
				try
					{
						companyTest.deleteCoupon(expiredCoupon);
					}
				catch (CompanyFacadeException e)
					{
						System.out.println(e.getMessage());
						
					}
				System.out.println("\n" + "***Trying to purchase coupon that already exist : ***" + "\n");
				try
					{
						myCustomer.purchaseCoupon(randomCoupon);
					}
				catch (CustomerFacadeException e)
					{
						System.out.println(e.getMessage());
					}
				try
					{
						allCoupons = couponSys.couponDao.getAll().size() + 1;
						System.out.println("number of coupons is "+allCoupons);
					}
				catch (DAOException e)
					{
						System.out.println(e.getMessage());
						
					}
				try
					{
						myCustomer.purchaseCoupon(newCoupon1);
					}
				catch (CustomerFacadeException e)
					{
						System.out.println(e.getMessage());
						
					}
				System.out.println("\n" + "***Getting all purchased coupons ***" + "\n");
				try
					{
						System.out.println(myCustomer.getAllPurchasedCoupons());
					}
				catch (CustomerFacadeException e)
					{
						System.out.println(e.getMessage());
						
					}
				System.out.println("\n" + "***Getting all purchased coupons by type ELECTRICITY***" + "\n");
				try
					{
						System.out.println(myCustomer.getAllPurchasedCouponsByType(CouponType.ELECTRICITY));
					}
				catch (CustomerFacadeException e)
					{
						System.out.println(e.getMessage());
						
					}
				System.out.println("\n" + "***Getting all coupons ***" + "\n");
				try
					{
						System.out.println(companyTest.getAllCoupons());
					}
				catch (CompanyFacadeException e)
					{
						System.out.println(e.getMessage());
						
					}
				System.out.println("\n" + "***Getting all coupons of the company by price ***" + "\n");
				try
					{
						System.out.println(myCustomer.getAllPurchasedCouponsByPrice(104.99));
					}
				catch (CustomerFacadeException e)
					{
						System.out.println(e.getMessage());
						
					}
				System.out.println("\n" + "***Getting all companies Coupons***" + "\n");
				System.out.println("*********************************" + "******************************************"
						+ "**************************");
				// ***************************************
				System.out.println("\n" + "***Testing Admin facade***" + "\n");
				AdminFacade admin = null;
				try
					{
						admin = (AdminFacade) couponSys.login("admin", "1234", ClientType.ADMIN);
					}
				catch (Exception e)
					{
						System.out.println(e.getMessage());
						
					}
				try
					{
						admin.createCompany(new Company(0, "companyTest2", "1234", "yahoo@wallack.co.il"));
						TableManagement.print("Company");
					}
				catch (AdminFacadeException e)
					{
						System.out.println(e.getMessage());
						
					}
				CompanyFacade companyTest2 = null;
				try
					{
						companyTest2 = (CompanyFacade) couponSys.login("Apple", "1235", ClientType.COMPANY);
					}
				catch (Exception e)
					{
						System.out.println(e.getMessage());
						
					}
				try
					{
						System.out.println(companyTest.getAllCoupons());
					}
				catch (CompanyFacadeException e)
					{
						System.out.println(e.getMessage());
						
					}
				// System.out.println(companyTest.getCouponsOfCompany());
				try
					{
						System.out.println(admin.getAllCompanies());
					}
				catch (AdminFacadeException e)
					{
						System.out.println(e.getMessage());
						
					}

				Coupon homeTheater = null;
				Coupon michelinDinner = null;
				Coupon seasonTeamTickets = null;
				try
					{

						homeTheater = new Coupon(13,"Home Theater", Date.valueOf("2016-1-1"), Date.valueOf("2017-1-1"),
								120, CouponType.ELECTRICITY, "random coupon message", 229.99, "pathToImage/image.jpeg");
						michelinDinner = new Coupon(14,"Michelin Dinner", Date.valueOf("2016-1-1"),
								Date.valueOf("2017-1-1"), 30, CouponType.RESTAURANT, "random coupon message", 299.99,
								"pathToImage/image.jpeg");
						seasonTeamTickets = new Coupon(15,"Season Team Tickets", Date.valueOf("2016-1-1"),
								Date.valueOf("2017-1-1"), 30, CouponType.SPORTS, "random coupon message", 999.99,
								"pathToImage/image.jpeg");
					}
				catch (Exception e)
					{
						System.out.println(e.getMessage());
						
					}
				try
					{
						companyTest2.createCoupon(homeTheater);
						companyTest2.createCoupon(michelinDinner);
						companyTest2.createCoupon(seasonTeamTickets);
						TableManagement.print("Coupon");
					}
				catch (CompanyFacadeException e)
					{
						System.out.println(e.getMessage());
						
					}
				try
					{
						System.out.println(couponSys.couponDao.getAll());
					}
				catch (DAOException e)
					{
						System.out.println(e.getMessage());
						
					}
				try
					{
						myCustomer.purchaseCoupon(homeTheater);
						TableManagement.print("CUSTOMER_COUPON");
						myCustomer.purchaseCoupon(michelinDinner);
						TableManagement.print("CUSTOMER_COUPON");
						myCustomer.purchaseCoupon(seasonTeamTickets);
						TableManagement.print("CUSTOMER_COUPON");
					}
				catch (CustomerFacadeException e)
					{
						System.out.println(e.getMessage());
						
					}
				System.out.println("\n" + "***Trying to create coupon with title that already exists***" + "\n");

				try
					{
						//1
						companyTest.createCoupon(homeTheater);
					}
				catch (CompanyFacadeException e)
					{
						System.out.println(e.getMessage());
					}

				System.out.println("\n"
						+ "***Tyring to delete Company that has Coupons that customer purchased the Coupons***" + "\n");
				try
					{
						admin.deleteCompany(3);
					}
				catch (AdminFacadeException e)
					{
						System.out.println(e.getMessage());
						
					}
				System.out.println("\n" + "***Show all coupons***" + "\n");
				try
					{
						System.out.println(couponSys.couponDao.getAll());
					}
				catch (DAOException e)
					{
						System.out.println(e.getMessage());
						
					}
				try
					{
						System.out.println(myCustomer.getAllPurchasedCoupons());
					}
				catch (CustomerFacadeException e)
					{
						System.out.println(e.getMessage());
						
					}
				try
					{
						System.out.println(companyTest.getAllCoupons());
					}
				catch (CompanyFacadeException e)
					{
						System.out.println(e.getMessage());
						
					}
				try
					{
						admin.createCompany(new Company(9, "Subaru", "*1425", "subaru@me.com"));
					}
				catch (AdminFacadeException e)
					{
						System.out.println(e.getMessage());
						
					}
				System.out.println("\n" + "***Tyring to add Company that has the same name***");
				try
					{
						admin.createCompany(new Company(10, "Subaru", "25235", "ImSubaru@gmail.co.il"));
					}
				catch (AdminFacadeException e)
					{

						System.out.println(e.getMessage());
					}
				try
					{
						admin.createCustomer(new Customer(8, "custName", "1235"));
					}
				catch (AdminFacadeException e)
					{
						System.out.println(e.getMessage());
						
					}
				System.out.println("\n" + "***Tyring to add Customer that has the same name ***" + "\n");
				try
					{
						admin.createCustomer(new Customer(11, "custName", "12352"));
					}
				catch (AdminFacadeException e)
				// catch (CouponSystemException e)
					{
						System.out.println(e.getMessage());
					}
				try
					{
						System.out.println(admin.getAllCompanies());
					}
				catch (AdminFacadeException e)
					{
						System.out.println(e.getMessage());
						
					}
				try
					{
						System.out.println(admin.getAllCustomers());
					}
				catch (AdminFacadeException e)
					{
						System.out.println(e.getMessage());
					//	
					}
				System.out.println("***Trying to delete company***");
				try
					{
						admin.deleteCompany(9);
					}
				catch (AdminFacadeException e)
					{
						System.out.println(e.getMessage());
						//
					}
				CustomerFacade customer2 = null;
				System.out.println("*** Trying to login with a customer that doe not exist ***");
				try
					{
						customer2 = (CustomerFacade) couponSys.login("Gilbertu", "12345", ClientType.CUSTOMER);
						
					}
				catch (Exception e)
					{

					System.out.println(e.getMessage());
					}
//				try
//					{
//						customer2.purchaseCoupon(randomCoupon);
//						TableManagement.print("CUSTOMER_COUPON");
//						customer2.purchaseCoupon(newCoupon2);
//						TableManagement.print("CUSTOMER_COUPON");
//						customer2.purchaseCoupon(michelinDinner);
//						TableManagement.print("CUSTOMER_COUPON");
//					}
//				catch (CustomerFacadeException e)
//					{
//						System.out.println(e.getMessage());
//					//	
//					}
				System.out.println("\n" + "***Trying to remove customer that has coupon purchased***" + "\n");
				try
					{
						admin.deleteCustomer(5);
					}
				catch (AdminFacadeException e)
					{
						System.out.println(e.getMessage());
						//
					}
				System.out.println("\n" + "***Showing all Companies and Customers***" + "\n");
				try
					{
						System.out.println(admin.getAllCompanies());
					}
				catch (AdminFacadeException e)
					{
						System.out.println(e.getMessage());
					//	
					}
				try
					{
						System.out.println(admin.getAllCustomers());
					}
				catch (AdminFacadeException e)
					{
						System.out.println(e.getMessage());
					}
			}

		/**
		 * @throws DAOException
		 *             DAOException
		 * @throws CouponSystemException
		 *             CouponSystemException
		 */
		private static void InitiateCouponDAO() throws DAOException, CouponSystemException
			{
				// Date format "yyyy-[m]m-[d]d
				Coupon Holiday = new Coupon(1,"Holiday", Date.valueOf("2016-1-1"), Date.valueOf("2017-1-1"), 300,
						CouponType.TRAVELLING, "random coupon message", 149.99, "pathToImage/image.jpeg");
				couponSys.couponDao.create(Holiday);
				Coupon Food = new Coupon(2,"Food", Date.valueOf("2016-1-1"), Date.valueOf("2017-1-1"), 300,
						CouponType.RESTAURANT, "random coupon message", 149.99, "pathToImage/image.jpeg");
				couponSys.couponDao.create(Food);
				Coupon newTablet = new Coupon(3,"newTablet", Date.valueOf("2016-1-1"), Date.valueOf("2017-1-1"), 300,
						CouponType.ELECTRICITY, "random coupon message", 149.99, "pathToImage/image.jpeg");
				couponSys.couponDao.create(newTablet);
				Coupon Sport = new Coupon(4,"Sport", Date.valueOf("2016-1-1"), Date.valueOf("2017-1-1"), 300,
						CouponType.SPORTS, "random coupon message", 149.99, "pathToImage/image.jpeg");
				couponSys.couponDao.create(Sport);
				Coupon HomeDesign = new Coupon(5,"HomeDesign", Date.valueOf("2016-1-1"), Date.valueOf("2017-1-1"), 300,
						CouponType.TRAVELLING, "random coupon message", 149.99, "pathToImage/image.jpeg");
				couponSys.couponDao.create(HomeDesign);
				TableManagement.print("Coupon");
				couponSys.couponDao.delete(1);
				TableManagement.print("Coupon");
			}

		/**
		 * @throws DAOException
		 *             DAOException
		 */
		private static void InitiateCustomerDAO() throws DAOException
			{
				Customer kowalsky = new Customer(1, "kowalsky", "1234");
				couponSys.customerDao.create(kowalsky);
				TableManagement.print("Customer");
				couponSys.customerDao.create(new Customer(2, "Greg", "12345"));
				TableManagement.print("Customer");
				couponSys.customerDao.create(new Customer(3, "Robert", "12345"));
				TableManagement.print("Customer");
				couponSys.customerDao.create(new Customer(4, "Eric", "12345"));
				TableManagement.print("Customer");
				couponSys.customerDao.create(new Customer(5, "Lisa", "12345"));
				TableManagement.print("Customer");
				couponSys.customerDao.create(new Customer(6, "Tony", "12345"));
				TableManagement.print("Customer");
				couponSys.customerDao.create(new Customer(7, "Stephen", "12345"));
				TableManagement.print("Customer");
				couponSys.customerDao.create(new Customer(8, "Kevin", "12345"));
				TableManagement.print("Customer");
				couponSys.customerDao.update(kowalsky);
				TableManagement.print("Customer");
				couponSys.customerDao.delete(1);
				TableManagement.print("Customer");

			}

		/**
		 * @throws DAOException
		 *             DAOException
		 * @throws CouponSystemException
		 *             CouponSystemException
		 */
		private static void InitiateCompanyDAO() throws DAOException, CouponSystemException
			{
				couponSys.comapnyDao.create(new Company(1, "Intel", "1234abc", "intel@intel.com"));
				TableManagement.print("Company");
				couponSys.comapnyDao.create(new Company(2, "Qualitest", "1234abc", "Melbourne@liquid.com"));
				TableManagement.print("Company");
				couponSys.comapnyDao.create(new Company(3, "SigmaDesigns", "1234AbC", "Mel34@liquid.com"));
				TableManagement.print("Company");
				Company apple = new Company(4, "Apple", "1235", "appleCrack@gsdgs.com");
				couponSys.comapnyDao.create(apple);
				TableManagement.print("Company");
				apple.setEmail("blabla@Goo.com");
				couponSys.comapnyDao.update(apple);
				TableManagement.print("Company");
				System.out.println(couponSys.comapnyDao.login("Apple", "1235"));
				couponSys.comapnyDao.create(new Company(5, "McGrew-Hill Education", "MHCampus", "man@oop.com"));
				TableManagement.print("Company");
				couponSys.comapnyDao.create(new Company(6, "Adidas", "123", "jjj@kkk.com"));
				TableManagement.print("Company");
				couponSys.comapnyDao.create(new Company(7, "Nike", "123", "jjj@66hy.com"));
				TableManagement.print("Company");
				couponSys.comapnyDao.create(new Company(8, "OrCorp", "123", "jjj@cvbft.com"));
				TableManagement.print("Company");
			}

	}