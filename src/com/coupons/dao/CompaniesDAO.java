package com.coupons.dao;

import java.util.List;

import com.coupons.beans.Company;

public interface CompaniesDAO {

	boolean isCompanyExists(String email, String password);
	
	Company getCompanyByEmailPassword(String email, String password);
	
	boolean canAddCompany(String email, String name);

	void addCompany(Company company);

	void updateCompany(Company company);

	void deleteCompany(int companyId);

	List<Company> getAllCompanies();

	Company getOneCompany(int companyId);

}
