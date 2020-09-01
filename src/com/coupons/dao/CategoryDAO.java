package com.coupons.dao;

import com.coupons.beans.Category;

public interface CategoryDAO {

	void addCategory(Category category);

	Category getCategory(int categoryId);
	
	int getCategoryId(Category category);
}
