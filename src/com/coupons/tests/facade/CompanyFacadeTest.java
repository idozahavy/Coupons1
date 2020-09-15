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
import com.coupons.tests.Table100;

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
		Table100.print(facade.getCompanyDetails());

		System.out.println(Art.padTo120Stars(" Get Company Coupons "));
		Table100.print(facade.getCompanyCoupons());

		System.out.println(Art.padTo120Stars(" Get Company Coupons Category Computers "));
		Table100.print(facade.getCompanyCoupons(Category.Computers));

		System.out.println(Art.padTo120Stars(" Get Company Coupons Max Price 40 "));
		Table100.print(facade.getCompanyCoupons(40));

		System.out.println(Art.padTo120Stars(" Add Coupon "));
		System.out.println("Before - ");
		Table100.print(facade.getCompanyCoupons());
		try {
			facade.addCoupon(new Coupon(facade.getCompanyDetails().getId(), Category.Technology, "couptitle", "desc434",
					Date.valueOf("2019-05-12"), Date.valueOf("2015-05-12"), 62, 19.99, "shlik"));
		} catch (DetailDuplicationException | WrongIdException e) {
			System.err.println(e.getMessage());
		}
		System.out.println(" After - ");
		List<Coupon> companyCoupons = facade.getCompanyCoupons();
		Table100.print(companyCoupons);
		Coupon tempCoupon = companyCoupons.get(companyCoupons.size() - 1);

		System.out.println(Art.padTo120Stars(" Add Coupon (Fail - coupon title already exist) "));
		try {
			facade.addCoupon(new Coupon(facade.getCompanyDetails().getId(), Category.Technology, "couptitle", "desc434",
					Date.valueOf("2019-05-12"), Date.valueOf("2015-05-12"), 62, 19.99, "shlik"));
			System.err.println("failed error------------------------------");
		} catch (DetailDuplicationException | WrongIdException e) {
			System.out.println(e.getMessage());
		}
		System.out.println(" After - ");
		Table100.print(facade.getCompanyCoupons());

		System.out.println(Art.padTo120Stars(" Add Coupon (Fail - coupon.companyId not correct) "));
		System.out.println("Before - ");
		Table100.print(couponsDBDAO.getAllCoupons());
		try {
			facade.addCoupon(new Coupon(facade.getCompanyDetails().getId() + 1, Category.Technology, "couptitle",
					"desc434", Date.valueOf("2019-05-12"), Date.valueOf("2015-05-12"), 62, 19.99, "shlik"));
			System.err.println("failed error------------------------------");
		} catch (DetailDuplicationException | WrongIdException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.println(" After - ");
		Table100.print(couponsDBDAO.getAllCoupons());

		System.out.println(Art.padTo120Stars(" Update Coupon [category] "));
		System.out.println("Before - ");
		Table100.print(couponsDBDAO.getOneCoupon(tempCoupon.getId()));
		tempCoupon.setCategory(Category.Restaurant);
		try {
			facade.updateCoupon(tempCoupon);
		} catch (WrongIdException | DataManipulationException e) {
			System.err.println(e.getMessage());
		}
		System.out.println(" After - ");
		Table100.print(couponsDBDAO.getOneCoupon(tempCoupon.getId()));

		System.out
				.println(Art.padTo120Stars(" Update Coupon [category, companyId] (Fail - can not change companyId) "));
		tempCoupon.setCategory(Category.Vacation);
		tempCoupon.setCompanyId(-1);
		try {
			facade.updateCoupon(tempCoupon);
			System.err.println("failed error------------------------------");
		} catch (WrongIdException | DataManipulationException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.println(" After - ");
		Table100.print(couponsDBDAO.getOneCoupon(tempCoupon.getId()));
		tempCoupon.setCompanyId(facade.getCompanyDetails().getId());

		System.out.println(Art.padTo120Stars(" Update Coupon -1 (Fail - coupon id does not exist) "));
		System.out.println("Before - ");
		Table100.print(facade.getCompanyCoupons());
		tempCoupon.setCategory(Category.Vacation);
		int tempCouponId = tempCoupon.getId();
		tempCoupon.setId(-1);
		try {
			facade.updateCoupon(tempCoupon);
			System.err.println("failed error------------------------------");
		} catch (WrongIdException | DataManipulationException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.println(" After - ");
		Table100.print(facade.getCompanyCoupons());
		tempCoupon.setId(tempCouponId);

		System.out.println(Art.padTo120Stars(" Update Coupon 3 (Fail - coupon does not belong to company) "));
		tempCoupon.setCategory(Category.Vacation);
		tempCoupon.setId(3);
		try {
			facade.updateCoupon(tempCoupon);
			System.err.println("failed error------------------------------");
		} catch (WrongIdException | DataManipulationException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		tempCoupon.setId(tempCouponId);

		tempCoupon.setId(3);
		System.out.println();
		System.out.println(Art.padTo120Stars(" Delete Coupon 3 (Fail - coupon doesn't belong to company) "));
		System.out.println("Before - ");
		Table100.print(couponsDBDAO.getAllCoupons());
		try {
			facade.deleteCoupon(tempCoupon.getId());
			System.err.println("failed error------------------------------");
		} catch (WrongIdException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.println(" After - ");
		Table100.print(couponsDBDAO.getAllCoupons());
		tempCoupon.setId(tempCouponId);

		System.out.println(Art.padTo120Stars(" Delete Coupon (Fail, wrong id) "));
		try {
			facade.deleteCoupon(-1);
			System.err.println("failed error------------------------------");
		} catch (WrongIdException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.println(" After - ");
		Table100.print(couponsDBDAO.getAllCoupons());

		System.out.println(Art.padTo120Stars(" Delete Coupon "));
		try {
			facade.deleteCoupon(tempCoupon.getId());
		} catch (WrongIdException e) {
			System.err.println(e.getMessage());
		}
		System.out.println(" After - ");
		Table100.print(facade.getCompanyCoupons());
	}

}
