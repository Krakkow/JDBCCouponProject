package com.couponsystem.tests;

import java.sql.Date;

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
 *
 * @author Or Kowalsky
 */
public class MainTest
	{

		/**
		 *
		 */
		public static int allCoupons;
		public static CouponSystem couponSys;
		private static Date date;

		/**
		 *
		 * @param couponSys
		 *            Coupon System
		 * @throws CouponSystemException
		 *             CouponSystemException
		 * @throws DAOException 
		 */
		@SuppressWarnings("static-access")
		public MainTest(CouponSystem couponSys) throws Exception {
				MainTest.couponSys = couponSys;
				try
					{
						System.out.println("***Testing CompanyDAO***");
						InitiateCompanyDAO();
						System.out.println("***Testing CouponDAO***");
						InitiateCouponDAO();
						System.out.println("***Testing CustomerDAO*** ");
						InitiateCustomerDAO();
						System.out.println("***Testing facade's***");
						initFacades(couponSys);
						
						//Testing Facades
						System.out.println("total Coupons" + CouponSystem.getInstance().couponDao.getAll());
						System.out.println("***Testing Facades in threads simultaniaclly***");
					}
				catch (CouponSystemException e)
					{
						e.printStackTrace();
						throw new CouponSystemException(ErrorType.TEST_ERROR_HAS_BEEN_INVOKED + e.getMessage());
					}
			}

		/**
		 *
		 * @param couponSys
		 *            Coupon System
		 * @throws CouponSystemException
		 *             CouponSystemException
		 */
		public static void initFacades(CouponSystem couponSys) throws CouponSystemException {
				// *************************************
				System.out.println("*********************************" + "******************************************"
						+ "**************************");
				System.out.println("***Testing Company facade******" + "\n");
				System.out.println("***Executing Company login : ***" + "\n");
			try
			{
				System.out.println(couponSys.comapnyDao.login("SigmaDesigns", "1234AbC")
						+ " Successfully logged");
			}
			catch (DAOException e)
			{
				e.printStackTrace();
			}
			CompanyFacade companyTest = null;
			try
			{
				companyTest = (CompanyFacade) couponSys.login("SigmaDesigns", "1234AbC",
                            ClientType.COMPANY);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			System.out.println("*****Creating standard Coupons*****" + "\n");
			try
			{
				companyTest.createCoupon(new Coupon(6, "Holiday and Spa", Date.valueOf("01\01\2016"), Date.valueOf("01\01\2017"), 300,
						CouponType.TRAVELLING, "random coupon message", 49.99, "pathToImage/image.jpeg"));
			}
			catch (CompanyFacadeException e)
			{
				e.printStackTrace();
			}
			Coupon randomCoupon = new Coupon(7, "CinemaCity",Date.valueOf("01\01\2016"), Date.valueOf("01\01\2017"), 300,
						CouponType.MOVIES, "random coupon message", 99.99, "pathToImage/image.jpeg");
				Coupon expiredCoupon = new Coupon(8, "Kitan+", Date.valueOf("01\01\2016"), Date.valueOf("01\01\2017"), 300,
						CouponType.HOMEDESIGN, "random coupon message", 109.99, "pathToImage/image.jpeg");
				Coupon noCouponsLeft = new Coupon(9,"Bed & Breakfast", Date.valueOf("01\01\2016"), Date.valueOf("01\01\2017"), 300,
						CouponType.TRAVELLING, "random coupon message", 149.99, "pathToImage/image.jpeg");
				Coupon newCoupon1 = new Coupon(10,"Casino Trip", Date.valueOf("01\01\2016"), Date.valueOf("01\01\2017"), 300,
						CouponType.TRAVELLING, "random coupon message", 149.99, "pathToImage/image.jpeg");
				Coupon newCoupon2 = new Coupon(11,"Discover Cambodia", Date.valueOf("01\01\2016"), Date.valueOf("01\01\2017"), 300,
						CouponType.TRAVELLING, "random coupon message", 149.99, "pathToImage/image.jpeg");

			try
			{
				companyTest.createCoupon(randomCoupon);
			}
			catch (CompanyFacadeException e)
			{
				e.printStackTrace();
			}
			System.out.println("\n" + "***Getting all company's Coupons***" + "\n");
			try
			{
				System.out.println(companyTest.getAllCoupons());
			}
			catch (CompanyFacadeException e)
			{
				e.printStackTrace();
			}
			try
			{
				System.out.println(companyTest.getAllCoupons());
			}
			catch (CompanyFacadeException e)
			{
				e.printStackTrace();
			}
			System.out.println("\n");
				System.out.println("\n" + "***Creating Coupons***" + "\n");
			try
			{
				companyTest.createCoupon(randomCoupon);
			}
			catch (CompanyFacadeException e)
			{
				e.printStackTrace();
			}
			try
			{
				companyTest.createCoupon(expiredCoupon);
			}
			catch (CompanyFacadeException e)
			{
				e.printStackTrace();
			}
			try
			{
				companyTest.createCoupon(noCouponsLeft);
			}
			catch (CompanyFacadeException e)
			{
				e.printStackTrace();
			}
			System.out.println("\n" + "***Getting all company's Coupons***" + "\n");
			try
			{
				System.out.println(companyTest.getAllCoupons());
			}
			catch (CompanyFacadeException e)
			{
				e.printStackTrace();
			}
			System.out.println("\n" + "***Updating Coupon***");
			try
			{
				companyTest.updateCoupon(7, Date.valueOf("01\01\2017"), 43.45);
			}
			catch (CompanyFacadeException e)
			{
				e.printStackTrace();
			}

			System.out.println("\n" + "***Getting Coupons of Company***" + "\n");
			try
			{
				System.out.println(companyTest.getAllCoupons());
			}
			catch (CompanyFacadeException e)
			{
				e.printStackTrace();
			}
			try
			{
				System.out.println(couponSys.couponDao.getAll());
			}
			catch (DAOException e)
			{
				e.printStackTrace();
			}
			System.out.println("\n" + "***Getting Coupons of Company by Date***" + "\n");
			try
			{
				System.out.println(companyTest.getCouponByDate(Date.valueOf("2017")));
			}
			catch (CompanyFacadeException e)
			{
				e.printStackTrace();
			}
			System.out.println("\n" + "***Getting Coupons of Company up to price 91***" + "\n");
			try
			{
				System.out.println(companyTest.getCouponsByPrice(109.99));
			}
			catch (CompanyFacadeException e)
			{
				e.printStackTrace();
			}
			System.out.println("\n" + "***Getting Coupons of Company by type TRAVELING***" + "\n");
			try
			{
				System.out.println(companyTest.getCouponsByType(CouponType.TRAVELLING));
			}
			catch (CompanyFacadeException e)
			{
				e.printStackTrace();
			}
			System.out.println("*********************************" + "******************************************"
						+ "**************************");
				// *************************************
				System.out.println("\n" + "***Testing Customer facade***" + "\n");
				System.out.println("\n" + "***Executing Company login : ***" + "\n");
			CustomerFacade myCustomer = null;
			try
			{
				myCustomer = (CustomerFacade) couponSys.login("kowalsky", "1234", ClientType.CUSTOMER);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			System.out.println("login Successfully");
			System.out.println("\n" + "***Purchasing coupons ***");
			System.out.println("\n" + "is coupons with the name randomCOupon and noCouponsleft purchasing done successfully?");
			try
            {
                myCustomer.purchaseCoupon(randomCoupon);
            }
            catch (CustomerFacadeException e)
            {
                e.printStackTrace();
            }
			try
            {
                myCustomer.purchaseCoupon(noCouponsLeft);
            }
            catch (CustomerFacadeException e)
            {
                e.printStackTrace();
            }
			try
			{
				System.out.println("\n" + " Coupons " + myCustomer.getAllPurchasedCoupons());
			}
			catch (CustomerFacadeException e)
			{
				e.printStackTrace();
			}
			System.out.println("\n" + "***Trying to purchase coupon that out of amount***" + "\n");
			try
            {
                myCustomer.purchaseCoupon(noCouponsLeft);
                System.out.println(myCustomer.getAllPurchasedCoupons());
            }
            catch (CustomerFacadeException e)
            {
                e.printStackTrace();
            }
			try
            {
                System.out.println(myCustomer.getAllPurchasedCoupons());
            }
            catch (CustomerFacadeException e)
            {
                e.printStackTrace();
            }
			try
			{
				companyTest.deleteCoupon(noCouponsLeft);
			}
			catch (CompanyFacadeException e)
			{
				e.printStackTrace();
			}
			System.out.println("\n" + "***Trying to purchase coupon that has expired***" + "\n");
				try
					{
						myCustomer.purchaseCoupon(expiredCoupon);
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
				e.printStackTrace();
			}
			System.out.println("\n" + "***Trying to purchase coupon that already exist : ***" + "\n");
			try
            {
                myCustomer.purchaseCoupon(randomCoupon);
            }
            catch (CustomerFacadeException e)
            {
                e.printStackTrace();
            }
			try
			{
				allCoupons = couponSys.couponDao.getAll().size() + 1;
			}
			catch (DAOException e)
			{
				e.printStackTrace();
			}
			try
			{
				myCustomer.purchaseCoupon(newCoupon1);
			}
			catch (CustomerFacadeException e)
			{
				e.printStackTrace();
			}
			System.out.println("\n" + "***Getting all purchased coupons ***" + "\n");
			try
			{
				System.out.println(myCustomer.getAllPurchasedCoupons());
			}
			catch (CustomerFacadeException e)
			{
				e.printStackTrace();
			}
			System.out.println("\n" + "***Getting all purchased coupons by type ELECTRICITY***" + "\n");
			try
			{
				System.out.println(myCustomer.getAllPurchasedCouponsByType(CouponType.ELECTRICITY));
			}
			catch (CustomerFacadeException e)
			{
				e.printStackTrace();
			}
			System.out.println("\n" + "***Getting all coupons ***" + "\n");
			try
			{
				System.out.println(companyTest.getAllCoupons());
			}
			catch (CompanyFacadeException e)
			{
				e.printStackTrace();
			}
			System.out.println("\n" + "***Getting all coupons of the company by price ***" + "\n");
			try
			{
				System.out.println(myCustomer.getAllPurchasedCouponsByPrice(104.99));
			}
			catch (CustomerFacadeException e)
			{
				e.printStackTrace();
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
				e.printStackTrace();
			}
			try
			{
				admin.createCompany(new Company(0, "companyTest2", "1234", "yahoo@wallack.co.il"));
			}
			catch (AdminFacadeException e)
			{
				e.printStackTrace();
			}
			CompanyFacade companyTest2 = null;
			try
			{
				companyTest2 = (CompanyFacade) couponSys.login("Apple", "1235", ClientType.COMPANY);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			try
			{
				System.out.println(companyTest.getAllCoupons());
			} catch (CompanyFacadeException e)
			{
				e.printStackTrace();
			}
//			System.out.println(companyTest.getCouponsOfCompany());
			try
			{
				System.out.println(admin.getAllCompanies());
			}
			catch (AdminFacadeException e)
			{
				e.printStackTrace();
			}
			companyTest2.createCoupon(new Coupon(1, "Holiday", Date.valueOf("01\01\2016"), Date.valueOf("01\01\2017"), 300,
					CouponType.TRAVELLING, "random coupon message", 149.99, "random image"));
				companyTest2.creatCoupon(random.getRandomCoupon());
				companyTest2.creatCoupon(random.getRandomCoupon());
				System.out.println(couponSys.couponDao.getAllCoupon());
				myCustomer.purchaseCoupon(12);
				myCustomer.purchaseCoupon(13);
				myCustomer.purchaseCoupon(14);
				System.out.println("\n" + "***Trying to create coupon with title that already exists***" + "\n");
				try
					{
						companyTest.creatCoupon(yoyo);
					}
				catch (CouponSystemException e)
					{
						System.out.println(e.getMessage());
					}
				System.out.println("\n"
						+ "***Tyring to delete Company that has Coupons that customer purchased the Coupons***" + "\n");
				try
					{
						admin.removeCompany(10);
					}
				catch (CouponSystemException e)
					{
						System.out.println(e.getMessage());
					}
				System.out.println("\n" + "***Show all coupons***" + "\n");
				System.out.println(couponSys.couponDao.getAllCoupon());
				System.out.println(myCustomer.getAllPurchasedCoupons());
				System.out.println(companyTest.getCouponsOfCompany());
				admin.creatCompany(new Company(9, "asusASSHOLES", "12345", "bgt@Walla.co.il"));
				System.out.println("\n" + "***Tyring to add Company that has the same name***");
				try
					{
						admin.creatCompany(new Company(10, "asusASSHOLES", "25235", "bgrtert@Walla.co.il"));
					}
				catch (CouponSystemException e)
					{
						System.out.println(e.getMessage());
					}
				admin.creatCustomer(new Customer(8, "custName", "1235"));
				System.out.println("\n" + "***Tyring to add Customer that has the same name ***" + "\n");
				try
					{
						admin.creatCustomer(new Customer(11, "custName", "12352"));
					}
				catch (CouponSystemException e)
					{
						System.out.println(e.getMessage());
					}
				System.out.println(admin.getAllCompanies());
				System.out.println(admin.getAllCustomer());
				admin.removeCompany(9);
				CustomerFacade customer2 = (CustomerFacade) couponSys.login("Gilbertu", "12345",
						ClientType.CUSTOMER);
				customer2.purchaseCoupon(7);
				customer2.purchaseCoupon(8);
				customer2.purchaseCoupon(9);
				System.out.println("\n" + "***Trying to remove customer that has coupon purchased***" + "\n");
				try
					{
						admin.removeCustomer(8);
					}
				catch (CouponSystemException e)
					{
						System.out.println(e.getMessage());
					}
				System.out.println("\n" + "***Showing all Companies and Customers***" + "\n");
				System.out.println(admin.getAllCompanies());
				System.out.println(admin.getAllCustomer());
			}

		/**
		 *
		 * @throws DAOException
		 *             DAOException
		 * @throws CouponSystemException
		 *             CouponSystemException
		 */
		public static void InitiateCouponDAO() throws DAOException, CouponSystemException
			{
				Coupon Holiday = new Coupon(1, "Holiday", Date.valueOf("01\01\2016"), Date.valueOf("01\01\2017"), 300,
						CouponType.TRAVELLING, "random coupon message", 149.99, "random image");
				couponSys.couponDao.create(Holiday);
				Coupon Food = new Coupon(2, "Food", Date.valueOf("01\01\2016"), Date.valueOf("01\01\2017"), 300,
						CouponType.RESTAURANT, "random coupon message", 149.99, "random image");
				couponSys.couponDao.create(Food);
				Coupon electricy = new Coupon(3, "Electricity", Date.valueOf("01\01\2016"), Date.valueOf("01\01\2017"),
						300, CouponType.ELECTRICITY, "random coupon message", 149.99, "random image");
				couponSys.couponDao.create(electricy);
				Coupon Sport = new Coupon(4, "Sport", Date.valueOf("01\01\2016"), Date.valueOf("01\01\2017"), 300,
						CouponType.SPORTS, "random coupon message", 149.99, "random image");
				couponSys.couponDao.create(Sport);
				Coupon Homedesign = new Coupon(5, "Homedesign", Date.valueOf("01\01\2016"), Date.valueOf("01\01\2017"),
						300, CouponType.TRAVELLING, "random coupon message", 149.99, "random image");
				couponSys.couponDao.create(Homedesign);
				couponSys.couponDao.delete(1);
			}

		/**
		 *
		 * @throws DAOException
		 *             DAOException
		 */
		public static void InitiateCustomerDAO() throws DAOException
			{
				Customer kowalsky = new Customer(1, "kowalsky", "1234");
				couponSys.customerDao.create(kowalsky);
				couponSys.customerDao.create(new Customer(2, "Greg", "12345"));
				couponSys.customerDao.create(new Customer(3, "Robert", "12345"));
				couponSys.customerDao.create(new Customer(4, "Eric", "12345"));
				couponSys.customerDao.create(new Customer(5, "Lisa", "12345"));
				couponSys.customerDao.create(new Customer(6, "Tony", "12345"));
				couponSys.customerDao.create(new Customer(7, "Stephen", "12345"));
				couponSys.customerDao.create(new Customer(8, "Kevin", "12345"));
				couponSys.customerDao.update(kowalsky);
				couponSys.customerDao.delete(1);
			}

		/**
		 *
		 * @throws DAOException
		 *             DAOException
		 * @throws CouponSystemException
		 *             CouponSystemException
		 */
		public void InitiateCompanyDAO() throws DAOException, CouponSystemException
			{
				couponSys.comapnyDao.create(new Company(1, "Intel", "1234abc", "intel@intel.com"));
				couponSys.comapnyDao.create(new Company(2, "Qualitest", "1234abc", "Melbourne@liquid.com"));
				couponSys.comapnyDao.create(new Company(3, "SigmaDesigns", "1234AbC", "Mel34@liquid.com"));
				Company apple = new Company(4, "Apple", "1235", "appleCrack@gsdgs.com");
				couponSys.comapnyDao.create(apple);
				apple.setEmail("blabla@Goo.com");
				couponSys.comapnyDao.update(apple);
				System.out.println(couponSys.comapnyDao.login("Apple", "1235"));
				couponSys.comapnyDao.create(new Company(4, "McGrew-Hill Education", "MHCampus", "man@oop.com"));
				couponSys.comapnyDao.create(new Company(5, "Adidas", "123", "jjj@kkk.com"));
				couponSys.comapnyDao.create(new Company(6, "Nike", "123", "jjj@66hy.com"));
				couponSys.comapnyDao.create(new Company(7, "OrCorp", "123", "jjj@cvbft.com"));
			}

	}