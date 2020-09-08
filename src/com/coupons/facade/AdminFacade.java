package com.coupons.facade;

import java.util.List;

import com.coupons.beans.Company;
import com.coupons.beans.Coupon;
import com.coupons.beans.Customer;
import com.coupons.exceptions.DataManipulationException;
import com.coupons.exceptions.DetailDuplicationException;
import com.coupons.exceptions.NotLoggedInExcepetion;
import com.coupons.exceptions.WrongIdException;

public class AdminFacade extends ClientFacade {

	public boolean login(String email, String password) {
		if (email.equals("admin@admin.com") && password.equals("admin")) {
			Initialize();
			return true;
		}
		Deinitialize();
		return false;
	}

	public void addCompany(Company company) throws DetailDuplicationException, NotLoggedInExcepetion {
		if (couponsDAO == null) {
			throw new NotLoggedInExcepetion("you did not logged in properly - check your credentials");
		}
		if (companiesDAO.canAddCompany(company.getEmail(), company.getName())) {
			companiesDAO.addCompany(company);
		} else {
			throw new DetailDuplicationException("company already exist with email or name");
		}
	}

	public void updateCompany(Company company) throws DataManipulationException, NotLoggedInExcepetion, WrongIdException {
		if (couponsDAO == null) {
			throw new NotLoggedInExcepetion("you did not logged in properly - check your credentials");
		}
		Company dbComapny = companiesDAO.getOneCompany(company.getId());
		if (dbComapny == null) {
			throw new WrongIdException("company id does not exist");
		}
		boolean isNameEquals = dbComapny.getName().equals(company.getName());
		boolean isIdEquals = dbComapny.getId() == company.getId();
		// boolean isPasswordEquals =
		// dbComapny.getPassword().equals(company.getPassword()); // makes more sense
		// than id == id
		if (isNameEquals && isIdEquals) {
			companiesDAO.updateCompany(company);
		} else {
			throw new DataManipulationException("can not change company name");
		}
	}

	public void deleteCompany(int companyId) throws WrongIdException, NotLoggedInExcepetion {
		if (couponsDAO == null) {
			throw new NotLoggedInExcepetion("you did not logged in properly - check your credentials");
		}
		Company dbCompany = getOneCompany(companyId);
		if (dbCompany == null) {
			throw new WrongIdException("company id does not exist");
		}
		for (Coupon cp : dbCompany.getCoupons()) {
			couponsDAO.deleteCouponPurchases(cp.getId());
			couponsDAO.deleteCoupon(cp.getId());
		}
		companiesDAO.deleteCompany(companyId);
	}

	public List<Company> getAllCompanies() throws NotLoggedInExcepetion {
		if (couponsDAO == null) {
			throw new NotLoggedInExcepetion("you did not logged in properly - check your credentials");
		}
		return companiesDAO.getAllCompanies();
	}

	public Company getOneCompany(int companyId) throws WrongIdException, NotLoggedInExcepetion {
		if (couponsDAO == null) {
			throw new NotLoggedInExcepetion("you did not logged in properly - check your credentials");
		}
		Company company = companiesDAO.getOneCompany(companyId);
		if (company != null) {
			return company;
		} else {
			throw new WrongIdException("company id does not exist");
		}
	}

	public void addCustomer(Customer customer) throws DetailDuplicationException, NotLoggedInExcepetion {
		if (couponsDAO == null) {
			throw new NotLoggedInExcepetion("you did not logged in properly - check your credentials");
		}
		if (customerDAO.canAddCustomer(customer.getEmail())) {
			customerDAO.addCustomer(customer);
		} else {
			throw new DetailDuplicationException("can not add customer with same email as others");
		}
	}

	public void updateCustomer(Customer customer) throws WrongIdException, DataManipulationException, NotLoggedInExcepetion {
		if (couponsDAO == null) {
			throw new NotLoggedInExcepetion("you did not logged in properly - check your credentials");
		}
		Customer dbCustomer = customerDAO.getOneCustomer(customer.getId());
		if (dbCustomer == null) {
			throw new WrongIdException("customer id does not exist");
		}
		if (dbCustomer.getId() == customer.getId()) {
			customerDAO.updateCustomer(customer);
		} else {
			// wont get here anyway
			throw new DataManipulationException("can not change customer id");
		}

	}

	public void deleteCustomer(int customerId) throws WrongIdException, NotLoggedInExcepetion {
		if (couponsDAO == null) {
			throw new NotLoggedInExcepetion("you did not logged in properly - check your credentials");
		}
		Customer dbCustomer = customerDAO.getOneCustomer(customerId);
		if (dbCustomer == null) {
			throw new WrongIdException("customer id does not exist");
		}
		for (Coupon cp : dbCustomer.getCoupons()) {
			couponsDAO.deleteCouponPurchase(customerId, cp.getId());
			cp.setAmount(cp.getAmount() + 1);
			couponsDAO.updateCoupon(cp);
		}
		customerDAO.deleteCustomer(customerId);
	}

	public List<Customer> getAllCustomers() throws NotLoggedInExcepetion {
		if (couponsDAO == null) {
			throw new NotLoggedInExcepetion("you did not logged in properly - check your credentials");
		}
		return customerDAO.getAllCustomers();
	}

	public Customer getOneCustomer(int customerId) throws WrongIdException, NotLoggedInExcepetion {
		if (couponsDAO == null) {
			throw new NotLoggedInExcepetion("you did not logged in properly - check your credentials");
		}
		Customer customer = customerDAO.getOneCustomer(customerId);
		if (customer != null) {
			return customer;
		} else {
			throw new WrongIdException("customer id does not exist");
		}
	}
}
