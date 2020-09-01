package com.coupons.dao;

import java.util.List;

import com.coupons.beans.Customer;

public interface CustomersDAO {

	boolean isCustomerExists(String email, String password);
	
	boolean canAddCustomer(String email);

	void addCustomer(Customer customer);

	void updateCustomer(Customer customer);

	void deleteCustomer(int customerId);

	List<Customer> getAllCustomers();

	Customer getOneCustomer(int customerId);

	Customer GetCustomerByEmailPassword(String email, String password);
}
