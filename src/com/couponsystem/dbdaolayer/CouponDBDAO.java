package com.couponsystem.dbdaolayer;

import java.util.Collection;
import java.util.List;

import com.coupnsystem.db.DataBaseHandler;
import com.couponsystem.DAO.CouponDAO;
import com.couponsystem.beans.Coupon;
import com.couponsystem.exceptions.DAOException;
import com.couponsystem.exceptions.ErrorType;

@SuppressWarnings("unchecked")
public class CouponDBDAO implements CouponDAO
	{

		public CouponDBDAO()
			{
				super();
			}

		@Override
		public void create(Coupon coupon) throws DAOException
			{

				String addCustomer = "INSERT INTO Coupon(id, coup_title,"
						+ " start_date, end_date, amount, type, message," + " price, image) VALUES (?,?,?,?,?,?,?,?,?)";
				String MessageAction = new Object()
					{
					}.getClass().getEnclosingMethod().getName();
				DataBaseHandler.actionOnDataBase(addCustomer, MessageAction, coupon);

			}

		@Override
		public Coupon read(long couponId) throws DAOException
			{
				String getCoupon = "SELECT * From Coupon WHERE id=?";
				String MessageAction = new Object()
					{
					}.getClass().getEnclosingMethod().getName();
				List<Coupon> coupons = (List<Coupon>) DataBaseHandler.readInDataBase(getCoupon, MessageAction, "Coupon",
						couponId);
				return coupons.get(0);

			}

		@Override
		public boolean update(Coupon coupon) throws DAOException
			{
				String updateCoupon = "UPDATE Coupon SET endDate=?, price=? where id=?";
				String MessageAction = new Object(){}.getClass().getEnclosingMethod().getName();
				boolean didUpdateCoupon = DataBaseHandler.actionOnDataBase(updateCoupon, MessageAction,
						coupon.getEndDate(), coupon.getPrice(), coupon.getId());
				return didUpdateCoupon;

			}

		@Override
		public void delete(long couponId) throws DAOException
			{
				String deletCouponById = "DELETE FROM Coupon WHERE id= ?";
				String MessageAction = ErrorType.UNABLE_TO_REMOVE_COUPON + "couponId: " + couponId;
				boolean coupon = DataBaseHandler.actionOnDataBase(deletCouponById, MessageAction, couponId);
				if (coupon)
					MessageAction = "successfully deleted";
				System.out.println(MessageAction);


			}

		@Override
		public Collection<Coupon> getAll() throws DAOException
			{
				String getAllCoupon = "SELECT * FROM Coupon";
				String messageAction = ErrorType.UNABLE_TO_GET_COUPON + " ";
				List<Coupon> allCoupons = (List<Coupon>) DataBaseHandler.readInDataBase(getAllCoupon, messageAction,"Coupon");
				return allCoupons;


			}

		public Collection<Coupon> getCouponsByType(String couponType) throws DAOException
			{
				String MessageAction = new Object(){}.getClass().getEnclosingMethod().getName();
				String getAllCouponByTypeSQLstatement = "SELECT * FROM Coupons WHERE TYPE=?";
				return (Collection<Coupon>) DataBaseHandler.readInDataBase(getAllCouponByTypeSQLstatement, MessageAction, "Coupon", couponType);

			}

		@Override
		public void deleteCouponsOfCompanyFromCoupon(long companyId) throws DAOException
			{
				String deleteCompanyFromCompanyCouponTable = "DELETE FROM Company_coupon WHERE comp_id =? ";
				DataBaseHandler.actionOnDataBase(deleteCompanyFromCompanyCouponTable, "Company", companyId);

			}

		@Override
		public boolean updateCouponAmount(long couponId) throws DAOException
			{
				String updateCouponAmount = "UPDATE Coupon SET amount = amount-1 where id=?";
				String MessageAction = new Object()
				{
				}.getClass().getEnclosingMethod().getName();
				boolean didUpdateCouponAmount = DataBaseHandler.actionOnDataBase(updateCouponAmount, MessageAction, couponId);
				return didUpdateCouponAmount;
					

			}

		@Override
		public void deleteCouponFromCompanyCoupon(long couponId) throws DAOException
			{
				String removeCouponFromCompanyCouponSQLstatement = "DELETE FROM Company_coupon WHERE "
						+ "coup_id =?";
				String MassageAction = ErrorType.UNABLE_TO_REMOVE_COUPON + " : Coupons id : " + couponId
						+ " from all customers that has been purchased";
				DataBaseHandler.actionOnDataBase(removeCouponFromCompanyCouponSQLstatement, MassageAction, couponId);				
			}

		@Override
		public void deleteCouponFromCustomerCoupon(long couponId) throws DAOException
			{
				String removeCouponFromCustomerCouponSQLstatement = "DELETE FROM Customer_coupon WHERE "
						+ "coup_id =?";
				String MassageAction = ErrorType.UNABLE_TO_REMOVE_COUPON + " : Coupons id : " + couponId
						+ " from all customers that has been purchased";
				DataBaseHandler.actionOnDataBase(removeCouponFromCustomerCouponSQLstatement, MassageAction, couponId);				
			}



	}
