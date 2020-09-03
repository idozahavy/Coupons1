package com.coupons;

import com.coupons.db.ConnectionPool;
import com.coupons.tests.AllTests;

public class Test {

	public static void main(String[] args) throws InterruptedException {
		AllTests.main(args);
		
		ConnectionPool.getInstance().closeAllConnection();
	}
}
