package com.coupons.facade;

import java.util.List;

import com.coupons.beans.Category;
import com.coupons.beans.Company;
import com.coupons.beans.Coupon;
import com.coupons.exceptions.DataManipulationException;
import com.coupons.exceptions.DetailDuplicationException;
import com.coupons.exceptions.NotLoggedInExcepetion;
import com.coupons.exceptions.WrongIdException;

public class CompanyFacade extends ClientFacade {

	private int companyId;

	public boolean login(String email, String password) {
		Initialize();
		if (companiesDBDAO.isCompanyExists(email, password)) {
			companyId = companiesDBDAO.getCompanyByEmailPassword(email, password).getId();
			return true;
		}
		Deinitialize();
		return false;
	}

	public void addCoupon(Coupon coupon) throws DetailDuplicationException, WrongIdException, NotLoggedInExcepetion {
		if (couponsDBDAO == null) {
			throw new NotLoggedInExcepetion("you did not logged in properly - check your credentials");
		}
		if (coupon.getCompanyId() == companyId) {
			if (couponsDBDAO.canAddCoupon(coupon.getTitle(), coupon.getCompanyId())) {
				couponsDBDAO.addCoupon(coupon);
			} else {
				throw new DetailDuplicationException("coupon title already exist in company");
			}
		} else {
			throw new WrongIdException("coupon company id does not match company id");
		}
	}

	public void updateCoupon(Coupon coupon) throws WrongIdException, DataManipulationException, NotLoggedInExcepetion {
		if (couponsDBDAO == null) {
			throw new NotLoggedInExcepetion("you did not logged in properly - check your credentials");
		}
		if (coupon.getCompanyId() == companyId) {
			Coupon dbCoupon = couponsDBDAO.getOneCoupon(coupon.getId());
			if (dbCoupon == null) {
				throw new WrongIdException("coupon id does not exist");
			}
			if (dbCoupon.getCompanyId() != companyId) {
				throw new WrongIdException("you can not change a coupon that does not belong to your company");
			}
			if (dbCoupon.getCompanyId() == coupon.getCompanyId() && dbCoupon.getId() == coupon.getId()) {
				couponsDBDAO.updateCoupon(coupon);
			} else {
				throw new DataManipulationException("you can not change coupon comapny id");
			}
		}else {
			throw new WrongIdException("coupon company id does not match company id");
		}
	}

	public void deleteCoupon(int couponId) throws WrongIdException, NotLoggedInExcepetion {
		if (couponsDBDAO == null) {
			throw new NotLoggedInExcepetion("you did not logged in properly - check your credentials");
		}
		Coupon dbCoupon = couponsDBDAO.getOneCoupon(couponId);
		if (dbCoupon == null) {
			throw new WrongIdException("coupon id does not exist");
		}
		if (dbCoupon.getCompanyId() == companyId) {
			couponsDBDAO.deleteCouponPurchases(couponId);
			couponsDBDAO.deleteCoupon(couponId);
		} else {
			throw new WrongIdException("coupon company id does not match company id");
		}
	}

	public List<Coupon> getCompanyCoupons() throws NotLoggedInExcepetion {
		if (couponsDBDAO == null) {
			throw new NotLoggedInExcepetion("you did not logged in properly - check your credentials");
		}
		return couponsDBDAO.getCompanyCoupons(companyId);
	}

	public List<Coupon> getAllCouponsByCategory(Category category) throws NotLoggedInExcepetion {
		if (couponsDBDAO == null) {
			throw new NotLoggedInExcepetion("you did not logged in properly - check your credentials");
		}
		List<Coupon> coupons = getCompanyCoupons();
		for (int i = 0; i < coupons.size(); i++) {
			if (coupons.get(i).getCategory().equals(category) == false) {
				coupons.remove(i--);
			}
		}
		return coupons;
	}

	public List<Coupon> getAllCouponsByMaxPrice(int maxPrice) throws NotLoggedInExcepetion {
		if (couponsDBDAO == null) {
			throw new NotLoggedInExcepetion("you did not logged in properly - check your credentials");
		}
		List<Coupon> coupons = getCompanyCoupons();
		for (int i = 0; i < coupons.size(); i++) {
			if (coupons.get(i).getPrice() > maxPrice) {
				coupons.remove(i--);
			}
		}
		return coupons;
	}

	public Company getCompanyDetails() throws WrongIdException, NotLoggedInExcepetion {
		if (couponsDBDAO == null) {
			throw new NotLoggedInExcepetion("you did not logged in properly - check your credentials");
		}
		Company company = companiesDBDAO.getOneCompany(companyId);
		if (company != null) {
			return company;
		} else {
			throw new WrongIdException("coupon company id does not match company id");
		}
	}

}
