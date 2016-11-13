package com.couponsystem.system;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.couponsystem.DAO.CouponDAO;
import com.couponsystem.beans.Coupon;
import com.couponsystem.dbdaolayer.CouponDBDAO;
import com.couponsystem.exceptions.CouponSystemException;
import com.couponsystem.exceptions.DAOException;

public class DailyCouponExpirationTask extends Thread
	{

		private static DailyCouponExpirationTask expirationTask;
		private static int hourOfDay;
		private CouponDAO couponDao;
		private static boolean didAbort;

		public DailyCouponExpirationTask(int hourOfDay2)
			{
				super();
				couponDao = new CouponDBDAO();
				hourOfDay = hourOfDay2;
			}

		public static DailyCouponExpirationTask getInstance(int hourOfDay)
			{// 5 am, 1 time a day for example
				if (expirationTask == null)
					{
						expirationTask = new DailyCouponExpirationTask(hourOfDay);
					}
				return expirationTask;
			}

		@Override
		public void run()
			{
				try
					{
						executeCouponExpirationDeletion();
					}
				catch (CouponSystemException e)
					{
						System.out.println("There was an error: " + e.getMessage());
					}
			}

		private void executeCouponExpirationDeletion() throws CouponSystemException
			{

				while (!didAbort)
					{
						try
							{
								waitForDeletion();
								deleteCouponExpire();
							}
						catch (InterruptedException e)
							{
								if (!didAbort)
									{
										deleteCouponExpire();
										Thread.currentThread().interrupt();
									}
								else
									{
										System.out.println("Coupon Expire has been shut down, not running any more");
										throw new CouponSystemException(e.getMessage());

									}
							}
					}

			}



		private void deleteCouponExpire()
			{
				try
					{

						List<Coupon> coupons = (List<Coupon>) couponDao.getAll();
						if(coupons.get(0)==null){
							coupons=new ArrayList<>();
						}
						for (Coupon coupon : coupons )
							{
								if (coupon.getEndDate().before(getDate()))
									{
										this.couponDao.deleteCouponFromCompanyCoupon(coupon.getId());
										this.couponDao.deleteCouponFromCustomerCoupon(coupon.getId());
										this.couponDao.delete(coupon.getId());
									}
							}
					}
				catch (DAOException e)
					{
						System.out.println("Error has occured");
						System.out.println(e.getMessage());
						System.exit(0);
					}
			}

		private void waitForDeletion() throws InterruptedException
			{
				Thread.sleep(calculateSleepTime());
			}

		public long calculateSleepTime()
			{// use of java 8
				LocalDateTime localNow = LocalDateTime.now();
				ZoneId currentZone = ZoneId.of("Israel");
				ZonedDateTime zonedNow = ZonedDateTime.of(localNow, currentZone);
				ZonedDateTime zonedNext5;
				zonedNext5 = zonedNow.withHour(hourOfDay).withMinute(0).withSecond(0);
				if (zonedNow.compareTo(zonedNext5) > 0)
					zonedNext5 = zonedNext5.plusDays(1);
				Duration duration = Duration.between(zonedNow, zonedNext5);
				long initialDelay = duration.getSeconds();
				return initialDelay;
			}

		public void stopTask()
			{
				didAbort = true;
				interrupt();
			}

		public static java.sql.Date getDate()
			{
				java.util.Date utilDate = new java.util.Date();
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				return sqlDate;
			}

	}
