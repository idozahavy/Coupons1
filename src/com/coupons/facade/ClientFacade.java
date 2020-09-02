package com.coupons.facade;

import com.coupons.dbdao.CompaniesDBDAO;
import com.coupons.dbdao.CouponsDBDAO;
import com.coupons.dbdao.CustomerDBDAO;

public abstract class ClientFacade {
	protected CouponsDBDAO couponsDBDAO = null;
	protected CompaniesDBDAO companiesDBDAO = null;
	protected CustomerDBDAO customerDBDAO = null;

	public ClientFacade() {

	}

	protected void Initialize() {
		couponsDBDAO = new CouponsDBDAO();
		companiesDBDAO = new CompaniesDBDAO();
		customerDBDAO = new CustomerDBDAO();
	}
	
	protected void Deinitialize() {
		couponsDBDAO = null;
		companiesDBDAO = null;
		customerDBDAO = null;
	}


	public abstract boolean login(String email, String password);
}
