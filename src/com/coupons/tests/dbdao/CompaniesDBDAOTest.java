package com.coupons.tests.dbdao;

import com.coupons.beans.Company;
import com.coupons.dbdao.CompaniesDBDAO;
import com.coupons.dbdao.CouponsDBDAO;
import com.coupons.tests.Art;
import com.coupons.tests.Table100;

public class CompaniesDBDAOTest {
	public static void main(String[] args) {
		System.out.println();
		System.out.println(Art.stringToArtH1("CompaniesDBDAO Test"));

		System.out.println();
		System.out.println(Art.padTo120Stars(" Get All Companies "));
		CompaniesDBDAO dbdao = new CompaniesDBDAO();
		System.out.println(dbdao.getAllCompanies());
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Add Company "));
		dbdao.addCompany(new Company("added", "new@email.com", "stampass"));
		System.out.println(dbdao.getAllCompanies());
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Is Company Exist (True) "));
		System.out.println(dbdao.isCompanyExists("new@email.com", "stampass"));
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Is Company Exist (False - wrong password) "));
		System.out.println(dbdao.isCompanyExists("new@email.com", ""));
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Can Add Company (True) "));
		System.out.println(dbdao.canAddCompany("noonecompany@email.com", "new_name"));
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Can Add Company (False - existing email) "));
		System.out.println(dbdao.canAddCompany("new@email.com", "existing_name"));
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Get Company By Email Pass "));
		Company tempCompany = dbdao.getCompanyByEmailPassword("new@email.com", "stampass");
		System.out.println(tempCompany);
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Get Company By Email Pass (wrong pass) "));
		System.out.println(dbdao.getCompanyByEmailPassword("new@email.com", "no_pass"));
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Get One Company "));
		System.out.println(dbdao.getOneCompany(1));
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Get One Company (no such id) "));
		System.out.println(dbdao.getOneCompany(-1));
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Update Company "));
		System.out.print("Before - ");
		System.out.println(dbdao.getOneCompany(tempCompany.getId()));
		tempCompany.setName("new_company_name");
		tempCompany.setPassword("new_company_password");
		dbdao.updateCompany(tempCompany);
		System.out.print(" After - ");
		System.out.println(dbdao.getOneCompany(tempCompany.getId()));
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Delete Company "));
		System.out.print("Before - ");
		System.out.println(dbdao.getAllCompanies());
		dbdao.deleteCompany(tempCompany.getId());
		System.out.print(" After - ");
		System.out.println(dbdao.getAllCompanies());
		
		Table100.print(dbdao.getAllCompanies());
		Table100.print(new CouponsDBDAO().getAllCoupons());

		Table100.print(tempCompany);
		
	}
}
