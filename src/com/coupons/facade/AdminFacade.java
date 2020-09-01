package com.coupons.facade;

import java.util.List;

import com.coupons.beans.Company;
import com.coupons.beans.Coupon;
import com.coupons.beans.Customer;

public class AdminFacade extends Facade {

	public boolean login(String email, String password) {
		return email.equals("admin@admin.com") && password.equals("admin");
	}

	public void addCompany(Company company) {
		if (companiesDBDAO.canAddCompany(company.getEmail(), company.getName())) {
			companiesDBDAO.addCompany(company);
		}
	}

	public void updateCompany(Company company) {
		Company dbComapny = companiesDBDAO.getOneCompany(company.getId());
		if (dbComapny == null) {
			return;
		}
		boolean isNameEquals = dbComapny.getName().equals(company.getName());
		boolean isIdEquals = dbComapny.getId() == company.getId();
//		boolean isPasswordEquals = dbComapny.getPassword().equals(company.getPassword()); // makes more sense than id == id
		if (isNameEquals && isIdEquals) {
			companiesDBDAO.updateCompany(company);
		}
	}

	public void deleteCompany(int companyId) {
		Company dbCompany = getOneCompany(companyId);
		if (dbCompany == null) {
			return;
		}
		for (Coupon cp : dbCompany.getCoupons()) {
			couponsDBDAO.deleteCouponPurchases(cp.getId());
			couponsDBDAO.deleteCoupon(cp.getId());
		}
		companiesDBDAO.deleteCompany(companyId);
	}

	public List<Company> getAllCompanies() {
		return companiesDBDAO.getAllCompanies();
	}

	public Company getOneCompany(int companyId) {
		return companiesDBDAO.getOneCompany(companyId);
	}

	public void addCustomer(Customer customer) {
		if (customerDBDAO.canAddCustomer(customer.getEmail())) {
			customerDBDAO.addCustomer(customer);
		}
	}

	public void updateCustomer(Customer customer) {
		Customer dbCustomer = customerDBDAO.getOneCustomer(customer.getId());
		if (dbCustomer == null) {
			return;
		}
		if (dbCustomer.getPassword().equals(customer.getPassword())) {
			customerDBDAO.updateCustomer(customer);
		}
	}

	public void deleteCustomer(int customerId) {
		Customer dbCustomer = customerDBDAO.getOneCustomer(customerId);
		if (dbCustomer == null) {
			return;
		}
		for (Coupon cp : dbCustomer.getCoupons()) {
			couponsDBDAO.deleteCouponPurchase(customerId, cp.getId());
			cp.setAmount(cp.getAmount() + 1);
			couponsDBDAO.updateCoupon(cp);
		}
		customerDBDAO.deleteCustomer(customerId);
	}

	public List<Customer> getAllCustomers() {
		return customerDBDAO.getAllCustomers();
	}

	public Customer getOneCustomer(int customerId) {
		return customerDBDAO.getOneCustomer(customerId);
	}
}
