package com.coupons.tests.facade;

import java.sql.Date;
import java.util.List;

import com.coupons.beans.Category;
import com.coupons.beans.Coupon;
import com.coupons.dbdao.CouponsDBDAO;
import com.coupons.exceptions.DataManipulationException;
import com.coupons.exceptions.DetailDuplicationException;
import com.coupons.exceptions.NotLoggedInExcepetion;
import com.coupons.exceptions.WrongIdException;
import com.coupons.facade.CompanyFacade;
import com.coupons.tests.Art;

public class CompanyFacadeTest {

	public static void main(String[] args) throws WrongIdException, NotLoggedInExcepetion {
		System.out.println();
		System.out.println(Art.stringToArtH1("CompanyFacade Test"));

		System.out.println();
		CompanyFacade facade = new CompanyFacade();
		CouponsDBDAO couponsDBDAO = new CouponsDBDAO();

		System.out.println();
		System.out.println(Art.padTo120Stars(" Login (Fail - wrong credentials) "));
		System.out.println(facade.login("no", "yes"));

		System.out.println();
		System.out.println(Art.padTo120Stars(" Login (Success) "));
		System.out.println(facade.login("mymail@cc.com", "1234"));
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Get Details "));
		System.out.println(facade.getCompanyDetails());

		System.out.println();
		System.out.println(Art.padTo120Stars(" Get Company Coupons "));
		System.out.println(facade.getCompanyCoupons());
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Get Company Coupons Category Computers "));
		System.out.println(facade.getAllCouponsByCategory(Category.Computers));
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Get Company Coupons Max Price 40 "));
		System.out.println(facade.getAllCouponsByMaxPrice(40));

		System.out.println();
		System.out.println(Art.padTo120Stars(" Add Coupon "));
		System.out.print("Before - ");
		System.out.println(facade.getCompanyCoupons());
		try {
			facade.addCoupon(new Coupon(facade.getCompanyDetails().getId(), Category.Technology, "couptitle", "desc434",
					Date.valueOf("2019-05-12"), Date.valueOf("2015-05-12"), 62, 19.99, "shlik"));
		} catch (DetailDuplicationException | WrongIdException e) {
			e.printStackTrace();
		}
		System.out.print(" After - ");
		List<Coupon> companyCoupons = facade.getCompanyCoupons();
		System.out.println(companyCoupons);
		Coupon tempCoupon = companyCoupons.get(companyCoupons.size() - 1);
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Add Coupon (Fail - coupon.companyId not correct) "));
		System.out.print("Before - ");
		System.out.println(couponsDBDAO.getAllCoupons());
		try {
			facade.addCoupon(new Coupon(facade.getCompanyDetails().getId()+1, Category.Technology, "couptitle", "desc434",
					Date.valueOf("2019-05-12"), Date.valueOf("2015-05-12"), 62, 19.99, "shlik"));
		} catch (DetailDuplicationException | WrongIdException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.print(" After - ");
		System.out.println(couponsDBDAO.getAllCoupons());

		System.out.println();
		System.out.println(Art.padTo120Stars(" Update Coupon (category) "));
		System.out.print("Before - ");
		System.out.println(couponsDBDAO.getOneCoupon(tempCoupon.getId()));
		tempCoupon.setCategory(Category.Restaurant);
		try {
			facade.updateCoupon(tempCoupon);
		} catch (WrongIdException | DataManipulationException e) {
			e.printStackTrace();
		}
		System.out.print(" After - ");
		System.out.println(couponsDBDAO.getOneCoupon(tempCoupon.getId()));

		System.out.println();
		System.out.println(Art.padTo120Stars(" Update Coupon [category, companyId] (Fail - can not change companyId) "));
		System.out.print("Before - ");
		System.out.println(couponsDBDAO.getOneCoupon(tempCoupon.getId()));
		tempCoupon.setCategory(Category.Vacation);
		tempCoupon.setCompanyId(-1);
		try {
			facade.updateCoupon(tempCoupon);
		} catch (WrongIdException | DataManipulationException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.print(" After - ");
		System.out.println(couponsDBDAO.getOneCoupon(tempCoupon.getId()));
		tempCoupon.setCompanyId(facade.getCompanyDetails().getId());

		int tempId = tempCoupon.getId();
		tempCoupon.setId(3);
		System.out.println();
		System.out.println(Art.padTo120Stars(" Delete Coupon (Fail - coupon doesn't belong to company) "));
		System.out.print("Before - ");
		System.out.println(couponsDBDAO.getAllCoupons());
		try {
			facade.deleteCoupon(tempCoupon.getId());
		} catch (WrongIdException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.print(" After - ");
		System.out.println(couponsDBDAO.getAllCoupons());
		tempCoupon.setId(tempId);
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Delete Coupon (Fail, wrong id) "));
		System.out.print("Before - ");
		System.out.println(facade.getCompanyCoupons());
		try {
			facade.deleteCoupon(-1);
		} catch (WrongIdException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.print(" After - ");
		System.out.println(facade.getCompanyCoupons());
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Delete Coupon "));
		System.out.print("Before - ");
		System.out.println(facade.getCompanyCoupons());
		try {
			facade.deleteCoupon(tempCoupon.getId());
		} catch (WrongIdException e) {
			e.printStackTrace();
		}
		System.out.print(" After - ");
		System.out.println(facade.getCompanyCoupons());
	}

}
