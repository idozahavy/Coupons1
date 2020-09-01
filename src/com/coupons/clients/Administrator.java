package com.coupons.clients;

import com.coupons.beans.Company;
import com.coupons.dbdao.CompaniesDBDAO;

public class Administrator implements Client {

	public void addCompany(Company company) {
		CompaniesDBDAO companiesDBDAO;
		companiesDBDAO = new CompaniesDBDAO();
		companiesDBDAO.addCompany(company);
	}
	
	public void updateCompany(Company company) {
		CompaniesDBDAO companiesDBDAO;
		companiesDBDAO = new CompaniesDBDAO();
		companiesDBDAO.updateCompany(company);
	}
}
