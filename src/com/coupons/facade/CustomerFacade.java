package com.coupons.facade;

public class CustomerFacade extends ClientFacade {

	private int customerId;
	
	@Override
	public boolean login(String email, String password) {
		if (customerDBDAO.isCustomerExists(email, password)) {
			customerId = customerDBDAO.GetCustomerByEmailPassword(email, password).getId();
			return true;
		}
		return false;
	}

}
