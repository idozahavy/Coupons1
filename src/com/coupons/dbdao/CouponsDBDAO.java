package com.coupons.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.coupons.beans.Category;
import com.coupons.beans.Coupon;
import com.coupons.dao.CouponsDAO;
import com.coupons.db.ConnectionMethod;
import com.coupons.db.ConnectionPool;

public class CouponsDBDAO implements CouponsDAO {

	private static final String ADD_COUPON_QUERY = "INSERT INTO `coupons`.`coupons` "
			+ "(`company_id`, `category_id`, `title`, `description`, `start_date`, `end_date`, `amount`, `price`, `image`) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
	
	private static final String GET_COUPON_BY_TITLE_AND_COMPANY_ID = "SELECT * FROM `coupons`.`coupons` "
			+ "WHERE `title` = ? AND `company_id` = ?";

	private static final String UPDATE_COUPON_QUERY = "UPDATE `coupons`.`coupons` "
			+ "SET `company_id` = ? , `category_id` = ? , `title` = ? , `description` = ? ,"
			+ " `start_date` = ? , `end_date` = ? , `amount` = ? , `price` = ? , `image` = ? WHERE `id` = ?;";

	private static final String DELETE_COUPON_QUERY = "DELETE FROM `coupons`.`coupons` WHERE `id` = ?";

	private static final String GET_COUPON_QUERY = "SELECT * FROM `coupons`.`coupons` WHERE `id` = ?";

	private static final String ADD_COUPON_PURCHASE_QUERY = "INSERT INTO `coupons`.`customers_vs_coupons` "
			+ "(`customer_id`,`coupon_id`) VALUES(?,?)";
	
	private static final String DELETE_COUPON_PURCHASE_QUERY = "DELETE FROM `coupons`.`customers_vs_coupons` "
			+ "WHERE `customer_id` = ? AND `coupon_id` = ?";

	private static final String DELETE_COUPON_PURCHASES_QUERY = "DELETE FROM `coupons`.`customers_vs_coupons` "
			+ "WHERE `coupon_id` = ?";

	private static final String GET_ALL_COUPONS_QUERY = "SELECT * FROM `coupons`.`coupons` ";
	
	private static final String GET_CUSTOMER_COUPONS_QUERY = "SELECT cp.* "
			+ "FROM `coupons`.`coupons` AS cp, `coupons`.`customers_vs_coupons` AS cvc "
			+ "WHERE cvc.`coupon_id` = cp.`id` "
			+ "AND cvc.`customer_id` = ?";
	
	private static final String GET_COMPANY_COUPONS_QUERY = "SELECT * "
			+ "FROM `coupons`.`coupons` "
			+ "WHERE `company_id` = ? ";
	

	private Connection conn;

	public CouponsDBDAO() {
	}

