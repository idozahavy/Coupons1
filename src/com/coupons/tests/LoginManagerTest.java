package com.coupons.tests;

import com.coupons.exceptions.NotLoggedInExcepetion;
import com.coupons.exceptions.WrongIdException;
import com.coupons.facade.AdminFacade;
import com.coupons.facade.CompanyFacade;
import com.coupons.login.ClientType;
import com.coupons.login.LoginManager;

public class LoginManagerTest {

	public static void main(String[] args) {
		
		System.out.println();
		System.out.println(Art.stringToArtH1("- LoginManager -"));

		System.out.println();
		System.out.println(Art.padTo120Stars(" Admin Login "));
		AdminFacade adminFacade = (AdminFacade)LoginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
		System.out.println(Art.padTo120Stars(" Admin Login - getAllCompanies check "));
		try {
			System.out.println(adminFacade.getAllCompanies());
		} catch (NotLoggedInExcepetion e) {
			e.printStackTrace();
		}
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Admin Login (Fail - wrong credentials "));
		adminFacade = (AdminFacade)LoginManager.login("admin@admin.com", "8452", ClientType.ADMINISTRATOR);
		System.out.println(Art.padTo120Stars(" Admin Login - getAllCompanies check "));
		try {
			System.out.println(adminFacade.getAllCompanies());
		} catch (NotLoggedInExcepetion e) {
			System.out.println("Error - "+ e.getMessage());
		}
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Company Login "));
		CompanyFacade companyFacade = (CompanyFacade)LoginManager.login("myamd@cc.com", "2345", ClientType.COMPANY);
		System.out.println(Art.padTo120Stars(" Company Login - getCompanyDetails check "));
		try {
			System.out.println(companyFacade.getCompanyDetails());
		} catch (WrongIdException | NotLoggedInExcepetion e) {
			e.printStackTrace();
		}
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Company Login (Fail - wrong credentials "));
		companyFacade = (CompanyFacade)LoginManager.login("myamd@cc.com", "1234", ClientType.COMPANY);
		System.out.println(Art.padTo120Stars(" Company Login - getCompanyDetails check "));
		try {
			System.out.println(companyFacade.getCompanyDetails());
		} catch (WrongIdException | NotLoggedInExcepetion e) {
			System.out.println("Error - "+ e.getMessage());
		}
	}

}
