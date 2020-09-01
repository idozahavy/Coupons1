package com.coupons.login;

import com.coupons.facade.AdminFacade;
import com.coupons.facade.CompanyFacade;
import com.coupons.facade.CustomerFacade;
import com.coupons.facade.ClientFacade;

public class LoginManager {

	public static ClientFacade login(String email, String password, ClientType clientType) {
		ClientFacade facade = null;
		switch (clientType) {
		case ADMINISTRATOR:
			facade = new AdminFacade();
			break;
		case COMPANY:
			facade = new CompanyFacade();
			break;
		case CUSTOMER:
			facade = new CustomerFacade();
			break;
		default:
			throw new RuntimeException("Switch case could not catch type " + clientType.toString());
		}
		if (facade.login(email, password)) {
			return facade;
		}
		return null;
	}

}
