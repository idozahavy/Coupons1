package com.coupons.tests.facade;

import java.util.List;

import com.coupons.beans.Category;
import com.coupons.beans.Coupon;
import com.coupons.dbdao.CouponsDBDAO;
import com.coupons.exceptions.DataManipulationException;
import com.coupons.exceptions.NotLoggedInExcepetion;
import com.coupons.exceptions.WrongIdException;
import com.coupons.facade.CustomerFacade;
import com.coupons.tests.Art;

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
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Get Details "));
		System.out.println(facade.getCustomerDetails());

		System.out.println();
		System.out.println(Art.padTo120Stars(" Get Customer Coupons "));
		System.out.println(facade.getCustomerCoupons());
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Get Customer Coupons Category Computers "));
		System.out.println(facade.getCustomerCoupons(Category.Computers));
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Get Customer Coupons Max Price 40 "));
		System.out.println(facade.getCustomerCoupons(40));
		
		List<Coupon> allCoupons = couponsDBDAO.getAllCoupons();
		Coupon couponPurchased = allCoupons.get(allCoupons.size()-1);
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Purchase Coupon "));
		System.out.print("Before Coupons - ");
		System.out.println(couponsDBDAO.getAllCoupons());
		System.out.print("Before - ");
		System.out.println(facade.getCustomerCoupons());
		try {
			facade.purchaseCoupon(couponPurchased);
		} catch ( WrongIdException | DataManipulationException e) {
			e.printStackTrace();
		}
		System.out.print(" After - ");
		System.out.println(facade.getCustomerCoupons());
		System.out.print(" After Coupons - ");
		System.out.println(couponsDBDAO.getAllCoupons());
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Purchase Coupon (Fail - already own this coupon) "));
		System.out.print("Before - ");
		System.out.println(facade.getCustomerCoupons());
		try {
			facade.purchaseCoupon(couponPurchased);
		} catch (WrongIdException | DataManipulationException e) {
			System.out.println("Error Thrown - " + e.getMessage());
		}
		System.out.print(" After - ");
		System.out.println(facade.getCustomerCoupons());
	}

}
