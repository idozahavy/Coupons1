package com.coupons.tests;

import com.coupons.exceptions.WrongIdException;
import com.coupons.tests.facade.AdminFacadeTest;
import com.coupons.tests.facade.CompanyFacadeTest;

public class FacadeTests {

	public static void main(String[] args) {
		System.out.println();
		System.out.println(Art.stringToArtH1("- Facade Tests -".toUpperCase()));
		System.out.println();
		
		try {
			AdminFacadeTest.main(args);
		} catch (WrongIdException e) {
			e.printStackTrace();
		}
		try {
			CompanyFacadeTest.main(args);
		} catch (WrongIdException e) {
			e.printStackTrace();
		}
	}

}
