package com.coupons.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.coupons.beans.Category;
import com.coupons.dao.CategoryDAO;
import com.coupons.db.ConnectionMethod;
import com.coupons.db.ConnectionPool;

public class CategoryDBDAO implements CategoryDAO {

	private static final String ADD_CATEGORY_QUERY = "INSERT INTO `coupons`.`categories` " + "(`name`) VALUES(?)";

	private static final String GET_CATEGORY_BY_ID_QUERY = "SELECT * FROM `coupons`.`categories` WHERE `id` = ?";

	private static final String GET_CATEGORY_BY_NAME_QUERY = "SELECT * FROM `coupons`.`categories` WHERE `name` = ?";

	private Connection conn;

	public CategoryDBDAO() {
	}

	public CategoryDBDAO(Connection conn) {
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
	public void addCategory(Category category) {
		ConnectionMethod<?> method = (Connection conn) -> {
			try {
				PreparedStatement statement = conn.prepareStatement(ADD_CATEGORY_QUERY);
				statement.setString(1, category.toString());
				statement.execute();
			} catch (SQLException e) {
				System.out.println("Error - " + e.getMessage());
			}
			return null;
		};

		invokeConnectionMethod(method);
	}

	@Override
	public Category getCategory(int categoryId) {
		ConnectionMethod<Category> method = (Connection conn) -> {
			PreparedStatement statement;
			try {
				statement = conn.prepareStatement(GET_CATEGORY_BY_ID_QUERY);
				statement.setInt(1, categoryId);
				ResultSet result = statement.executeQuery();
				if (result.next()) {
					return Category.valueOf(Category.class, result.getString("name"));
				}
			} catch (SQLException e) {
				System.out.println("Error - " + e.getMessage());
			}
			return null;
		};

		return invokeConnectionMethod(method);
	}

	@Override
	public int getCategoryId(Category category) {
		ConnectionMethod<Integer> method = (Connection conn) -> {
			PreparedStatement statement;
			try {
				statement = conn.prepareStatement(GET_CATEGORY_BY_NAME_QUERY);
				statement.setString(1, category.toString());
				ResultSet result = statement.executeQuery();
				if (result.next()) {
					return result.getInt("id");
				}
			} catch (SQLException e) {
				System.out.println("Error - " + e.getMessage());
			}
			return -1;
		};

		return invokeConnectionMethod(method);
	}

}
