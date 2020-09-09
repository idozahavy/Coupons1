package com.coupons.tests.dbdao;

import java.util.List;

import com.coupons.beans.Customer;
import com.coupons.dbdao.CustomerDBDAO;
import com.coupons.tests.Art;
import com.coupons.tests.Table100;

public class CustomersDBDAOTest {

	public static void main(String[] args) {
		System.out.println();
		System.out.println(Art.stringToArtH1("CustomersDBDAO Test"));

		System.out.println();
		System.out.println(Art.padTo120Stars(" Get All Customers "));
		CustomerDBDAO dbdao = new CustomerDBDAO();
		Table100.print(dbdao.getAllCustomers());

		System.out.println(Art.padTo120Stars(" Add Customer "));
		dbdao.addCustomer(new Customer("first", "last", "myEmail@email.com", "passoooo"));
		List<Customer> allCustomers = dbdao.getAllCustomers();
		Table100.print(allCustomers);
		Customer tempCustomer = allCustomers.get(allCustomers.size() - 1);

		System.out.println(Art.padTo120Stars(" Is Customer Exist (True) "));
		System.out.println(dbdao.isCustomerExists("myEmail@email.com", "passoooo"));

		System.out.println();
		System.out.println(Art.padTo120Stars(" Is Customer Exist (False - wrong password) "));
		System.out.println(dbdao.isCustomerExists("myEmail@email.com", "no pass"));

		System.out.println();
		System.out.println(Art.padTo120Stars(" Can Add Customer (True) "));
		System.out.println(dbdao.canAddCustomer("new_email@rr.rr"));

		System.out.println();
		System.out.println(Art.padTo120Stars(" Can Add Customer (False - existing email) "));
		System.out.println(dbdao.canAddCustomer("myEmail@email.com"));

		System.out.println(Art.padTo120Stars(" Get One Customer "));
		Table100.print(dbdao.getOneCustomer(tempCustomer.getId()));

		System.out.println(Art.padTo120Stars(" Get Customer By Email Password "));
		Table100.print(dbdao.GetCustomerByEmailPassword(tempCustomer.getEmail(), tempCustomer.getPassword()));

		System.out.println(Art.padTo120Stars(" Get One Customer (no such id) "));
		Table100.print(dbdao.getOneCustomer(-1));

		System.out.println(Art.padTo120Stars(" Update Customer (email , lastname) "));
		System.out.println("Before - ");
		Table100.print(dbdao.getOneCustomer(tempCustomer.getId()));
		tempCustomer.setEmail("new_email");
		tempCustomer.setLastName("new_last");
		dbdao.updateCustomer(tempCustomer);
		System.out.println(" After - ");
		Table100.print(dbdao.getOneCustomer(tempCustomer.getId()));

		System.out.println(Art.padTo120Stars(" Delete Customer "));
		System.out.println("Before - ");
		Table100.print(dbdao.getAllCustomers());
		dbdao.deleteCustomer(tempCustomer.getId());
		System.out.println(" After - ");
		Table100.print(dbdao.getAllCustomers());
	}

}
