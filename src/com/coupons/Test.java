package com.coupons;

import java.sql.Date;
import java.util.List;

import com.coupons.db.ConnectionPool;
import com.coupons.db.DatabaseManager;
import com.coupons.facade.AdminFacade;
import com.coupons.tests.AllTests;

@SuppressWarnings("unused")
public class Test {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Initializing ConnectionPool...");
		ConnectionPool.getInstance();
		System.out.println();
		System.out.println("ConnectionPool Initialized");
		System.out.println();
		

		DatabaseManager.DropAllTables();
		DatabaseManager.CreateAllTables();

		AllTests.main(args);
		
		ConnectionPool.getInstance().closeAllConnection();
	}
}
