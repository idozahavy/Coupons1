package com.coupons.dao;

import java.util.List;

import com.coupons.beans.Coupon;

public interface CouponsDAO {

	void addCoupon(Coupon coupon);
	
	boolean canAddCoupon(String title, int companyId);

	void updateCoupon(Coupon coupon);

	void deleteCoupon(int couponId);

	List<Coupon> getAllCoupons();
	
	List<Coupon> getCustomerCoupons(int customerId);
	
	List<Coupon> getCompanyCoupons(int companyId);

	Coupon getOneCoupon(int couponId);

	void addCouponPurchase(int customerId, int couponId);

	void deleteCouponPurchase(int customerId, int couponId);
	
	void deleteCouponPurchases(int couponId);
}
