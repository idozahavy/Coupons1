package com.coupons.tests.dbdao;

import com.coupons.beans.Category;
import com.coupons.dbdao.CategoryDBDAO;
import com.coupons.tests.Art;

public class CategoryDBDAOTest {

	public static void main(String[] args) {

		System.out.println();
		System.out.println(Art.stringToArtH1("CategoryDBDAO Test"));

		System.out.println();
		CategoryDBDAO dbdao = new CategoryDBDAO();
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Getting Category 2 "));
		System.out.println(dbdao.getCategory(2));
		
		System.out.println();
		System.out.println(Art.padTo120Stars(" Getting Category ID Computer "));
		System.out.println(dbdao.getCategoryId(Category.Computers));
	}

}
