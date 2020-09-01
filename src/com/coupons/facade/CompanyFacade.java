package com.coupons.facade;

import java.util.List;

import com.coupons.beans.Category;
import com.coupons.beans.Company;
import com.coupons.beans.Coupon;

public class CompanyFacade extends Facade {

	private int companyId;

	public boolean login(String email, String password) {
		if (companiesDBDAO.isCompanyExists(email, password)) {
			companyId = companiesDBDAO.getCompanyByEmailPassword(email, password).getId();
			return true;
		}
		return false;

	}

	public void addCoupon(Coupon coupon) {
		if (coupon.getCompanyId() == companyId) {
			if (couponsDBDAO.canAddCoupon(coupon.getTitle(), coupon.getCompanyId())) {
				couponsDBDAO.addCoupon(coupon);
			}
		}
	}

	public void updateCoupon(Coupon coupon) {
		if (coupon.getCompanyId() == companyId) {
			Coupon dbCoupon = couponsDBDAO.getOneCoupon(coupon.getId());
			if (dbCoupon == null || dbCoupon.getCompanyId() != companyId) {
				return;
			}
			if (dbCoupon.getCompanyId() == coupon.getCompanyId() && dbCoupon.getId() == coupon.getId()) {
				couponsDBDAO.updateCoupon(coupon);
			}
		}
	}

	public void deleteCoupon(int couponId) {
		Coupon dbCoupon = couponsDBDAO.getOneCoupon(couponId);
		if (dbCoupon == null) {
			return;
		}
		if (dbCoupon.getCompanyId() == companyId) {
			couponsDBDAO.deleteCouponPurchases(couponId);
			couponsDBDAO.deleteCoupon(couponId);
		}

	}

	public List<Coupon> getCompanyCoupons() {
		return couponsDBDAO.getCompanyCoupons(companyId);
	}

	public List<Coupon> getAllCouponsByCategory(Category category) {
		List<Coupon> coupons = getCompanyCoupons();
		for (int i = 0; i < coupons.size(); i++) {
			if (coupons.get(i).getCategory() != category) {
				coupons.remove(i--);
			}
		}
		return coupons;
	}

	public List<Coupon> getAllCouponsByMaxPrice(int maxPrice) {
		List<Coupon> coupons = getCompanyCoupons();
		for (int i = 0; i < coupons.size(); i++) {
			if (coupons.get(i).getPrice() > maxPrice) {
				coupons.remove(i--);
			}
		}
		return coupons;
	}

	public Company getCompanyDetails() {
		return companiesDBDAO.getOneCompany(companyId);
	}

}
