package com.coupons.tests.dbdao;

import com.coupons.beans.Company;
import com.coupons.dbdao.CompaniesDBDAO;
import com.coupons.tests.Art;
import com.coupons.tests.Table100;

public class CompaniesDBDAOTest {
	public static void main(String[] args) {
		System.out.println();
		System.out.println(Art.stringToArtH1("CompaniesDBDAO Test"));

		System.out.println();
		System.out.println(Art.padTo120Stars(" Get All Companies "));
		CompaniesDBDAO dbdao = new CompaniesDBDAO();
		Table100.print(dbdao.getAllCompanies());
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Add Company "));
		dbdao.addCompany(new Company("added", "new@email.com", "stampass"));
		Table100.print(dbdao.getAllCompanies());
		
		System.out.println(Art.padTo120Stars(" Is Company Exist (True) "));
		System.out.println(dbdao.isCompanyExists("new@email.com", "stampass"));
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Is Company Exist (False - wrong password) "));
		System.out.println(dbdao.isCompanyExists("new@email.com", ""));
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Can Add Company (True) "));
		System.out.println(dbdao.canAddCompany("noonecompany@email.com", "noonepass"));
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Can Add Company (False - existing email) "));
		System.out.println(dbdao.canAddCompany("new@email.com", "existing_name"));
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Get Company By Email Pass "));
		Company tempCompany = dbdao.getCompanyByEmailPassword("new@email.com", "stampass");
		Table100.print(tempCompany);
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Get Company By Email Pass (wrong pass) "));
		Table100.print(dbdao.getCompanyByEmailPassword("new@email.com", "no_pass"));
		
		System.out.println(Art.padTo120Stars(" Get One Company "));
		Table100.print(dbdao.getOneCompany(1));
		
		System.out.println(Art.padTo120Stars(" Get One Company (no such id) "));
		Table100.print(dbdao.getOneCompany(-1));
		
		System.out.println(Art.padTo120Stars(" Update Company "));
		tempCompany.setName("new_company_name");
		tempCompany.setPassword("new_company_password");
		dbdao.updateCompany(tempCompany);
		System.out.println(" After - ");
		Table100.print(dbdao.getOneCompany(tempCompany.getId()));
		
		System.out.println(Art.padTo120Stars(" Delete Company "));
		System.out.println("Before - ");
		Table100.print(dbdao.getAllCompanies());
		dbdao.deleteCompany(tempCompany.getId());
		System.out.println(" After - ");
		Table100.print(dbdao.getAllCompanies());
	}
}
