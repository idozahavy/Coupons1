package com.coupons;

import com.coupons.db.ConnectionPool;
import com.coupons.dbdao.CouponsDBDAO;
import com.coupons.tests.AllTests;
import com.coupons.timers.DatedCouponsTimer;

@SuppressWarnings("unused")
public class Test {

	public static void main(String[] args) throws InterruptedException {
//		Admin
//		---------------
//		update non existant comapny
//		delete non existant comapny
//		delete company with coupons
//
//		update non existant customer
//		get non existant customer
//		delete customer with coupons
//
//		Company
//		----------------
//		adding an existing title in company
//		update non existant coupon
//		update coupon not in company
//		changing coupon company id
//
//
//		Customer
//		-----------------
//		purchase non existant coupon
//		cant purchase becuase of amount/endDate

		AllTests.main(args);

		CouponsDBDAO dbdao = new CouponsDBDAO();
				
		System.out.println(dbdao.getAllCoupons());
		new Thread(new DatedCouponsTimer()).start();
		Thread.sleep(1000);
		System.out.println(dbdao.getAllCoupons());

//		ConnectionPool.getInstance().closeAllConnection();
	}
}
