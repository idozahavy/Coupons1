package com.coupons.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.coupons.beans.Category;
import com.coupons.dbdao.CategoryDBDAO;

public class DatabaseManager {

	private static final String URL = "jdbc:mysql://localhost:3306/coupons?"
			+ "createDatabaseIfNotExist=TRUE&useTimezone=TRUE&serverTimezone=UTC";

	private static final String USER = "root";

	private static final String PASSWORD = "12345678";

	public static String getUrl() {
		return URL;
	}

	public static String getUser() {
		return USER;
	}

	public static String getPassword() {
		return PASSWORD;
	}

	public static void CreateTableCompanies() {
		String query = "CREATE TABLE `coupons`.`companies` (\r\n" + "  `id` INT NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `name` VARCHAR(45) NOT NULL,\r\n" + "  `email` VARCHAR(45) NOT NULL,\r\n"
				+ "  `password` VARCHAR(45) NOT NULL,\r\n" + "  PRIMARY KEY (`id`));\r\n" + "";
		executeQuery(query);
	}

	public static void CreateTableCustomers() {
		String query = "CREATE TABLE `coupons`.`customers` (\r\n" + "  `id` INT NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `first_name` VARCHAR(45) NOT NULL,\r\n" + "  `last_name` VARCHAR(45) NOT NULL,\r\n"
				+ "  `email` VARCHAR(45) NOT NULL,\r\n" + "  `password` VARCHAR(45) NOT NULL,\r\n"
				+ "  PRIMARY KEY (`id`));\r\n" + "";
		executeQuery(query);
	}

	public static void CreateTableCategories() {
		String query = "CREATE TABLE `coupons`.`categories` (\r\n" + "  `id` INT NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `name` VARCHAR(45) NOT NULL,\r\n" + "  PRIMARY KEY (`id`));\r\n" + "";
		executeQuery(query);
		CategoryDBDAO categoryDBDAO;
		categoryDBDAO = new CategoryDBDAO();
		for (Category category : Category.values()) {
			categoryDBDAO.addCategory(category);
		}
	}

	public static void CreateTableCoupons() {
		String query = "CREATE TABLE `coupons`.`coupons` (\r\n" + "  `id` INT NOT NULL AUTO_INCREMENT,\r\n"
				+ "  `company_id` INT NOT NULL,\r\n" + "  `category_id` INT NOT NULL,\r\n"
				+ "  `title` VARCHAR(45) NOT NULL,\r\n" + "  `description` VARCHAR(45) NOT NULL,\r\n"
				+ "  `start_date` DATE NULL,\r\n" + "  `end_date` DATE NULL,\r\n" + "  `amount` INT NOT NULL,\r\n"
				+ "  `price` DOUBLE NOT NULL,\r\n" + "  `image` VARCHAR(45) NOT NULL,\r\n" + "  PRIMARY KEY (`id`),\r\n"
				+ "  INDEX `Company_idx` (`company_id` ASC) VISIBLE,\r\n"
				+ "  INDEX `Category_idx` (`category_id` ASC) VISIBLE,\r\n" + "  CONSTRAINT `Company`\r\n"
				+ "    FOREIGN KEY (`company_id`)\r\n" + "    REFERENCES `coupons`.`companies` (`id`)\r\n"
				+ "    ON DELETE NO ACTION\r\n" + "    ON UPDATE NO ACTION,\r\n" + "  CONSTRAINT `Category`\r\n"
				+ "    FOREIGN KEY (`category_id`)\r\n" + "    REFERENCES `coupons`.`categories` (`id`)\r\n"
				+ "    ON DELETE NO ACTION\r\n" + "    ON UPDATE NO ACTION);";
		executeQuery(query);
	}

	public static void CreateTableCustomersVsCoupons() {
		String query = "CREATE TABLE `coupons`.`customers_vs_coupons` (\r\n" + "  `customer_id` INT NOT NULL,\r\n"
				+ "  `coupon_id` INT NOT NULL,\r\n" + "  PRIMARY KEY (`customer_id`, `coupon_id`),\r\n"
				+ "  INDEX `Coupon_idx` (`coupon_id` ASC) VISIBLE,\r\n" + "  CONSTRAINT `Customer`\r\n"
				+ "    FOREIGN KEY (`customer_id`)\r\n" + "    REFERENCES `coupons`.`customers` (`id`)\r\n"
				+ "    ON DELETE NO ACTION\r\n" + "    ON UPDATE NO ACTION,\r\n" + "  CONSTRAINT `Coupon`\r\n"
				+ "    FOREIGN KEY (`coupon_id`)\r\n" + "    REFERENCES `coupons`.`coupons` (`id`)\r\n"
				+ "    ON DELETE NO ACTION\r\n" + "    ON UPDATE NO ACTION);";
		executeQuery(query);
	}

	public static void DropTableCustomersVsCoupons() {
		String query = "DROP TABLE `coupons`.`customers_vs_coupons`";
		executeQuery(query);
	}

	public static void DropTableCoupons() {
		String query = "DROP TABLE `coupons`.`coupons`";
		executeQuery(query);
	}

	public static void DropTableCustomers() {
		String query = "DROP TABLE `coupons`.`customers`";
		executeQuery(query);
	}

	public static void DropTableCategories() {
		String query = "DROP TABLE `coupons`.`categories`";
		executeQuery(query);
	}

	public static void DropTableCompanies() {
		String query = "DROP TABLE `coupons`.`companies`";
		executeQuery(query);
	}

	public static void DropAllTables() {
		DropTableCustomersVsCoupons();
		DropTableCoupons();
		DropTableCompanies();
		DropTableCustomers();
		DropTableCategories();
	}

	public static void CreateAllTables() {
		CreateTableCategories();
		CreateTableCompanies();
		CreateTableCustomers();
		CreateTableCoupons();
		CreateTableCustomersVsCoupons();
	}

	private static void executeQuery(String query) {
		ConnectionPool.instanceInvoke((Connection conn) -> {
			try {
				PreparedStatement statement = conn.prepareStatement(query);
				statement.execute();
			} catch (SQLException e) {
				System.out.println("Error - " + e.getMessage());
			}
			return null;
		});
	}
}
