package com.coupons.tests.facade;

import java.util.List;

import com.coupons.beans.Company;
import com.coupons.beans.Customer;
import com.coupons.exceptions.DataManipulationException;
import com.coupons.exceptions.DetailDuplicationException;
import com.coupons.exceptions.WrongIdException;
import com.coupons.facade.AdminFacade;
import com.coupons.tests.Art;

public class AdminFacadeTest {

	public static void main(String[] args) throws WrongIdException {
		System.out.println();
		System.out.println(Art.stringToArtH1("AdminFacade Test"));

		System.out.println();
		AdminFacade facade = new AdminFacade();

		System.out.println();
		System.out.println(Art.padTo80Stars(" Login (Fail) "));
		System.out.println(facade.login("no", "yes"));

		System.out.println();
		System.out.println(Art.padTo80Stars(" Login (Success) "));
		System.out.println(facade.login("admin@admin.com", "admin"));

		System.out.println();
		System.out.println(Art.padTo80Stars(" Get All Companies "));
		System.out.println(facade.getAllCompanies());

		System.out.println();
		System.out.println(Art.padTo80Stars(" Get All Customers "));
		System.out.println(facade.getAllCustomers());

		System.out.println();
		System.out.println(Art.padTo80Stars(" Add Company "));
		System.out.print("Before - ");
		System.out.println(facade.getAllCompanies());
		try {
			facade.addCompany(new Company("adminCompany", "bjdfjk", "hashcodedpass"));
		} catch (DetailDuplicationException e) {
			e.printStackTrace();
		}
		System.out.print(" After - ");
		List<Company> allCompanies = facade.getAllCompanies();
		System.out.println(allCompanies);
		Company tempCompany = allCompanies.get(allCompanies.size() - 1);

		System.out.println();
		System.out.println(Art.padTo80Stars(" Add Company (Fail - exists) "));
		System.out.print("Before - ");
		System.out.println(facade.getAllCompanies());
		try {
			facade.addCompany(new Company("adminCompany", "bjdfjk", "hashcodedpass"));
		} catch (DetailDuplicationException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.print(" After - ");
		System.out.println(facade.getAllCompanies());

		System.out.println();
		System.out.println(Art.padTo80Stars(" Get One Company "));
		System.out.println(facade.getOneCompany(tempCompany.getId()));

		System.out.println();
		System.out.println(Art.padTo80Stars(" Update Company (email) "));
		System.out.print("Before - ");
		System.out.println(facade.getOneCompany(tempCompany.getId()));
		tempCompany.setEmail("company_new2_Email");
		try {
			facade.updateCompany(tempCompany);
		} catch (DataManipulationException e) {
			e.printStackTrace();
		}
		System.out.print(" After - ");
		System.out.println(facade.getOneCompany(tempCompany.getId()));

		System.out.println();
		System.out.println(Art.padTo80Stars(" Update Company (email, name) (Fail - cannot change name) "));
		System.out.print("Before - ");
		System.out.println(facade.getOneCompany(tempCompany.getId()));
		tempCompany.setEmail("sabba.com_Email");
		tempCompany.setName("sucks to be me");
		try {
			facade.updateCompany(tempCompany);
		} catch (DataManipulationException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.print(" After - ");
		System.out.println(facade.getOneCompany(tempCompany.getId()));

		System.out.println();
		System.out.println(Art.padTo80Stars(" Delete Company (Fail - id do not exist "));
		System.out.print("Before - ");
		System.out.println(facade.getAllCompanies());
		try {
			facade.deleteCompany(-1);
		} catch (WrongIdException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.print(" After - ");
		System.out.println(facade.getAllCompanies());
		
		System.out.println();
		System.out.println(Art.padTo80Stars(" Delete Company "));
		System.out.print("Before - ");
		System.out.println(facade.getAllCompanies());
		try {
			facade.deleteCompany(tempCompany.getId());
		} catch (WrongIdException e) {
			e.printStackTrace();
		}
		System.out.print(" After - ");
		System.out.println(facade.getAllCompanies());

		System.out.println();
		System.out.println(Art.padTo80Stars(" Add Customer "));
		System.out.print("Before - ");
		System.out.println(facade.getAllCustomers());
		try {
			facade.addCustomer(new Customer("firtstff", "mooooo", "shishkabab@fff.dd", "irdontrcare"));
		} catch (DetailDuplicationException e) {
			e.printStackTrace();
		}
		System.out.print(" After - ");
		List<Customer> allCustomers = facade.getAllCustomers();
		System.out.println(allCustomers);
		Customer tempCustomer = allCustomers.get(allCustomers.size() - 1);

		System.out.println();
		System.out.println(Art.padTo80Stars(" Add Customer (Fail - existing email) "));
		System.out.print("Before - ");
		System.out.println(facade.getAllCustomers());
		try {
			facade.addCustomer(new Customer("firtstff", "mooooo", "shishkabab@fff.dd", "irdontrcare"));
		} catch (DetailDuplicationException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.print(" After - ");
		System.out.println(facade.getAllCustomers());

		System.out.println();
		System.out.println(Art.padTo80Stars(" Get One Customer "));
		System.out.println(facade.getOneCustomer(tempCustomer.getId()));

		System.out.println();
		System.out.println(Art.padTo80Stars(" Update Customer (email, firstname) "));
		System.out.print("Before - ");
		System.out.println(facade.getOneCustomer(tempCustomer.getId()));
		tempCustomer.setEmail("company_new2_Email");
		tempCustomer.setFirstName("cust1name504");
		try {
			facade.updateCustomer(tempCustomer);
		} catch (WrongIdException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		} catch (DataManipulationException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.print(" After - ");
		System.out.println(facade.getOneCustomer(tempCustomer.getId()));

		System.out.println();
		System.out.println(Art.padTo80Stars(" Delete Customer (Fail , no such id) "));
		System.out.print("Before - ");
		System.out.println(facade.getAllCustomers());
		try {
			facade.deleteCustomer(-1);
		} catch (WrongIdException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.print(" After - ");
		System.out.println(facade.getAllCustomers());
		
		System.out.println();
		System.out.println(Art.padTo80Stars(" Delete Customer "));
		System.out.print("Before - ");
		System.out.println(facade.getAllCustomers());
		try {
			facade.deleteCustomer(tempCustomer.getId());
		} catch (WrongIdException e) {
			e.printStackTrace();
		}
		System.out.print(" After - ");
		System.out.println(facade.getAllCustomers());

	}

}
