package com.coupons.tests.facade;

import java.sql.Date;
import java.util.List;

import com.coupons.beans.Category;
import com.coupons.beans.Coupon;
import com.coupons.dbdao.CouponsDBDAO;
import com.coupons.exceptions.DataManipulationException;
import com.coupons.exceptions.NotLoggedInExcepetion;
import com.coupons.exceptions.WrongIdException;
import com.coupons.facade.CustomerFacade;
import com.coupons.tests.Art;
import com.coupons.tests.Table100;

public class CustomerFacadeTest {

	public static void main(String[] args) throws WrongIdException, NotLoggedInExcepetion {
		System.out.println();
		System.out.println(Art.stringToArtH1("CustomerFacade Test"));

		System.out.println();
		CustomerFacade facade = new CustomerFacade();
		CouponsDBDAO couponsDBDAO = new CouponsDBDAO();

		System.out.println();
		System.out.println(Art.padTo120Stars(" Login (Fail) "));
		System.out.println(facade.login("no", "yes"));

		System.out.println();
		System.out.println(Art.padTo120Stars(" Login (Success) "));
		System.out.println(facade.login("mail1", "1"));
		
		List<Coupon> allCoupons = couponsDBDAO.getAllCoupons();
		Coupon couponPurchased = allCoupons.get(allCoupons.size() - 1);
		couponsDBDAO.deleteCouponPurchase(facade.getCustomerDetails().getId(), couponPurchased.getId());

		System.out.println();
		System.out.println(Art.padTo120Stars(" Get Details "));
		Table100.print(facade.getCustomerDetails());

		System.out.println(Art.padTo120Stars(" Get Customer Coupons "));
		Table100.print(facade.getCustomerCoupons());

		System.out.println(Art.padTo120Stars(" Get Customer Coupons Category Computers "));
		Table100.print(facade.getCustomerCoupons(Category.Computers));

		System.out.println(Art.padTo120Stars(" Get Customer Coupons Max Price 40 "));
		Table100.print(facade.getCustomerCoupons(40));

		System.out.println(Art.padTo120Stars(" Purchase Coupon "));
		System.out.println("Before Coupons - ");
		Table100.print(couponsDBDAO.getAllCoupons());
		System.out.println("Before - ");
		Table100.print(facade.getCustomerCoupons());
		try {
			facade.purchaseCoupon(couponPurchased);
		} catch (WrongIdException | DataManipulationException e) {
			System.err.println(e.getMessage());
		}
		System.out.println(" After - ");
		Table100.print(facade.getCustomerCoupons());
		System.out.println(" After Coupons - ");
		Table100.print(couponsDBDAO.getAllCoupons());

		System.out.println(Art.padTo120Stars(" Purchase Coupon (Fail - already own this coupon) "));
		System.out.println("Before - ");
		Table100.print(facade.getCustomerCoupons());
		try {
			facade.purchaseCoupon(couponPurchased);
			System.err.println("failed error------------------------------");
		} catch (WrongIdException | DataManipulationException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.println(" After - ");
		Table100.print(facade.getCustomerCoupons());
		
		System.out.println(Art.padTo120Stars(" Purchase Coupon (Fail - coupon does not exist) "));
		try {
			Coupon noCoupon = new Coupon(0, 0, Category.AI, "", "", new Date(0), new Date(0), 0, 0, "");
			facade.purchaseCoupon(noCoupon);
			System.err.println("failed error------------------------------");
		} catch (WrongIdException | DataManipulationException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		
		System.out.println(Art.padTo120Stars(" Purchase Coupon (Fail - coupon amount is 0) "));
		try {
			Coupon noAmountCoupon = new Coupon(4, 0, Category.AI, "", "", new Date(0), new Date(0), 0, 0, "");
			facade.purchaseCoupon(noAmountCoupon);
			System.err.println("failed error------------------------------");
		} catch (WrongIdException | DataManipulationException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
	}

}
