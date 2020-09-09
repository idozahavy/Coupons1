package com.coupons;

import com.coupons.db.ConnectionPool;
import com.coupons.dbdao.CouponsDBDAO;
import com.coupons.tests.AllTests;
import com.coupons.tests.Table100;
import com.coupons.timers.DatedCouponsTimer;

@SuppressWarnings("unused")
public class Test {

	public static void main(String[] args) throws InterruptedException {
//		Admin
//		---------------
//		delete company with coupons
//
//		update non existant customer
//		get non existant customer
//		delete customer with coupons
//
//		Company
//		----------------
//		---tables---
//		adding an existing title in company
//		update non existant coupon
//		update coupon not in company
//		changing coupon company id

		
//		// Disables coupons attribute from showing in tables
//		Table100.noShowFields.add("coupons");
		
		
		AllTests.main(args);

		CouponsDBDAO dbdao = new CouponsDBDAO();
				
		System.out.println(dbdao.getAllCoupons());
		new Thread(new DatedCouponsTimer()).start();
		Thread.sleep(1000);
		System.out.println(dbdao.getAllCoupons());

//		ConnectionPool.getInstance().closeAllConnection();
	}
}
