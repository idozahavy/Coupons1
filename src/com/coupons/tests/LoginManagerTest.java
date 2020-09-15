package com.coupons.tests;

import com.coupons.exceptions.NotLoggedInExcepetion;
import com.coupons.exceptions.WrongIdException;
import com.coupons.facade.AdminFacade;
import com.coupons.facade.CompanyFacade;
import com.coupons.facade.CustomerFacade;
import com.coupons.login.ClientType;
import com.coupons.login.LoginManager;

public class LoginManagerTest {

	public static void main(String[] args) {

		System.out.println();
		System.out.println(Art.stringToArtH1("- LoginManager -"));

		System.out.println();
		System.out.println(Art.padTo120Stars(" Admin Login "));
		AdminFacade adminFacade = (AdminFacade) LoginManager.login("admin@admin.com", "admin",
				ClientType.ADMINISTRATOR);
		System.out.println(Art.padTo120Stars(" Admin Login - getAllCompanies check "));
		try {
			System.out.println(adminFacade.getAllCompanies());
		} catch (NotLoggedInExcepetion e) {
			System.err.println(e.getMessage());
		}

		System.out.println();
		System.out.println(Art.padTo120Stars(" Admin Login (Fail - wrong credentials) "));
		adminFacade = (AdminFacade) LoginManager.login("admin@admin.com", "8452", ClientType.ADMINISTRATOR);
		System.out.println("Admin Facade = " + adminFacade);

		System.out.println();
		System.out.println(Art.padTo120Stars(" Company Login "));
		CompanyFacade companyFacade = (CompanyFacade) LoginManager.login("myamd@cc.com", "2345", ClientType.COMPANY);
		System.out.println(Art.padTo120Stars(" Company Login - getCompanyDetails check "));
		try {
			System.out.println(companyFacade.getCompanyDetails());
		} catch (WrongIdException | NotLoggedInExcepetion e) {
			System.err.println(e.getMessage());
		}

		System.out.println();
		System.out.println(Art.padTo120Stars(" Company Login (Fail - wrong credentials) "));
		companyFacade = (CompanyFacade) LoginManager.login("myamd@cc.com", "1234", ClientType.COMPANY);
		System.out.println("Company Facade = " + companyFacade);
		
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Customer Login "));
		CustomerFacade customerFacade = (CustomerFacade) LoginManager.login("mail1", "1", ClientType.CUSTOMER);
		System.out.println(Art.padTo120Stars(" Customer Login - getCustomerDetails check "));
		try {
			System.out.println(customerFacade.getCustomerDetails());
		} catch (WrongIdException | NotLoggedInExcepetion e) {
			System.err.println(e.getMessage());
		}

		System.out.println();
		System.out.println(Art.padTo120Stars(" Customer Login (Fail - wrong credentials) "));
		customerFacade = (CustomerFacade) LoginManager.login("mail1", "22", ClientType.CUSTOMER);
		System.out.println("Customer Facade = " + customerFacade);
	}

}
