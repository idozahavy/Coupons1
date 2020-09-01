package com.coupons.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.coupons.beans.Coupon;
import com.coupons.beans.Customer;
import com.coupons.dao.CustomersDAO;
import com.coupons.db.ConnectionMethod;
import com.coupons.db.ConnectionPool;

public class CustomerDBDAO implements CustomersDAO {

	private static final String GET_CUSTOMER_BY_EMAIL_PASSWORD_QUERY = "SELECT * FROM `coupons`.`customers` "
			+ "WHERE `email` = ? AND `password` = ?";

	private static final String GET_ALL_CUSTOMERS_QUERY = "SELECT * FROM `coupons`.`customers` ";

	private static final String GET_CUSTOMER_QUERY = "SELECT * FROM `coupons`.`customers` WHERE `id` = ?";

	private static final String GET_CUSTOMER_BY_EMAIL_QUERY = "SELECT * FROM `coupons`.`customers` WHERE `email` = ?";

	private static final String ADD_CUSTOMER_QUERY = "INSERT INTO `coupons`.`customers` "
			+ "(`first_name`, `last_name`, `email`, `password`) VALUES (?, ?, ?, ?);";

	private static final String UPDATE_CUSTOMER_QUERY = "UPDATE `coupons`.`customers` "
			+ "SET `first_name` = ?, `last_name` = ?, `email` = ?, `password` = ? WHERE `id` = ?";

	private static final String DELETE_CUSTOMER_QUERY = "DELETE FROM `coupons`.`customers` WHERE `id` = ?";

	private Connection conn;

	public CustomerDBDAO() {
	}

	public CustomerDBDAO(Connection conn) {
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
	public boolean isCustomerExists(String email, String password) {
		ConnectionMethod<Boolean> method = (Connection conn) -> {
			try {
				PreparedStatement statement = conn.prepareStatement(GET_CUSTOMER_BY_EMAIL_PASSWORD_QUERY);
				statement.setString(1, email);
				statement.setString(2, password);
				return statement.executeQuery().next();
			} catch (SQLException e) {
				System.out.println("Error - " + e.getMessage());
			}
			return false;
		};

		return invokeConnectionMethod(method);
	}

	@Override
	public Customer GetCustomerByEmailPassword(String email, String password) {
		ConnectionMethod<Customer> method = (Connection conn) -> {
			try {
				PreparedStatement statement = conn.prepareStatement(GET_CUSTOMER_BY_EMAIL_PASSWORD_QUERY);
				statement.setString(1, email);
				statement.setString(2, password);
				CouponsDBDAO couponsDBDAO = new CouponsDBDAO(conn);
				ResultSet resultSet = statement.executeQuery();
				if (resultSet.next()) {
					int customerId = resultSet.getInt("id");
					List<Coupon> customerCoupons = couponsDBDAO.getCustomerCoupons(customerId);
					return new Customer(customerId, resultSet.getString("first_name"), resultSet.getString("last_name"),
							resultSet.getString("email"), resultSet.getString("password"), customerCoupons);
				}
			} catch (SQLException e) {
				System.out.println("Error - " + e.getMessage());
			}
			return null;
		};

		return invokeConnectionMethod(method);
	}

	@Override
	public boolean canAddCustomer(String email) {
		ConnectionMethod<Boolean> method = (Connection conn) -> {
			try {
				PreparedStatement statement = conn.prepareStatement(GET_CUSTOMER_BY_EMAIL_QUERY);
				statement.setString(1, email);
				return !statement.executeQuery().next();
			} catch (SQLException e) {
				System.out.println("Error - " + e.getMessage());
			}
			return true;
		};

		return invokeConnectionMethod(method);
	}

	@Override
	public void addCustomer(Customer customer) {
		ConnectionMethod<?> method = (Connection conn) -> {
			try {
				PreparedStatement statement = conn.prepareStatement(ADD_CUSTOMER_QUERY);
				statement.setString(1, customer.getFirstName());
				statement.setString(2, customer.getLastName());
				statement.setString(3, customer.getEmail());
				statement.setString(4, customer.getPassword());
				statement.execute();
			} catch (SQLException e) {
				System.out.println("Error - " + e.getMessage());
			}
			return null;
		};

		invokeConnectionMethod(method);
	}

	@Override
	public void updateCustomer(Customer customer) {
		ConnectionMethod<?> method = (Connection conn) -> {
			try {
				PreparedStatement statement = conn.prepareStatement(UPDATE_CUSTOMER_QUERY);
				statement.setString(1, customer.getFirstName());
				statement.setString(2, customer.getLastName());
				statement.setString(3, customer.getEmail());
				statement.setString(4, customer.getPassword());
				statement.setInt(5, customer.getId());
				statement.execute();
			} catch (SQLException e) {
				System.out.println("Error - " + e.getMessage());
			}
			return null;
		};

		invokeConnectionMethod(method);
	}

	@Override
	public void deleteCustomer(int customerId) {
		ConnectionMethod<?> method = (Connection conn) -> {
			try {
				PreparedStatement statement = conn.prepareStatement(DELETE_CUSTOMER_QUERY);
				statement.setInt(1, customerId);
				statement.execute();
			} catch (SQLException e) {
				System.out.println("Error - " + e.getMessage());
			}
			return null;
		};

		invokeConnectionMethod(method);
	}

	@Override
	public List<Customer> getAllCustomers() {
		ConnectionMethod<List<Customer>> method = (Connection conn) -> {
			List<Customer> customers = new ArrayList<>();
			try {
				PreparedStatement statement = conn.prepareStatement(GET_ALL_CUSTOMERS_QUERY);
				CouponsDBDAO couponsDBDAO = new CouponsDBDAO(conn);
				ResultSet customerResult = statement.executeQuery();
				while (customerResult.next()) {
					int customerId = customerResult.getInt("id");
					List<Coupon> customerCoupons = couponsDBDAO.getCustomerCoupons(customerId);
					customers.add(new Customer(customerId, customerResult.getString("first_name"),
							customerResult.getString("last_name"), customerResult.getString("email"),
							customerResult.getString("password"), customerCoupons));
				}
			} catch (SQLException e) {
				System.out.println("Error - " + e.getMessage());
			}
			return customers;
		};

		return invokeConnectionMethod(method);
	}

	@Override
	public Customer getOneCustomer(int customerId) {
		ConnectionMethod<Customer> method = (Connection conn) -> {
			try {
				PreparedStatement statement = conn.prepareStatement(GET_CUSTOMER_QUERY);
				CouponsDBDAO couponsDBDAO = new CouponsDBDAO(conn);
				statement.setInt(1, customerId);
				ResultSet customerResult = statement.executeQuery();
				if (customerResult.next()) {
					List<Coupon> customerCoupons = couponsDBDAO.getCustomerCoupons(customerId);
					return new Customer(customerId, customerResult.getString("first_name"),
							customerResult.getString("last_name"), customerResult.getString("email"),
							customerResult.getString("password"), customerCoupons);
				}
			} catch (SQLException e) {
				System.out.println("Error - " + e.getMessage());
			}
			return null;
		};

		return invokeConnectionMethod(method);
	}

}
