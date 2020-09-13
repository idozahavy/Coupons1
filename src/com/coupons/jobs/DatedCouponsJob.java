package com.coupons.jobs;

import java.io.*;
import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

import com.coupons.beans.Coupon;
import com.coupons.dao.CouponsDAO;
import com.coupons.dbdao.CouponsDBDAO;

public class DatedCouponsJob implements Runnable {

	private static final String FILE_NAME = "last_dated_coupons_check.txt";
	
	private CouponsDAO couponsDAO = null;
	private volatile boolean quit = false;

	public DatedCouponsJob() {
		couponsDAO = new CouponsDBDAO();
	}

	public void run() {
		do {
			synchronized (this.getClass()) {
				String lastDateString = readFile();
				if (lastDateString != null) {
					LocalDate lastDate = LocalDate.parse(lastDateString);
					LocalDate nowDate = LocalDate.now();

					// checks if now is after lastDate, equal dates does not evaluate to true
					if (nowDate.isAfter(lastDate)) {
						clearOutdatedCoupons();
						writeNowToFile();
					}
				} else {
					clearOutdatedCoupons();
					writeNowToFile();
				}
			}
			try {
				// waits for 1 hour
				Thread.sleep(60 * 60 * 1000);
			} catch (InterruptedException e) {
			}
		} while (quit == false);
	}
	
	public void stop() {
		quit = true;
	}
	
	

	private void clearOutdatedCoupons() {
		List<Coupon> coupons = couponsDAO.getAllCoupons();
		for (Coupon coupon : coupons) {
			if (coupon.getEndDate().before(Date.valueOf(LocalDate.now()))) {
				couponsDAO.deleteCouponPurchases(coupon.getId());
				couponsDAO.deleteCoupon(coupon.getId());
			}
		}
	}

	private void writeNowToFile() {
		File file = new File(FILE_NAME);
		try {
			FileWriter fileWriter = new FileWriter(file, false);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(LocalDate.now().toString());
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String readFile() {
		try {
			File file = new File(FILE_NAME);
			file.createNewFile();
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String lastDateString = bufferedReader.readLine();
			bufferedReader.close();
			return lastDateString;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
