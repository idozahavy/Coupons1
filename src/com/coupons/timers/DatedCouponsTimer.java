package com.coupons.timers;

import java.io.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import com.coupons.beans.Coupon;
import com.coupons.dao.CouponsDAO;
import com.coupons.dbdao.CouponsDBDAO;

public class DatedCouponsTimer implements Runnable {

	private static final String FILE_NAME = "last_dated_coupons_check.txt";

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
		} while (true);
	}

	private void clearOutdatedCoupons() {
		CouponsDAO couponsDAO = new CouponsDBDAO();
		List<Coupon> coupons = couponsDAO.getAllCoupons();
		for (Coupon coupon : coupons) {
			if (coupon.getEndDate().before(Date.valueOf(LocalDate.now()))) {
				couponsDAO.deleteCouponPurchases(coupon.getId());
				couponsDAO.deleteCoupon(coupon.getId());
				System.out.println("deleted coupon id = " + coupon.getId());
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
