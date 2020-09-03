package com.coupons.facade;


import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import com.coupons.beans.Category;
import com.coupons.beans.Coupon;
import com.coupons.beans.Customer;
import com.coupons.exceptions.DataManipulationException;
import com.coupons.exceptions.NotLoggedInExcepetion;
import com.coupons.exceptions.WrongIdException;

public class CustomerFacade extends ClientFacade {

	private int customerId;

	@Override
	public boolean login(String email, String password) {
		Initialize();
		if (customerDBDAO.isCustomerExists(email, password)) {
			customerId = customerDBDAO.GetCustomerByEmailPassword(email, password).getId();
			return true;
		}
		Deinitialize();
		return false;
		
	}

	public void purchaseCoupon(Coupon coupon) throws DataManipulationException, WrongIdException, NotLoggedInExcepetion {
		if (couponsDBDAO == null) {
			throw new NotLoggedInExcepetion("you did not logged in properly - check your credentials");
		}
		Coupon dbCoupon = couponsDBDAO.getOneCoupon(coupon.getId());
		if (dbCoupon == null) {
			throw new WrongIdException("coupon id does not exist");
		}
		boolean isCouponPurchaseExist = false;
		for (Coupon customerCoupon : getCustomerCoupons()) {
			isCouponPurchaseExist |= customerCoupon.getId() == dbCoupon.getId();
		}
		boolean canBuyCoupon = dbCoupon.getAmount() > 0 && dbCoupon.getEndDate().after(Date.valueOf(LocalDate.now()));
		if (isCouponPurchaseExist) {
			throw new DataManipulationException("can not purchase coupon that customer already has");
		}
		if (canBuyCoupon) {
			couponsDBDAO.addCouponPurchase(customerId, dbCoupon.getId());
			dbCoupon.setAmount(dbCoupon.getAmount() - 1);
			couponsDBDAO.updateCoupon(dbCoupon);
		} else {
			throw new DataManipulationException("can not buy coupon because of endDate/amount");
		}
	}

	public List<Coupon> getCustomerCoupons() throws WrongIdException, NotLoggedInExcepetion {
		if (couponsDBDAO == null) {
			throw new NotLoggedInExcepetion("you did not logged in properly - check your credentials");
		}
		Customer customer = getCustomerDetails();
		return customer.getCoupons();
	}

	public List<Coupon> getCustomerCoupons(Category category) throws WrongIdException, NotLoggedInExcepetion {
		if (couponsDBDAO == null) {
			throw new NotLoggedInExcepetion("you did not logged in properly - check your credentials");
		}
		List<Coupon> coupons = getCustomerCoupons();
		for (int i = 0; i < coupons.size(); i++) {
			if (coupons.get(i).getCategory().equals(category) == false) {
				coupons.remove(i--);
			}
		}
		return coupons;
	}

	public List<Coupon> getCustomerCoupons(double maxPrice) throws WrongIdException, NotLoggedInExcepetion {
		if (couponsDBDAO == null) {
			throw new NotLoggedInExcepetion("you did not logged in properly - check your credentials");
		}
		List<Coupon> coupons = getCustomerCoupons();
		for (int i = 0; i < coupons.size(); i++) {
			if (coupons.get(i).getPrice() > maxPrice) {
				coupons.remove(i--);
			}
		}
		return coupons;
	}

	public Customer getCustomerDetails() throws WrongIdException, NotLoggedInExcepetion {
		if (couponsDBDAO == null) {
			throw new NotLoggedInExcepetion("you did not logged in properly - check your credentials");
		}
		Customer customer = customerDBDAO.getOneCustomer(customerId);
		if (customer == null) {
			throw new WrongIdException("customer id does not exist");
		}
		return customer;
	}

}
