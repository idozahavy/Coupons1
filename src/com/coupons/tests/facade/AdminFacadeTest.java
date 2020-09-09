package com.coupons.tests.facade;

import java.util.List;

import com.coupons.beans.Company;
import com.coupons.beans.Customer;
import com.coupons.exceptions.DataManipulationException;
import com.coupons.exceptions.DetailDuplicationException;
import com.coupons.exceptions.NotLoggedInExcepetion;
import com.coupons.exceptions.WrongIdException;
import com.coupons.facade.AdminFacade;
import com.coupons.tests.Art;
import com.coupons.tests.Table100;

public class AdminFacadeTest {

	public static void main(String[] args) throws WrongIdException, NotLoggedInExcepetion {
		System.out.println();
		System.out.println(Art.stringToArtH1("AdminFacade Test"));

		System.out.println();
		AdminFacade facade = new AdminFacade();

		System.out.println();
		System.out.println(Art.padTo120Stars(" Login (Fail - wrong credentials) "));
		System.out.println(facade.login("no", "yes"));

		System.out.println();
		System.out.println(Art.padTo120Stars(" Login (Success) "));
		System.out.println(facade.login("admin@admin.com", "admin"));

		System.out.println();
		System.out.println(Art.padTo120Stars(" Get All Companies "));
		Table100.print(facade.getAllCompanies());

		System.out.println(Art.padTo120Stars(" Get All Customers "));
		Table100.print(facade.getAllCustomers());

		System.out.println(Art.padTo120Stars(" Add Company "));
		try {
			facade.addCompany(new Company("adminCompany", "bjdfjk", "hashcodedpass"));
		} catch (DetailDuplicationException e) {
			System.err.println(e.getMessage());
		}
		System.out.println(" After - ");
		List<Company> allCompanies = facade.getAllCompanies();
		Table100.print(allCompanies);
		Company tempCompany = allCompanies.get(allCompanies.size() - 1);

		System.out.println(Art.padTo120Stars(" Add Company (Fail - company name exists) "));
		try {
			facade.addCompany(new Company("adminCompany", "bjdfjk", "hashcodedpass"));
		} catch (DetailDuplicationException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.println(" After - ");
		Table100.print(facade.getAllCompanies());

		System.out.println(Art.padTo120Stars(" Get One Company "));
		Table100.print(facade.getOneCompany(tempCompany.getId()));

		System.out.println(Art.padTo120Stars(" Update Company [email] "));
		tempCompany.setEmail("company_new2_Email");
		try {
			facade.updateCompany(tempCompany);
		} catch (DataManipulationException e) {
			System.err.println(e.getMessage());
		}
		System.out.println(" After - ");
		Table100.print(facade.getOneCompany(tempCompany.getId()));

		System.out.println(Art.padTo120Stars(" Update Company [email, name] (Fail - cannot change name) "));
		tempCompany.setEmail("sabba.com_Email");
		tempCompany.setName("sucks to be me");
		try {
			facade.updateCompany(tempCompany);
		} catch (DataManipulationException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.println(" After - ");
		Table100.print(facade.getOneCompany(tempCompany.getId()));

		System.out.println(Art.padTo120Stars(" Update Company [email, pass] (Fail - company does not exist) "));
		int tempCouponId = tempCompany.getId();
		tempCompany.setId(0);
		tempCompany.setEmail("sabba.com_Email");
		tempCompany.setPassword("sucks to be me");
		try {
			facade.updateCompany(tempCompany);
		} catch (DataManipulationException | NotLoggedInExcepetion | WrongIdException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.println(" After - ");
		Table100.print(facade.getAllCompanies());
		tempCompany.setId(tempCouponId);

		System.out.println(Art.padTo120Stars(" Delete Company (Fail - company does not exist) "));
		try {
			facade.deleteCompany(-1);
		} catch (WrongIdException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.println(" After - ");
		Table100.print(facade.getAllCompanies());

		System.out.println(Art.padTo120Stars(" Delete Company "));
		try {
			facade.deleteCompany(tempCompany.getId());
		} catch (WrongIdException e) {
			System.err.println(e.getMessage());
		}
		System.out.println(" After - ");
		Table100.print(facade.getAllCompanies());

		System.out.println(Art.padTo120Stars(" Add Customer "));
		System.out.println("Before - ");
		Table100.print(facade.getAllCustomers());
		try {
			facade.addCustomer(new Customer("firtstff", "mooooo", "shishkabab@fff.dd", "irdontrcare"));
		} catch (DetailDuplicationException e) {
			System.err.println(e.getMessage());
		}
		System.out.println(" After - ");
		List<Customer> allCustomers = facade.getAllCustomers();
		Table100.print(allCustomers);
		Customer tempCustomer = allCustomers.get(allCustomers.size() - 1);

		System.out.println(Art.padTo120Stars(" Add Customer (Fail - existing email) "));
		try {
			facade.addCustomer(new Customer("firtstff", "mooooo", "shishkabab@fff.dd", "irdontrcare"));
		} catch (DetailDuplicationException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.println(" After - ");
		Table100.print(facade.getAllCustomers());

		System.out.println(Art.padTo120Stars(" Get One Customer "));
		Table100.print(facade.getOneCustomer(tempCustomer.getId()));

		System.out.println(Art.padTo120Stars(" Update Customer [email, firstname] "));
		System.out.println("Before - ");
		Table100.print(facade.getOneCustomer(tempCustomer.getId()));
		tempCustomer.setEmail("company_new2_Email");
		tempCustomer.setFirstName("cust1name504");
		try {
			facade.updateCustomer(tempCustomer);
		} catch (WrongIdException | DataManipulationException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.println(" After - ");
		Table100.print(facade.getOneCustomer(tempCustomer.getId()));

		System.out.println(Art.padTo120Stars(" Delete Customer (Fail , no such id) "));
		try {
			facade.deleteCustomer(-1);
		} catch (WrongIdException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.println(" After - ");
		Table100.print(facade.getAllCustomers());

		System.out.println(Art.padTo120Stars(" Delete Customer "));
		try {
			facade.deleteCustomer(tempCustomer.getId());
		} catch (WrongIdException e) {
			System.err.println(e.getMessage());
		}
		System.out.println(" After - ");
		Table100.print(facade.getAllCustomers());

	}

}
