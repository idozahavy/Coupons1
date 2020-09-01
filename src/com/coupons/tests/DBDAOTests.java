package com.coupons.tests;

import com.coupons.tests.dbdao.CategoryDBDAOTest;
import com.coupons.tests.dbdao.CompaniesDBDAOTest;
import com.coupons.tests.dbdao.CouponsDBDAOTest;
import com.coupons.tests.dbdao.CustomersDBDAOTest;

public class DBDAOTests {

	public static void main(String[] args) {
		System.out.println();
		System.out.println(Art.stringToArtH1("- DBDAO Tests -".toUpperCase()));
		System.out.println();
		
		CategoryDBDAOTest.main(args);
		CompaniesDBDAOTest.main(args);
		CouponsDBDAOTest.main(args);
		CustomersDBDAOTest.main(args);
	}

}
