package com.coupons.tests.dbdao;

import java.util.List;

import com.coupons.beans.Customer;
import com.coupons.dbdao.CustomerDBDAO;
import com.coupons.tests.Art;

public class CustomersDBDAOTest {

	public static void main(String[] args) {
		System.out.println();
		System.out.println(Art.stringToArtH1("CustomersDBDAO Test"));

		System.out.println();
		CustomerDBDAO dbdao = new CustomerDBDAO();
		System.out.println(dbdao.getAllCustomers());
		
		System.out.println();
		System.out.println(Art.padTo80Stars(" Add Customer "));
		dbdao.addCustomer(new Customer("first", "last", "myEmail@email.com", "passoooo"));
		List<Customer> allCustomers = dbdao.getAllCustomers();
		System.out.println(allCustomers);
		Customer tempCustomer = allCustomers.get(allCustomers.size()-1);
		
		System.out.println();
		System.out.println(Art.padTo80Stars(" Is Customer Exist (True) "));
		System.out.println(dbdao.isCustomerExists("myEmail@email.com", "passoooo"));
		
		System.out.println();
		System.out.println(Art.padTo80Stars(" Is Customer Exist (False) "));
		System.out.println(dbdao.isCustomerExists("myEmail@email.com", "no pass"));
		
		System.out.println();
		System.out.println(Art.padTo80Stars(" Can Add Customer (True) "));
		System.out.println(dbdao.canAddCustomer("new_email@rr.rr"));
		
		System.out.println();
		System.out.println(Art.padTo80Stars(" Can Add Customer (False) "));
		System.out.println(dbdao.canAddCustomer("myEmail@email.com"));
		
		System.out.println();
		System.out.println(Art.padTo80Stars(" Get One Customer "));
		System.out.println(dbdao.getOneCustomer(tempCustomer.getId()));
		
		System.out.println();
		System.out.println(Art.padTo80Stars(" Get Customer By Email Password "));
		System.out.println(dbdao.GetCustomerByEmailPassword(tempCustomer.getEmail(), tempCustomer.getPassword()));
		
		System.out.println();
		System.out.println(Art.padTo80Stars(" Get One Customer (no such id) "));
		System.out.println(dbdao.getOneCustomer(-1));
		
		System.out.println();
		System.out.println(Art.padTo80Stars(" Update Customer (email , lastname) "));
		System.out.print("Before - ");
		System.out.println(dbdao.getOneCustomer(tempCustomer.getId()));
		tempCustomer.setEmail("new_email");
		tempCustomer.setLastName("new_last");
		dbdao.updateCustomer(tempCustomer);
		System.out.print(" After - ");
		System.out.println(dbdao.getOneCustomer(tempCustomer.getId()));
		
		System.out.println();
		System.out.println(Art.padTo80Stars(" Delete Customer "));
		System.out.print("Before - ");
		System.out.println(dbdao.getAllCustomers());
		dbdao.deleteCustomer(tempCustomer.getId());
		System.out.print(" After - ");
		System.out.println(dbdao.getAllCustomers());
	}

}
