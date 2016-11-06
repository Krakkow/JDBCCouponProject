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
				// Connection con = pool.getConnection();
				//
				// try
				// {
				// String addCoupon = "INSERT INTO Coupon(id, coup_title,"
				// + " start_date, end_date, amount, type, message,"
				// + " price, image) VALUES (?,?,?,?,?,?,?,?,?)";
				// PreparedStatement pstmt = con.prepareStatement(addCoupon);
				// pstmt.setLong(1, coupon.getId());
				// pstmt.setString(2, coupon.getTitle());
				// pstmt.setDate(3, coupon.getStartDate());
				// pstmt.setDate(4, coupon.getEndDate());
				// pstmt.setInt(5, coupon.getAmount());
				// pstmt.setString(6, coupon.getType());
				// pstmt.setString(7, coupon.getMessage());
				// pstmt.setDouble(8, coupon.getPrice());
				// pstmt.setString(9, coupon.getImage());
				// pstmt.execute();
				// System.out.println("The Coupon " + coupon.getTitle() + " was
				// added successfully!");
				// }
				// catch (SQLException e)
				// {
				// System.err.println("Could not add new coupon to DB " +
				// e.getMessage());
				// }
				// finally
				// {
				// pool.returnConnection(con);
				// System.out.println("Connection was returned to pool
				// successfully");
				// }

			}

		@Override
		public Coupon read(long couponId) throws DAOException
			{
				// Connection con = pool.getConnection();
				String getCoupon = "SELECT * From Coupon WHERE id=?";
				String MessageAction = new Object()
					{
					}.getClass().getEnclosingMethod().getName();
				List<Coupon> coupons = (List<Coupon>) DataBaseHandler.readInDataBase(getCoupon, MessageAction, "Coupon",
						couponId);
				return coupons.get(0);
				//
				// Coupon coupon = new Coupon();
				// try
				// {
				// Statement stmt = con.createStatement();
				// ResultSet rs = stmt.executeQuery(getCoupon);
				//
				// if (rs.next())
				// {
				// coupon.setId(id);
				// coupon.setTitle(rs.getString(1));
				// coupon.setStartDate(rs.getDate(2));
				// coupon.setEndDate(rs.getDate(3));
				// coupon.setAmount(rs.getInt(4));
				// coupon.setType(rs.getString(5));
				// coupon.setMessage(rs.getString(6));
				// coupon.setPrice(rs.getDouble(7));
				// coupon.setImage(rs.getString(8));
				// }
				// else
				// {
				// throw new SQLException("Requested Coupon could not be
				// retrieved with id: " +
				// + id + " was not found in database");
				// }
				// System.out.println("Requested Coupon has been retrieved
				// successfully.");
				//
				// }
				// catch (SQLException e)
				// {
				// System.err.println("Query from Database has failed. " +
				// e.getMessage());
				// }
				// finally
				// {
				// pool.returnConnection(con);
				// System.out.println("Connection was returned to pool
				// successfully");
				// }
				//
				// return coupon;
			}

		@Override
		public boolean update(Coupon coupon) throws DAOException
			{
				String updateCoupon = "UPDATE Coupon SET endDate=?, price=? where id=?";
				String MessageAction = new Object()
					{
					}.getClass().getEnclosingMethod().getName();
				boolean didUpdateCoupon = DataBaseHandler.actionOnDataBase(updateCoupon, MessageAction,
						coupon.getEndDate(), coupon.getPrice(), coupon.getId());
				return didUpdateCoupon;

				// Connection con = pool.getConnection();
				// String updateCoupon = "UPDATE Couon where id='" +
				// coupon.getId() + "',"
				// + " coup_title='" + coupon.getTitle() +"',"
				// + " start_date='" + coupon.getStartDate() + "',"
				// + " end_date='" + coupon.getEndDate() + "',"
				// + " amount='" + coupon.getAmount() + "',"
				// + " type='" + coupon.getType() + "',"
				// + " message='" + coupon.getMessage() + "',"
				// + " price='" + coupon.getPrice() + "',"
				// + " image='" + coupon.getImage() + "'";
				//
				// ;
				// try
				// { Statement stmt = con.createStatement();
				// stmt.executeUpdate(updateCoupon);
				// System.out.println("Coupon " + coupon.getId() + " " +
				// coupon.getTitle() + " was updated successfully!");
				// } catch (SQLException e)
				// {
				// System.err.println("Unable to update coupon " +
				// coupon.getId() + " " + coupon.getTitle() + e.getMessage());
				// }
				// finally
				// {
				// pool.returnConnection(con);
				// System.out.println("Connection was returned to pool
				// successfully");
				// }
				// return false;
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

				// Connection con = pool.getConnection();
				//
				// String removeCoupon = "DELETE FROM Coupon WHERE ID=" +
				// couponID;
				// try
				// {
				// Statement stmt = con.createStatement();
				// stmt.executeUpdate(removeCoupon);
				// System.out.println("Coupon " + couponID + " was removed from
				// DB successfully!");
				// }
				// catch (SQLException e)
				// {
				// System.err.println("Could not delete coupon from DB. " +
				// e.getMessage());
				// }
				// finally
				// {
				// pool.returnConnection(con);
				// System.out.println("Connection was returned to pool
				// successfully");
				// }
			}

		@Override
		public Collection<Coupon> getAll() throws DAOException
			{
				String getAllCoupon = "SELECT * FROM Coupon";
				String messageAction = ErrorType.UNABLE_TO_GET_COUPON + " ";
				List<Coupon> allCoupons = (List<Coupon>) DataBaseHandler.readInDataBase(getAllCoupon, messageAction,"Coupon");
				return allCoupons;
				// Connection con = pool.getConnection();
				// String getAllCoupons = "SELECT * FROM Coupon";
				// List<Coupon> allCoupons = new ArrayList<Coupon>();
				// try
				// {
				// Statement stmt = con.createStatement();
				// ResultSet rs = stmt.executeQuery(getAllCoupons);
				//
				// while (rs.next())
				// {
				// Coupon coupon = new Coupon();
				// coupon.setId(rs.getLong(1));
				// coupon.setTitle(rs.getString(2));
				// coupon.setStartDate(rs.getDate(3));
				// coupon.setEndDate(rs.getDate(4));
				// coupon.setAmount(rs.getInt(5));
				// coupon.setType(rs.getString(6));
				// coupon.setMessage(rs.getString(7));
				// coupon.setPrice(rs.getDouble(8));
				// coupon.setImage(rs.getString(9));
				// allCoupons.add(coupon);
				// }
				// System.out.println("All coupon has been retrieved
				// successfully!\n " + allCoupons);
				//
				// }
				// catch (SQLException e)
				// {
				// System.err.println("Could not retrieve all coupons. " +
				// e.getMessage());
				// }
				// finally
				// {
				// pool.returnConnection(con);
				// }
				// return allCoupons;

			}

		public Collection<Coupon> getCouponsByType(String couponType) throws DAOException
			{
				String MessageAction = new Object(){}.getClass().getEnclosingMethod().getName();
				String getAllCouponByTypeSQLstatement = "SELECT * FROM Coupons WHERE TYPE=?";
				return (Collection<Coupon>) DataBaseHandler.readInDataBase(getAllCouponByTypeSQLstatement, MessageAction, "Coupon", couponType);
//				Connection con = pool.getConnection();
//				String couponsByType = "SELECT * FROM Coupon WHERE type='" + couponType + "'";
//				List<Coupon> couponsType = new ArrayList<Coupon>();
//
//				try
//					{
//						Statement stmt = con.createStatement();
//						ResultSet rs = stmt.executeQuery(couponsByType);
//						while (rs.next())
//							{
//								Coupon coupon = new Coupon();
//								coupon.setId(rs.getLong(1));
//								coupon.setTitle(rs.getString(2));
//								coupon.setStartDate(rs.getDate(3));
//								coupon.setEndDate(rs.getDate(4));
//								coupon.setAmount(rs.getInt(5));
//								coupon.setType(rs.getString(6));
//								coupon.setMessage(rs.getString(7));
//								coupon.setPrice(rs.getDouble(8));
//								coupon.setImage(rs.getString(9));
//								couponsType.add(coupon);
//							}
//						System.out.println("All coupon from type " + couponType + " has been retrieved successfully!\n"
//								+ couponsType);
//					}
//				catch (SQLException e)
//					{
//						System.err.println("Could not retrieve coupons by type from DB." + e.getMessage());
//					}
//				finally
//					{
//						pool.returnConnection(con);
//						System.out.println("Connection was returned to pool successfully");
//					}
//
//				return couponsType;
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

	}
