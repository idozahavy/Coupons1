package com.coupons.facade;

import java.util.List;

import com.coupons.beans.Company;
import com.coupons.beans.Coupon;
import com.coupons.beans.Customer;
import com.coupons.exceptions.DataManipulationException;
import com.coupons.exceptions.DetailDuplicationException;
import com.coupons.exceptions.WrongIdException;

public class AdminFacade extends ClientFacade {

	public boolean login(String email, String password) {
		if (email.equals("admin@admin.com") && password.equals("admin")) {
			Initialize();
			return true;
		}
		return false;
	}

	public void addCompany(Company company) throws DetailDuplicationException {
		if (companiesDBDAO.canAddCompany(company.getEmail(), company.getName())) {
			companiesDBDAO.addCompany(company);
		} else {
			throw new DetailDuplicationException("company already exist with email or name");
		}
	}

	public void updateCompany(Company company) throws DataManipulationException {
		Company dbComapny = companiesDBDAO.getOneCompany(company.getId());
		if (dbComapny == null) {
			return;
		}
		boolean isNameEquals = dbComapny.getName().equals(company.getName());
		boolean isIdEquals = dbComapny.getId() == company.getId();
		// boolean isPasswordEquals =
		// dbComapny.getPassword().equals(company.getPassword()); // makes more sense
		// than id == id
		if (isNameEquals && isIdEquals) {
			companiesDBDAO.updateCompany(company);
		} else {
			throw new DataManipulationException("can not change company name");
		}
	}

	public void deleteCompany(int companyId) throws WrongIdException {
		Company dbCompany = getOneCompany(companyId);
		if (dbCompany == null) {
			throw new WrongIdException("company id does not exist");
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

	public Company getOneCompany(int companyId) throws WrongIdException {
		Company company = companiesDBDAO.getOneCompany(companyId);
		if (company != null) {
			return company;
		} else {
			throw new WrongIdException("company id does not exist");
		}
	}

	public void addCustomer(Customer customer) throws DetailDuplicationException {
		if (customerDBDAO.canAddCustomer(customer.getEmail())) {
			customerDBDAO.addCustomer(customer);
		} else {
			throw new DetailDuplicationException("can not add customer with same email as others");
		}
	}

	public void updateCustomer(Customer customer) throws WrongIdException, DataManipulationException {
		Customer dbCustomer = customerDBDAO.getOneCustomer(customer.getId());
		if (dbCustomer == null) {
			throw new WrongIdException("customer id does not exist");
		}
		if (dbCustomer.getId() == customer.getId()) {
			customerDBDAO.updateCustomer(customer);
		} else {
			// wont get here anyway
			throw new DataManipulationException("can not change customer id");
		}

	}

	public void deleteCustomer(int customerId) throws WrongIdException {
		Customer dbCustomer = customerDBDAO.getOneCustomer(customerId);
		if (dbCustomer == null) {
			throw new WrongIdException("customer id does not exist");
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

	public Customer getOneCustomer(int customerId) throws WrongIdException {
		Customer customer = customerDBDAO.getOneCustomer(customerId);
		if (customer != null) {
			return customer;
		} else {
			throw new WrongIdException("customer id does not exist");
		}
	}
}
