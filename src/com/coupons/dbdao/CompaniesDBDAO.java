package com.coupons.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.coupons.beans.Company;
import com.coupons.beans.Coupon;
import com.coupons.dao.CompaniesDAO;
import com.coupons.db.ConnectionMethod;
import com.coupons.db.ConnectionPool;

public class CompaniesDBDAO implements CompaniesDAO {

	private static final String GET_COMPANY_BY_EMAIL_PASSWORD_QUERY = "SELECT * FROM `coupons`.`companies` "
			+ "WHERE `email` = ? AND `password` = ?";

	private static final String ADD_COMPANY_QUERY = "INSERT INTO `coupons`.`companies` "
			+ "(`name`, `email`, `password`) VALUES (?, ?, ?);";

	private static final String GET_COMPANY_BY_EMAIL_OR_NAME_QUERY = "SELECT * FROM `coupons`.`companies` "
			+ "WHERE `email` = ? OR `name` = ?";

	private static final String UPDATE_COMPANY_QUERY = "UPDATE `coupons`.`companies` "
			+ "SET `name` = ? , `email` = ? , `password` = ? WHERE (`id` = ?);";

	private static final String DELETE_COMPANY_QUERY = "DELETE FROM `coupons`.`companies` " + "WHERE (`id` = ?);";

	private static final String GET_ALL_COMPANIES_QUERY = "SELECT * FROM `coupons`.`companies`";

	private static final String GET_COMPANY_QUERY = "SELECT * FROM `coupons`.`companies` WHERE `id` = ?";

	private Connection conn;

	public CompaniesDBDAO() {
	}

	public CompaniesDBDAO(Connection conn) {
		this.conn = conn;
	}

	private <T> T invokeConnectionMethod(ConnectionMethod<T> method) {
		if (conn != null) {
			return method.run(conn);
		} else {
			return ConnectionPool.instanceInvoke(method);
		}
	}

	@Override
	public boolean isCompanyExists(String email, String password) {
		ConnectionMethod<Boolean> method = (Connection conn) -> {
			try {
				PreparedStatement statement = conn.prepareStatement(GET_COMPANY_BY_EMAIL_PASSWORD_QUERY);
				statement.setString(1, email);
				statement.setString(2, password);
				return statement.executeQuery().next();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		};

		return invokeConnectionMethod(method);
	}

	@Override
	public Company getCompanyByEmailPassword(String email, String password) {
		ConnectionMethod<Company> method = (Connection conn) -> {
			try {
				PreparedStatement statement = conn.prepareStatement(GET_COMPANY_BY_EMAIL_PASSWORD_QUERY);
				statement.setString(1, email);
				statement.setString(2, password);
				ResultSet resultSet = statement.executeQuery();
				if (resultSet.next()) {
					return getOneCompany(resultSet.getInt("id"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		};

		return invokeConnectionMethod(method);
	}

	@Override
	public void addCompany(Company company) {
		ConnectionMethod<?> method = (Connection conn) -> {
			try {
				PreparedStatement statement = conn.prepareStatement(ADD_COMPANY_QUERY);
				statement.setString(1, company.getName());
				statement.setString(2, company.getEmail());
				statement.setString(3, company.getPassword());
				statement.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		};

		invokeConnectionMethod(method);
	}

	@Override
	public boolean canAddCompany(String email, String name) {
		ConnectionMethod<Boolean> method = (Connection conn) -> {
			try {
				PreparedStatement statement = conn.prepareStatement(GET_COMPANY_BY_EMAIL_OR_NAME_QUERY);
				statement.setString(1, email);
				statement.setString(2, name);
				return !statement.executeQuery().next();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return true;
		};

		return invokeConnectionMethod(method);
	}

	@Override
	public void updateCompany(Company company) {
		ConnectionMethod<?> method = (Connection conn) -> {
			try {
				PreparedStatement statement = conn.prepareStatement(UPDATE_COMPANY_QUERY);
				statement.setString(1, company.getName());
				statement.setString(2, company.getEmail());
				statement.setString(3, company.getPassword());
				statement.setInt(4, company.getId());
				statement.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		};

		invokeConnectionMethod(method);
	}

	@Override
	public void deleteCompany(int companyId) {
		ConnectionMethod<?> method = (Connection conn) -> {
			try {
				PreparedStatement statement = conn.prepareStatement(DELETE_COMPANY_QUERY);
				statement.setInt(1, companyId);
				statement.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		};

		invokeConnectionMethod(method);
	}

	@Override
	public List<Company> getAllCompanies() {
		ConnectionMethod<List<Company>> method = (Connection conn) -> {
			List<Company> companies = new ArrayList<>();
			try {
				PreparedStatement statement = conn.prepareStatement(GET_ALL_COMPANIES_QUERY);
				CouponsDBDAO couponsDBDAO = new CouponsDBDAO(conn);
				ResultSet companyResult = statement.executeQuery();
				while (companyResult.next()) {
					int companyId = companyResult.getInt("id");
					List<Coupon> companyCoupons = couponsDBDAO.getCompanyCoupons(companyId);
					companies.add(new Company(companyId, companyResult.getString("name"),
							companyResult.getString("email"), companyResult.getString("password"), companyCoupons));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return companies;
		};

		return invokeConnectionMethod(method);
	}

	@Override
	public Company getOneCompany(int companyId) {
		ConnectionMethod<Company> method = (Connection conn) -> {
			try {
				PreparedStatement statement = conn.prepareStatement(GET_COMPANY_QUERY);
				statement.setInt(1, companyId);
				CouponsDBDAO couponsDBDAO = new CouponsDBDAO(conn);
				ResultSet companyResult = statement.executeQuery();
				if (companyResult.next()) {
					List<Coupon> companyCoupons = couponsDBDAO.getCompanyCoupons(companyId);
					return new Company(companyId, companyResult.getString("name"), companyResult.getString("email"),
							companyResult.getString("password"), companyCoupons);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		};

		return invokeConnectionMethod(method);
	}

}
