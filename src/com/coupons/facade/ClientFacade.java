package com.coupons.facade;

import com.coupons.dao.*;
import com.coupons.dbdao.*;

public abstract class ClientFacade {
	protected CouponsDAO couponsDAO = null;
	protected CompaniesDAO companiesDAO = null;
	protected CustomersDAO customerDAO = null;

	public ClientFacade() {

	}

	protected void Initialize() {
		couponsDAO = new CouponsDBDAO();
		companiesDAO = new CompaniesDBDAO();
		customerDAO = new CustomerDBDAO();
	}
	
	protected void Deinitialize() {
		couponsDAO = null;
		companiesDAO = null;
		customerDAO = null;
	}


	public abstract boolean login(String email, String password);
}
