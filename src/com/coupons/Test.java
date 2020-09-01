package com.coupons;

import java.sql.Date;
import java.util.List;

import com.coupons.beans.*;
import com.coupons.db.*;
import com.coupons.dbdao.*;
import com.coupons.facade.AdminFacade;
import com.coupons.login.*;
import com.coupons.tests.AllTests;

public class Test {

	public static void main(String[] args) throws InterruptedException {

		DatabaseManager.DropAllTables();
		DatabaseManager.CreateAllTables();

		addAllCompanies();
		addAllCustomers();
		addAllCoupons();
		addAllPurchases();

		printAllCustomers();
		printAllCoupons();
		printAllCompanies();

//		AllTests.main(args);
		
		AdminFacade facade = (AdminFacade) LoginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
		
		System.out.println(facade.getAllCompanies());
		
		ConnectionPool.getInstance().closeAllConnection();
	}

	public static void addAllPurchases() {
		CouponsDBDAO couponsDBDAO = new CouponsDBDAO();
		Coupon cp1 = couponsDBDAO.getOneCoupon(1);
		Coupon cp2 = couponsDBDAO.getOneCoupon(2);
		Coupon cp3 = couponsDBDAO.getOneCoupon(3);
		Coupon cp4 = couponsDBDAO.getOneCoupon(4);
		
		couponsDBDAO.addCouponPurchase(1, 1);
		cp1.setAmount(cp1.getAmount()-1);		
		couponsDBDAO.addCouponPurchase(1, 2);
		cp2.setAmount(cp2.getAmount()-1);
		couponsDBDAO.addCouponPurchase(1, 3);
		cp3.setAmount(cp3.getAmount()-1);
		couponsDBDAO.addCouponPurchase(2, 3);
		cp3.setAmount(cp3.getAmount()-1);
		couponsDBDAO.addCouponPurchase(2, 1);
		cp1.setAmount(cp1.getAmount()-1);
		couponsDBDAO.addCouponPurchase(3, 2);
		cp2.setAmount(cp2.getAmount()-1);
		couponsDBDAO.addCouponPurchase(4, 4);
		cp4.setAmount(cp4.getAmount()-1);
		
		couponsDBDAO.updateCoupon(cp1);
		couponsDBDAO.updateCoupon(cp2);
		couponsDBDAO.updateCoupon(cp3);
		couponsDBDAO.updateCoupon(cp4);
	}

	public static void addAllCompanies() {
		CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
		Company ibm = new Company("ibm", "mymail@cc.com", "1234");
		Company amd = new Company("amd", "myamd@cc.com", "2345");
		companiesDBDAO.addCompany(ibm);
		companiesDBDAO.addCompany(amd);
	}

	public static void printAllCompanies() {
		CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
		List<Company> companies = companiesDBDAO.getAllCompanies();
		System.out.println();
		System.out.println("All Companies:");
		for (Company company : companies) {
			System.out.println(company);
		}
	}

	public static void addAllCoupons() {
		CouponsDBDAO couponsDBDAO = new CouponsDBDAO();
		Coupon coupon1 = new Coupon(0, 1, Category.Electricity, "first coupon", "first", Date.valueOf("2020-08-08"),
				Date.valueOf("2020-12-16"), 50, 10.90, "blop");
		Coupon coupon2 = new Coupon(0, 1, Category.Computers, "second coupon", "gro", Date.valueOf("2020-09-08"),
				Date.valueOf("2020-12-18"), 40, 50.90, "blop");
		Coupon coupon3 = new Coupon(0, 2, Category.AI, "third coupon", "shrik", Date.valueOf("2020-10-08"),
				Date.valueOf("2020-12-10"), 30, 69.90, "blop");
		Coupon coupon4 = new Coupon(0, 2, Category.Computers, "fourth coupon", "desc4", Date.valueOf("2020-11-08"),
				Date.valueOf("2020-12-11"), 20, 49.90, "blop");
		couponsDBDAO.addCoupon(coupon1);
		couponsDBDAO.addCoupon(coupon2);
		couponsDBDAO.addCoupon(coupon3);
		couponsDBDAO.addCoupon(coupon4);
	}

	public static void printAllCoupons() {
		CouponsDBDAO couponsDBDAO = new CouponsDBDAO();
		List<Coupon> coupons = couponsDBDAO.getAllCoupons();
		System.out.println();
		System.out.println("All Coupons:");
		for (Coupon coupon : coupons) {
			System.out.println(coupon);
		}
	}

	private static void addAllCustomers() {
		CustomerDBDAO customerDBDAO = new CustomerDBDAO();
		Customer customer1 = new Customer(0, "person1", "last1", "mail1", "1", null);
		Customer customer2 = new Customer(0, "person2", "last2", "mail2", "2", null);
		Customer customer3 = new Customer(0, "person3", "last3", "mail3", "3", null);
		Customer customer4 = new Customer(0, "person4", "last4", "mail4", "4", null);
		customerDBDAO.addCustomer(customer1);
		customerDBDAO.addCustomer(customer2);
		customerDBDAO.addCustomer(customer3);
		customerDBDAO.addCustomer(customer4);
	}

	private static void printAllCustomers() {
		CustomerDBDAO customerDBDAO = new CustomerDBDAO();
		List<Customer> customers = customerDBDAO.getAllCustomers();
		System.out.println();
		System.out.println("All Customers:");
		for (Customer customer : customers) {
			System.out.println(customer);
		}
	}

}
