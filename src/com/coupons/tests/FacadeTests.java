package com.coupons.tests;

import com.coupons.exceptions.NotLoggedInExcepetion;
import com.coupons.exceptions.WrongIdException;
import com.coupons.tests.facade.AdminFacadeTest;
import com.coupons.tests.facade.CompanyFacadeTest;
import com.coupons.tests.facade.CustomerFacadeTest;

public class FacadeTests {

	public static void main(String[] args) {
		System.out.println();
		System.out.println(Art.stringToArtH1("- Facade Tests -".toUpperCase()));
		System.out.println();

		try {
			AdminFacadeTest.main(args);
			CompanyFacadeTest.main(args);
			CustomerFacadeTest.main(args);
		} catch (WrongIdException | NotLoggedInExcepetion e) {
			e.printStackTrace();
		}
	}

}
