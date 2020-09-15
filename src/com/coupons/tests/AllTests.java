package com.coupons.tests;

import java.sql.Date;

import com.coupons.beans.*;
import com.coupons.db.ConnectionPool;
import com.coupons.db.DatabaseManager;
import com.coupons.dbdao.*;
import com.coupons.jobs.DatedCouponsJob;

public class AllTests {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		CouponsDBDAO dbdao = new CouponsDBDAO();
		Thread t1 = new Thread(new DatedCouponsJob());
		
		InitializeConnectionPool();
		InitializeDB();
		
		t1.start();
		System.out.println();
		System.out.println(Art.padTo120Stars(" after starting DatedCouponsJob "));
		Table100.print(dbdao.getAllCoupons());
		
		DBDAOTests.main(args);
		FacadeTests.main(args);
		LoginManagerTest.main(args);
		
		t1.stop();
		System.out.println();
		System.out.println(Art.padTo120Stars(" after stopping DatedCouponsJob "));
		Table100.print(dbdao.getAllCoupons());
		try {
			ConnectionPool.getInstance().closeAllConnection();
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
	}

	private static void InitializeConnectionPool() {
		System.out.println("Initializing ConnectionPool...");
		ConnectionPool.getInstance();
		System.out.println();
		System.out.println("ConnectionPool Initialized");
		System.out.println();
	}
	
	private static void InitializeDB() {
		DatabaseManager.DropAllTables();
		DatabaseManager.CreateAllTables();
		
		addAllCompanies();
		addAllCustomers();
		addAllCoupons();
		addAllPurchases();
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

	public static void addAllCoupons() {
		CouponsDBDAO couponsDBDAO = new CouponsDBDAO();
		Coupon coupon1 = new Coupon(1, Category.Electricity, "first coupon", "first", Date.valueOf("2020-08-08"),
				Date.valueOf("2020-12-16"), 50, 10.90, "fruit floopy");
		Coupon coupon2 = new Coupon(1, Category.Computers, "second coupon", "gro", Date.valueOf("2020-09-08"),
				Date.valueOf("2020-12-18"), 40, 50.90, "sigmoid");
		Coupon coupon3 = new Coupon(2, Category.AI, "third coupon", "shrik", Date.valueOf("2020-10-08"),
				Date.valueOf("2020-12-10"), 30, 69.90, "koka");
		Coupon coupon4 = new Coupon(2, Category.Food, "outdated coupon", "out", Date.valueOf("2020-04-08"),
				Date.valueOf("2020-08-11"), 20, 12.50, "no");
		Coupon coupon5 = new Coupon(1, Category.Computers, "noAmountCoupon", "0amount", Date.valueOf("2020-12-03"),
				Date.valueOf("2021-12-11"), 0, 90.90, "agid");
		Coupon coupon6 = new Coupon(2, Category.Computers, "fourth coupon", "desc4", Date.valueOf("2020-11-08"),
				Date.valueOf("2020-12-11"), 20, 49.90, "singaurd");
		couponsDBDAO.addCoupon(coupon1);
		couponsDBDAO.addCoupon(coupon2);
		couponsDBDAO.addCoupon(coupon3);
		couponsDBDAO.addCoupon(coupon4);
		couponsDBDAO.addCoupon(coupon5);
		couponsDBDAO.addCoupon(coupon6);
	}

	private static void addAllCustomers() {
		CustomerDBDAO customerDBDAO = new CustomerDBDAO();
		Customer customer1 = new Customer("person1", "last1", "mail1", "1");
		Customer customer2 = new Customer("person2", "last2", "mail2", "2");
		Customer customer3 = new Customer("person3", "last3", "mail3", "3");
		Customer customer4 = new Customer("person4", "last4", "mail4", "4");
		customerDBDAO.addCustomer(customer1);
		customerDBDAO.addCustomer(customer2);
		customerDBDAO.addCustomer(customer3);
		customerDBDAO.addCustomer(customer4);
	}

}
