package com.coupons.tests.facade;

import java.util.List;

import com.coupons.beans.Company;
import com.coupons.beans.Customer;
import com.coupons.clients.facade.AdminFacade;
import com.coupons.dbdao.CustomerDBDAO;
import com.coupons.tests.Art;

public class AdminFacadeTest {

	public static void main(String[] args) {
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
		facade.addCompany(new Company("adminCompany", "bjdfjk", "hashcodedpass"));
		System.out.print(" After - ");
		List<Company> allCompanies = facade.getAllCompanies();
		System.out.println(allCompanies);
		Company tempCompany = allCompanies.get(allCompanies.size() - 1);

		System.out.println();
		System.out.println(Art.padTo80Stars(" Get One Company "));
		System.out.println(facade.getOneCompany(tempCompany.getId()));

		System.out.println();
		System.out.println(Art.padTo80Stars(" Update Company (email) "));
		System.out.print("Before - ");
		System.out.println(facade.getOneCompany(tempCompany.getId()));
		tempCompany.setEmail("company_new2_Email");
		facade.updateCompany(tempCompany);
		System.out.print(" After - ");
		System.out.println(facade.getOneCompany(tempCompany.getId()));
		
		System.out.println();
		System.out.println(Art.padTo80Stars(" Update Company (email, name) (Fail) "));
		System.out.print("Before - ");
		System.out.println(facade.getOneCompany(tempCompany.getId()));
		tempCompany.setEmail("sabba.com_Email");
		tempCompany.setName("sucks to be me");
		facade.updateCompany(tempCompany);
		System.out.print(" After - ");
		System.out.println(facade.getOneCompany(tempCompany.getId()));

		System.out.println();
		System.out.println(Art.padTo80Stars(" Delete Company "));
		System.out.print("Before - ");
		System.out.println(facade.getAllCompanies());
		facade.deleteCompany(tempCompany.getId());
		System.out.print(" After - ");
		System.out.println(facade.getAllCompanies());
		
		
	
		
		System.out.println();
		System.out.println(Art.padTo80Stars(" Add Customer "));
		System.out.print("Before - ");
		System.out.println(facade.getAllCustomers());
		facade.addCustomer(new Customer("firtstff", "mooooo", "shishkabab@fff.dd", "irdontrcare"));
		System.out.print(" After - ");
		List<Customer> allCustomers = facade.getAllCustomers();
		System.out.println(allCustomers);
		Customer tempCustomer = allCustomers.get(allCustomers.size() - 1);
		
		System.out.println();
		System.out.println(Art.padTo80Stars(" Add Customer (Fail - existing email) "));
		System.out.print("Before - ");
		System.out.println(facade.getAllCustomers());
		facade.addCustomer(new Customer("firtstff", "mooooo", "shishkabab@fff.dd", "irdontrcare"));
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
		facade.updateCustomer(tempCustomer);
		System.out.print(" After - ");
		System.out.println(facade.getOneCustomer(tempCustomer.getId()));

		System.out.println();
		System.out.println(Art.padTo80Stars(" Delete Customer "));
		System.out.print("Before - ");
		System.out.println(facade.getAllCustomers());
		facade.deleteCustomer(tempCustomer.getId());
		System.out.print(" After - ");
		System.out.println(facade.getAllCustomers());
		
		
	}

}
