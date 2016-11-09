package com.couponsystem.tests;

import java.sql.Date;
import java.sql.Timestamp;

import com.couponsystem.beans.Company;
import com.couponsystem.beans.Coupon;
import com.couponsystem.beans.Coupon.CouponType;
import com.couponsystem.beans.Customer;
import com.couponsystem.exceptions.CouponSystemException;
import com.couponsystem.exceptions.DAOException;
import com.couponsystem.exceptions.ErrorType;
import com.couponsystem.facadedao.ClientType;
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
		public static int totalCoupons;
		public static CouponSystem couponSys;
		private static Date date;

		/**
		 *
		 * @param couponSys
		 *            Coupon System
		 * @throws CouponSystemException
		 *             CouponSystemException
		 */
		@SuppressWarnings("static-access")
		public MainTest(CouponSystem couponSys) throws CouponSystemException
			{
				this.couponSys = couponSys;
				try
					{
						System.out.println("***Testing CompanyDAO***");
						InitCompanyDAO();
						System.out.println("***Testing CouponDAO***");
						InitCouponDAO();
						System.out.println("***Testing CustomerDAO*** ");
						InitCustomerDAO();
						System.out.println("***Testing facade's***");
						initFacades(couponSys);
						/**
						 * facade tests simutanislly
						 */
						System.out.println("total Coupons" + CouponSystem.getInstance().couponDao.getAllCoupon());
						System.out.println("***Testing Facades in threads simultaniaclly***");
						new ThreadTests();
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
		public static void initFacades(CouponSystem couponSys) throws CouponSystemException
			{
				// *************************************
				RandomGenerator random = new RandomGenerator();
				System.out.println("*********************************" + "******************************************"
						+ "**************************");
				System.out.println("***Testing Company facade******" + "\n");
				System.out.println("***Executing Company login : ***" + "\n");
				System.out.println(couponSys.comapnyDao.getCompanyByNamePassword("MaxInnovations", "tyrone")
						+ " Successfully logged");
				CompanyFacadeDB companyTest = (CompanyFacadeDB) couponSys.login("MaxInnovations", "tyrone",
						ClientType.COMPANY);

				System.out.println("*****Creating standard Coupons*****" + "\n");
				companyTest.creatCoupon(new Coupon(5, "bestCoupon", generateDate(2016, 2017), generateDate(2017, 2018),
						20, CouponType.TRAVELING, " hawai", 100, "this is the img path/directory/idfmg.jdspg"));
				companyTest.creatCoupon(new Coupon(6, "omgCoupon", generateDate(2016, 2017), generateDate(2017, 2018),
						20, CouponType.ELECTRICITY, " honalulu", 50, "this is the img path/directory/rty.jpg"));
				Coupon yoyo = new Coupon(7, "purim", generateDate(2016, 2017), generateDate(2017, 2018), 23,
						CouponType.FOOD, "hag purim", 89.54, "path/directory/rty.jpg");
				Coupon hunny = new Coupon(8, "hanuuka", generateDate(2016, 2017), generateDate(2017, 2018), 20,
						CouponType.ELECTRICITY, "hag hunuuka", 60, "path/directory/rty.jpg");
				Coupon bear = new Coupon(9, "bearbuu", generateDate(2016, 2017), generateDate(2017, 2018), 20,
						CouponType.ELECTRICITY, "hag bear", 27, "path/directory/rty.jpg");
				Coupon expired = new Coupon(10, "expiredCoupon", generateDate(2014, 2015), generateDate(2014, 2015), 20,
						CouponType.ELECTRICITY, "hag bear", 27, "path/directory/rty.jpg");
				Coupon outOfAmountCoupon = new Coupon(11, "outofAmount", generateDate(2014, 2015),
						generateDate(2017, 2018), 0, CouponType.ELECTRICITY, "hag bear", 0, "path/directory/rty.jpg");

				companyTest.creatCoupon(yoyo);
				System.out.println("\n" + "***Getting all companies Coupons***" + "\n");
				System.out.println(companyTest.getCouponsOfCompany());
				System.out.println(companyTest.getCouponsOfCompany());
				System.out.println("\n");
				System.out.println("\n" + "***Creating Coupons***" + "\n");
				companyTest.creatCoupon(hunny);
				companyTest.creatCoupon(bear);
				companyTest.creatCoupon(expired);
				companyTest.creatCoupon(outOfAmountCoupon);
				System.out.println("\n" + "***Getting all companies Coupons***" + "\n");
				System.out.println(companyTest.getCouponsOfCompany());
				System.out.println("\n" + "***Updating Coupon***");
				companyTest.updateCoupon(7, generateDate(2018, 2019), 43.45);

				System.out.println("\n" + "***Getting Coupons of Company***" + "\n");
				System.out.println(companyTest.getCouponsOfCompany());
				System.out.println(couponSys.couponDao.getAllCoupon());
				System.out.println("\n" + "***Getting Coupons of Company by Date***" + "\n");
				System.out.println(companyTest.getCouponByDate(generateDate(2016, 2018)));
				System.out.println("\n" + "***Getting Coupons of Company up to price 91***" + "\n");
				System.out.println(companyTest.getCouponUpToPrice(91));
				System.out.println("\n" + "***Getting Coupons of Company by type TRAVELING***" + "\n");
				System.out.println(companyTest.getCouponByType(CouponType.TRAVELING));
				System.out.println("*********************************" + "******************************************"
						+ "**************************");
				// *************************************
				System.out.println("\n" + "***Testing Customer facade***" + "\n");
				System.out.println("\n" + "***Executing Company login : ***" + "\n");
				CustomerFacadeDB ilyacustomer = (CustomerFacadeDB) couponSys.login("ilya", "5556", ClientType.CUSTOMER);
				System.out.println("login Sucessfully");
				System.out.println("\n" + "***Purchasing coupons ***");
				try
					{
						System.out.println("\n" + "is coupons with id of 7 and 8 purchasing done successfully?"
								+ ilyacustomer.purchaseCoupon(7) + ilyacustomer.purchaseCoupon(8) + "\n");
					}
				catch (CouponSystemException e)
					{
						System.out.println(e.getMessage());
						e.printStackTrace();

					}
				System.out.println("\n" + " Coupons " + ilyacustomer.getAllPurchasedCoupons());
				System.out.println("\n" + "***Trying to purchase coupon that out of amount***" + "\n");
				try
					{
						System.out.println(ilyacustomer.purchaseCoupon(11));
						System.out.println(ilyacustomer.getAllPurchasedCoupons());
					}
				catch (CouponSystemException e)
					{
						System.out.println(e.getMessage());
					}
				companyTest.removeCoupon(11);
				System.out.println("\n" + "***Trying to purchase coupon that has expired***" + "\n");
				try
					{
						System.out.println(ilyacustomer.purchaseCoupon(10));
					}
				catch (CouponSystemException e)
					{
						System.out.println(e.getMessage());
					}
				companyTest.removeCoupon(10);
				System.out.println("\n" + "***Trying to purchase coupon that already exsist : ***" + "\n");
				try
					{
						System.out.println(ilyacustomer.purchaseCoupon(8));
					}
				catch (CouponSystemException e)
					{
						System.out.println(e.getMessage());
					}
				totalCoupons = couponSys.couponDao.getAllCoupon().size() + 1;
				System.out.println(ilyacustomer.purchaseCoupon(9));
				System.out.println("\n" + "***Getting all purchased coupons ***" + "\n");
				System.out.println(ilyacustomer.getAllPurchasedCoupons());
				System.out.println("\n" + "***Getting all purchased coupons by type ELECTRICITY***" + "\n");
				System.out.println(ilyacustomer.getAllPurchasedCouponsByType(CouponType.ELECTRICITY));
				System.out.println("\n" + "***Getting all coupons ***" + "\n");
				System.out.println(companyTest.getCouponsOfCompany());
				System.out.println("\n" + "***Getting all coupons of the company by price ***" + "\n");
				System.out.println(ilyacustomer.getAllPurchasedCouponsByPrice(41));
				System.out.println("\n" + "***Getting all companies Coupons***" + "\n");
				System.out.println("*********************************" + "******************************************"
						+ "**************************");
				// ***************************************
				System.out.println("\n" + "***Testing Admin facade***" + "\n");
				AdminFacadeDB admin = (AdminFacadeDB) couponSys.login("admin", "1234", ClientType.ADMIN);
				admin.creatCompany(new Company(0, "companyTest2", "1234", "bgtgg@Walla.co.il"));
				CompanyFacadeDB companyTest2 = (CompanyFacadeDB) couponSys.login("companyTest2", "1234",
						ClientType.COMPANY);
				System.out.println(companyTest.getCouponsOfCompany());
				System.out.println(companyTest.getCouponsOfCompany());
				System.out.println(admin.getAllCompanies());
				companyTest2.creatCoupon(random.getRandomCoupon());
				companyTest2.creatCoupon(random.getRandomCoupon());
				companyTest2.creatCoupon(random.getRandomCoupon());
				System.out.println(couponSys.couponDao.getAllCoupon());
				ilyacustomer.purchaseCoupon(12);
				ilyacustomer.purchaseCoupon(13);
				ilyacustomer.purchaseCoupon(14);
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
				System.out.println(ilyacustomer.getAllPurchasedCoupons());
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
				CustomerFacade customer2 = (CustomerFacade) couponSys.login("Greg", "12345",
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
		public static void InitCouponDAO() throws DAOException, CouponSystemException
			{
				Coupon Holiday = new Coupon(1, "Holiday",date.valueOf("01\01\2016") , date.valueOf("01\01\2017"), 300, CouponType.TRAVELLING, "random coupon message", 149.99, "random image");
				couponSys.couponDao.create(Holiday);
				Coupon Food = new Coupon(2, "Food",date.valueOf("01\01\2016") , date.valueOf("01\01\2017"), 300, CouponType.RESTAURANT, "random coupon message", 149.99, "random image");
				couponSys.couponDao.create(Food);
				Coupon Electricy = new Coupon(3, "Electricy",date.valueOf("01\01\2016") , date.valueOf("01\01\2017"), 300, CouponType.ELECTRICITY, "random coupon message", 149.99, "random image");
				couponSys.couponDao.create(Electricy);
				Coupon Sport = new Coupon(4, "Sport",date.valueOf("01\01\2016") , date.valueOf("01\01\2017"), 300, CouponType.SPORTS, "random coupon message", 149.99, "random image");
				couponSys.couponDao.create(Sport);
				Coupon Homedesign = new Coupon(5, "Homedesign",date.valueOf("01\01\2016") , date.valueOf("01\01\2017"), 300, CouponType.TRAVELLING, "random coupon message", 149.99, "random image");
				couponSys.couponDao.create(Homedesign);
				couponSys.couponDao.delete(1);
			}

		/**
		 *
		 * @throws DAOException
		 *             DAOException
		 */
		public static void InitCustomerDAO() throws DAOException
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
		public void InitCompanyDAO() throws DAOException, CouponSystemException
			{
				couponSys.comapnyDao.create(new Company(1, "Intel", "intelforyou", "intel@intel.com"));
				couponSys.comapnyDao.create(new Company(2, "Exotic Liquid", "Charlotte", "Melbourne@liquid.com"));
				couponSys.comapnyDao.create(new Company(3, "ExoeticG", "Charl8otte3", "Mel34@liquid.com"));
				Company google = new Company(4, "Google", "1235", "googleCorps@gsdgs.com");
				couponSys.comapnyDao.create(google);
				google.setEmail("blabla@Goo.com");
				couponSys.comapnyDao.update(google);
				System.out.println();
//						couponSys.comapnyDao.getCompanyByNamePassword(google.getCompName(), google.getPassword()));
				couponSys.comapnyDao.delete(google.getId());
				couponSys.comapnyDao.create(new Company(4, "MaxInnovations", "tyrone", "man@oop.com"));
				couponSys.comapnyDao.create(new Company(5, "lizacorps", "123", "jjj@kkk.com"));
				couponSys.comapnyDao.create(new Company(6, "ilyacorps", "123", "jjj@66hy.com"));
				couponSys.comapnyDao.create(new Company(7, "yossicorps", "123", "jjj@cvbft.com"));
				couponSys.comapnyDao.create(new Company(8, "davidcorps", "123", "jjj@eryrt.com"));
			}

		private static Timestamp generateDate(long startYear, long endYear)
			{
				long offset = Timestamp.valueOf(startYear + "-01-01 00:00:00").getTime();
				long end = Timestamp.valueOf(endYear + "-01-01 00:00:00").getTime();
				long diff = end - offset + 1;
				Timestamp rand = new Timestamp(offset + (long) (Math.random() * diff));
				return rand;
			}
	}