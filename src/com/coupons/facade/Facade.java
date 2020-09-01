package com.coupons.facade;

import com.coupons.dbdao.CompaniesDBDAO;
import com.coupons.dbdao.CouponsDBDAO;
import com.coupons.dbdao.CustomerDBDAO;

public abstract class Facade {
	protected CouponsDBDAO couponsDBDAO;
	protected CompaniesDBDAO companiesDBDAO;
	protected CustomerDBDAO customerDBDAO;
	
	public Facade() {
		couponsDBDAO = new CouponsDBDAO();
		companiesDBDAO = new CompaniesDBDAO();
		customerDBDAO = new CustomerDBDAO();
	}
	
	public abstract boolean login(String email, String password);
}
