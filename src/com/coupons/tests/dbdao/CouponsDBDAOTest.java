package com.coupons.tests.dbdao;

import java.sql.Date;
import java.util.List;

import com.coupons.beans.Category;
import com.coupons.beans.Coupon;
import com.coupons.dbdao.CouponsDBDAO;
import com.coupons.dbdao.CustomerDBDAO;
import com.coupons.tests.Art;

public class CouponsDBDAOTest {

	public static void main(String[] args) {
		System.out.println();
		System.out.println(Art.stringToArtH1("CouponsDBDAO Test"));

		System.out.println();
		CouponsDBDAO dbdao = new CouponsDBDAO();
		System.out.println(dbdao.getAllCoupons());
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Add Coupon "));
		dbdao.addCoupon(new Coupon(1, Category.Food, "tempCoupon", "tempCouponDesc", Date.valueOf("2020-08-01"), Date.valueOf("2022-08-01"), 42, 42.9, "nope"));
		List<Coupon> allCoupons = dbdao.getAllCoupons();
		System.out.println(allCoupons);
		Coupon tempCoupon = allCoupons.get(allCoupons.size()-1);
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Can Add Coupon (True) "));
		System.out.println(dbdao.canAddCoupon("new coupon123", 1));
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Can Add Coupon (False - existing coupon name in same comapny) "));
		System.out.println(dbdao.canAddCoupon("new coupon", 1));
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Get Company 1 Coupons "));
		System.out.println(dbdao.getCompanyCoupons(1));
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Get Customer 1 Coupons "));
		System.out.println(dbdao.getCustomerCoupons(1));
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Get One Coupon "));
		System.out.println(dbdao.getOneCoupon(tempCoupon.getId()));
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Add Coupon Purchase customer 1, new tempCoupon "));
		System.out.print("Before - ");
		System.out.println(new CustomerDBDAO().getOneCustomer(1));
		dbdao.addCouponPurchase(1, tempCoupon.getId());
		System.out.print(" After - ");
		System.out.println(new CustomerDBDAO().getOneCustomer(1));
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Delete Coupon Purchase customer 1, new tempCoupon "));
		System.out.print("Before - ");
		System.out.println(new CustomerDBDAO().getOneCustomer(1));
		dbdao.deleteCouponPurchase(1, tempCoupon.getId());
		System.out.print(" After - ");
		System.out.println(new CustomerDBDAO().getOneCustomer(1));
		
		System.out.println();
		dbdao.addCouponPurchase(1, tempCoupon.getId());
		System.out.println(Art.padTo120Stars(" Delete Coupon Purchases new tempCoupon "));
		System.out.print("Before - ");
		System.out.println(new CustomerDBDAO().getOneCustomer(1));
		dbdao.deleteCouponPurchases(tempCoupon.getId());
		System.out.print(" After - ");
		System.out.println(new CustomerDBDAO().getOneCustomer(1));
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Get One Coupon "));
		System.out.println(dbdao.getOneCoupon(1));
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Get One Coupon (no such id) "));
		System.out.println(dbdao.getOneCoupon(-1));
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Update Coupon (desc, image) "));
		System.out.print("Before - ");
		System.out.println(dbdao.getOneCoupon(tempCoupon.getId()));
		tempCoupon.setDescription("new_description");
		tempCoupon.setImage("new_image");
		dbdao.updateCoupon(tempCoupon);
		System.out.print(" After - ");
		System.out.println(dbdao.getOneCoupon(tempCoupon.getId()));
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Delete Coupon "));
		System.out.print("Before - ");
		System.out.println(dbdao.getAllCoupons());
		dbdao.deleteCoupon(tempCoupon.getId());
		System.out.print(" After - ");
		System.out.println(dbdao.getAllCoupons());
	}

}