	public CouponsDBDAO(Connection conn) {
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
	public void addCoupon(Coupon coupon) {
		ConnectionMethod<?> method = (Connection conn) -> {
			try {
				PreparedStatement statement = conn.prepareStatement(ADD_COUPON_QUERY);
				statement.setInt(1, coupon.getCompanyId());
				CategoryDBDAO categoryDBDAO = new CategoryDBDAO(conn);
				int categoryId = categoryDBDAO.getCategoryId(coupon.getCategory());
				statement.setInt(2, categoryId);
				statement.setString(3, coupon.getTitle());
				statement.setString(4, coupon.getDescription());
				statement.setDate(5, coupon.getStartDate());
				statement.setDate(6, coupon.getEndDate());
				statement.setInt(7, coupon.getAmount());
				statement.setDouble(8, coupon.getPrice());
				statement.setString(9, coupon.getImage());
				statement.execute();
			} catch (SQLException e) {
				System.out.println("Error - " + e.getMessage());
			}
			return null;
		};

		invokeConnectionMethod(method);
	}

	@Override
	public boolean canAddCoupon(String title, int companyId) {
		ConnectionMethod<Boolean> method = (Connection conn) -> {
			try {
				PreparedStatement statement = conn.prepareStatement(GET_COUPON_BY_TITLE_AND_COMPANY_ID);
				statement.setString(1, title);
				statement.setInt(2, companyId);
				return !statement.executeQuery().next();
			} catch (SQLException e) {
				System.out.println("Error - " + e.getMessage());
			}
			return true;
		};

		return invokeConnectionMethod(method);
	}

	@Override
	public void updateCoupon(Coupon coupon) {
		ConnectionMethod<?> method = (Connection conn) -> {
			try {
				PreparedStatement statement = conn.prepareStatement(UPDATE_COUPON_QUERY);
				statement.setInt(1, coupon.getCompanyId());
				CategoryDBDAO categoryDBDAO = new CategoryDBDAO(conn);
				int categoryId = categoryDBDAO.getCategoryId(coupon.getCategory());
				statement.setInt(2, categoryId);
				statement.setString(3, coupon.getTitle());
				statement.setString(4, coupon.getDescription());
				statement.setDate(5, coupon.getStartDate());
				statement.setDate(6, coupon.getEndDate());
				statement.setInt(7, coupon.getAmount());
				statement.setDouble(8, coupon.getPrice());
				statement.setString(9, coupon.getImage());
				statement.setInt(10, coupon.getId());
				statement.executeUpdate();
			} catch (SQLException e) {
				System.out.println("Error - " + e.getMessage());
			}
			return null;
		};

		invokeConnectionMethod(method);
	}

	@Override
	public void deleteCoupon(int couponId) {
		ConnectionMethod<?> method = (Connection conn) -> {
			try {
				PreparedStatement statement = conn.prepareStatement(DELETE_COUPON_QUERY);
				statement.setInt(1, couponId);
				statement.execute();
			} catch (SQLException e) {
				System.out.println("Error - " + e.getMessage());
			}
			return null;
		};

		invokeConnectionMethod(method);
	}

	@Override
	public Coupon getOneCoupon(int couponId) {
		ConnectionMethod<Coupon> method = (Connection conn) -> {
			try {
				PreparedStatement statement = conn.prepareStatement(GET_COUPON_QUERY);
				statement.setInt(1, couponId);
				ResultSet result = statement.executeQuery();
				if (result.next()) {
					CategoryDBDAO categoryDBDAO = new CategoryDBDAO(conn);
					Category couponCategory = categoryDBDAO.getCategory(result.getInt("category_id"));
					return new Coupon(result.getInt("id"), result.getInt("company_id"), couponCategory,
							result.getString("title"), result.getString("description"), result.getDate("start_date"),
							result.getDate("end_date"), result.getInt("amount"), result.getDouble("price"),
							result.getString("image"));
				}
			} catch (SQLException e) {
				System.out.println("Error - " + e.getMessage());
			}
			return null;
		};

		return invokeConnectionMethod(method);
	}

	@Override
	public void addCouponPurchase(int customerId, int couponId) {
		ConnectionMethod<?> method = (Connection conn) -> {
			try {
				PreparedStatement statement = conn.prepareStatement(ADD_COUPON_PURCHASE_QUERY);
				statement.setInt(1, customerId);
				statement.setInt(2, couponId);
				statement.execute();
			} catch (SQLException e) {
				System.out.println("Error - " + e.getMessage());
			}
			return null;
		};

		invokeConnectionMethod(method);
	}

	@Override
	public void deleteCouponPurchase(int customerId, int couponId) {
		ConnectionMethod<?> method = (Connection conn) -> {
			try {
				PreparedStatement statement = conn.prepareStatement(DELETE_COUPON_PURCHASE_QUERY);
				statement.setInt(1, customerId);
				statement.setInt(2, couponId);
				statement.execute();
			} catch (SQLException e) {
				System.out.println("Error - " + e.getMessage());
			}
			return null;
		};

		invokeConnectionMethod(method);
	}
	
	@Override
	public void deleteCouponPurchases(int couponId) {
		ConnectionMethod<?> method = (Connection conn) -> {
			try {
				PreparedStatement statement = conn.prepareStatement(DELETE_COUPON_PURCHASES_QUERY);
				statement.setInt(1, couponId);
				statement.execute();
			} catch (SQLException e) {
				System.out.println("Error - " + e.getMessage());
			}
			return null;
		};

		invokeConnectionMethod(method);
	}

	@Override
	public List<Coupon> getAllCoupons() {
		ConnectionMethod<List<Coupon>> method = (Connection conn) -> {
			List<Coupon> coupons = new ArrayList<>();
			try {
				PreparedStatement statement = conn.prepareStatement(GET_ALL_COUPONS_QUERY);
				ResultSet result = statement.executeQuery();
				while (result.next()) {
					CategoryDBDAO categoryDBDAO = new CategoryDBDAO(conn);
					Category couponCategory = categoryDBDAO.getCategory(result.getInt("category_id"));
					coupons.add(new Coupon(result.getInt("id"), result.getInt("company_id"), couponCategory,
							result.getString("title"), result.getString("description"), result.getDate("start_date"),
							result.getDate("end_date"), result.getInt("amount"), result.getDouble("price"),
							result.getString("image")));
				}
			} catch (SQLException e) {
				System.out.println("Error - " + e.getMessage());
			}
			return coupons;
		};

		return invokeConnectionMethod(method);
	}

	@Override
	public List<Coupon> getCustomerCoupons(int customerId) {
		ConnectionMethod<List<Coupon>> method = (Connection conn) -> {
			List<Coupon> coupons = new ArrayList<>();
			try {
				PreparedStatement statement = conn.prepareStatement(GET_CUSTOMER_COUPONS_QUERY);
				statement.setInt(1, customerId);
				ResultSet result = statement.executeQuery();
				while (result.next()) {
					CategoryDBDAO categoryDBDAO = new CategoryDBDAO(conn);
					Category couponCategory = categoryDBDAO.getCategory(result.getInt("category_id"));
					coupons.add(new Coupon(result.getInt("id"), result.getInt("company_id"), couponCategory,
							result.getString("title"), result.getString("description"), result.getDate("start_date"),
							result.getDate("end_date"), result.getInt("amount"), result.getDouble("price"),
							result.getString("image")));
				}
			} catch (SQLException e) {
				System.out.println("Error - " + e.getMessage());
			}
			return coupons;
		};

		return invokeConnectionMethod(method);
	}
	
	@Override
	public List<Coupon> getCompanyCoupons(int companyId) {
		ConnectionMethod<List<Coupon>> method = (Connection conn) -> {
			List<Coupon> coupons = new ArrayList<>();
			try {
				PreparedStatement statement = conn.prepareStatement(GET_COMPANY_COUPONS_QUERY);
				statement.setInt(1, companyId);
				ResultSet result = statement.executeQuery();
				while (result.next()) {
					CategoryDBDAO categoryDBDAO = new CategoryDBDAO(conn);
					Category couponCategory = categoryDBDAO.getCategory(result.getInt("category_id"));
					coupons.add(new Coupon(result.getInt("id"), result.getInt("company_id"), couponCategory,
							result.getString("title"), result.getString("description"), result.getDate("start_date"),
							result.getDate("end_date"), result.getInt("amount"), result.getDouble("price"),
							result.getString("image")));
				}
			} catch (SQLException e) {
				System.out.println("Error - " + e.getMessage());
			}
			return coupons;
		};

		return invokeConnectionMethod(method);
	}
}
