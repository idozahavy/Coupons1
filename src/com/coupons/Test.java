package com.coupons;

import com.coupons.db.ConnectionPool;
import com.coupons.dbdao.CouponsDBDAO;
import com.coupons.jobs.DatedCouponsJob;
import com.coupons.tests.AllTests;
import com.coupons.tests.Table100;

@SuppressWarnings("unused")
public class Test {

	public static void main(String[] args) throws InterruptedException {
		
		// Login manager 
		// Customer login
		// All logins
		
//		// Disables coupons attribute from showing in tables
//		Table100.noShowFields.add("coupons");
		
		AllTests.main(args);
	}
}
