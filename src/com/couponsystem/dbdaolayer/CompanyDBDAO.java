package com.couponsystem.dbdaolayer;

import java.util.Collection;
import java.util.List;

import com.coupnsystem.db.DataBaseHandler;
import com.couponsystem.DAO.CompanyDAO;
import com.couponsystem.beans.Company;
import com.couponsystem.beans.Coupon;
import com.couponsystem.exceptions.DAOException;
import com.couponsystem.exceptions.ErrorType;

/**
 * 
 * @author KubaL This class handles the creation, reading, updating and deleting
 *         companies from the DB
 */
@SuppressWarnings("unchecked")
public class CompanyDBDAO implements CompanyDAO

	{

		public CompanyDBDAO()
			{
				super();
			}

		@Override
		public void create(Company company) throws DAOException
			{
				String addCompany = "INSERT INTO Company(comp_name, password, email) VALUES (?,?,?)";
				DataBaseHandler.actionOnDataBase(addCompany, "Company", company.getCompName(), company.getPassword(),
						company.getEmail());
			}

		@Override
		// Get company by ID
		public Company read(long id) throws DAOException
			{
				String getCompany = "SELECT * From Company WHERE id =? ";
				List<Company> companies = (List<Company>) DataBaseHandler.readInDataBase(getCompany, "read company",
						"Company", id);
				return companies.get(0);
			}

		@Override
		// Get company by Name
		public Company getCompanyByName(String companyName) throws DAOException
			{
				String getCompanyByName = "SELECT * From Company WHERE comp_name=? ";
				String MessageAction = new Object(){}.getClass().getEnclosingMethod().getName();
				List<Company> companiesByName = (List<Company>) DataBaseHandler.readInDataBase(getCompanyByName,
						MessageAction, "Company", companyName);
				return companiesByName.get(0);
			}

		@Override
		public boolean update(Company company) throws DAOException
			{

				String updateCompany = "UPDATE Company SET password=? , email=? WHERE id=?";
				String MessageAction = new Object(){}.getClass().getEnclosingMethod().getName();
				boolean didUpdateCompany = DataBaseHandler.actionOnDataBase(updateCompany, MessageAction,
						company.getPassword(), company.getEmail(), company.getId());
				return didUpdateCompany;
			}

		@Override
		public void delete(long companyId) throws DAOException
			{
				String deletCompanyById = "DELETE FROM Company WHERE id= ?";
				String MessageAction = ErrorType.UNABLE_TO_REMOVE_COMPANY + "companyId: " + companyId;
				boolean company = DataBaseHandler.actionOnDataBase(deletCompanyById, MessageAction, companyId);
				if (company)
					MessageAction = "successfully deleted";
				System.out.println(MessageAction);

			}

		@Override
		public void deleteCouponsFromCompanyFromJoin(long companyId) throws DAOException
			{
				String removeCompanySQLstatement = "DELETE FROM Coupon WHERE "
						+ "Coupon.id IN ( SELECT coup_id FROM Company_coupon WHERE " + "Company_coupon.comp_id=?)";
				String MassageAction = ErrorType.UNABLE_TO_REMOVE_COUPON + " : Coupons of Company id : " + companyId
						+ " from all customers that has been purchased";
				DataBaseHandler.actionOnDataBase(removeCompanySQLstatement, MassageAction, companyId);

			}

		@Override
		public Collection<Company> getAll() throws DAOException
			{
				String getAllCompenies = "SELECT * FROM Company";
				String messageAction = ErrorType.UNABLE_TO_GET_COMPANIES + " ";
				List<Company> allCompanies = (List<Company>) DataBaseHandler.readInDataBase(getAllCompenies,
						messageAction, "Company");
				return allCompanies;
			}

		@Override
		public Collection<Coupon> getAllCouponsOfCompany(long companyId) throws DAOException
			{
				String getAllCouponCompaniesSQLstatement = "SELECT * FROM Coupon WHERE  "
						+ "Coupon.id IN ( SELECT coup_id FROM Company_coupons WHERE " + "Company_coupons.id=?)";
				String MessageAction = new Object()
					{
					}.getClass().getEnclosingMethod().getName();
				return (Collection<Coupon>) DataBaseHandler.readInDataBase(getAllCouponCompaniesSQLstatement,
						MessageAction, "Coupon", companyId);
			}

		@Override
		public Coupon getCoupon(long id) throws DAOException
			{
				String getCouponsId = "SELECT coup_id FROM Company_Coupon WHERE comp_id=?";
				String MessageAction = new Object()
					{
					}.getClass().getEnclosingMethod().getName();
				List<Coupon> coupons = (List<Coupon>) DataBaseHandler.readInDataBase(getCouponsId, MessageAction,
						"Coupon", id);
				return coupons.get(0);

			}

		@Override
		public Collection<Coupon> getCouponsFromCompanyCouponByPrice(long companyId, double price) throws DAOException
			{
				String MessageAction = new Object()
					{
					}.getClass().getEnclosingMethod().getName();
				String getAllCouponCompaniesSQLstatement = "SELECT * FROM Coupons WHERE  "
						+ "Coupons.id IN ( SELECT coup_id FROM Company_coupons WHERE "
						+ "Company_coupons.id=?) AND price <=?";
				return (Collection<Coupon>) DataBaseHandler.readInDataBase(getAllCouponCompaniesSQLstatement,
						MessageAction, "Coupon", companyId, price);

			}

		@Override
		public void createCouponToCompany(long companyID, long couponID) throws DAOException
			{
				String purchaseCoupon = "INSERT INTO Company_coupon (comp_id, coup_id) " + "VALUES(?,?) ";
				DataBaseHandler.actionOnDataBase(purchaseCoupon, "Coupon", companyID, couponID);

			}

		@Override
		public void unPurchaseCouponToCompany(long companyID, long couponID) throws DAOException
			{
				String unPurchaseCoupon = "DELETE FROM Company_coupon WHERE comp_id = ? and coup_id=? ";
				DataBaseHandler.actionOnDataBase(unPurchaseCoupon, "Coupon", companyID, couponID);

			}

		@Override
		public void deleteCompanyFromJoin(long companyID) throws DAOException
			{
				String deleteCompanyFromCompanyCouponTable = "DELETE FROM Company_coupon WHERE comp_id =? ";
				DataBaseHandler.actionOnDataBase(deleteCompanyFromCompanyCouponTable, "Company", companyID);
			}

		@Override
		public Company login(String comp_name, String password) throws DAOException
			{

				String login = "SELECT * FROM Company WHERE comp_name=? AND password=?";
				List<Company> companies = (List<Company>) DataBaseHandler.readInDataBase(login, "Logging in", "Company",
						comp_name, password);
				return companies.get(0);
			}

		@Override
		public void deleteCouponFromCompany(long couponId) throws DAOException
			{
				String deleteCouponFromCompany = "DELETE FROM Company_coupon WHERE coup_id =?";
				String MessageAction = new Object()
					{
					}.getClass().getEnclosingMethod().getName();
				DataBaseHandler.actionOnDataBase(deleteCouponFromCompany, MessageAction, couponId);

			}

	}
