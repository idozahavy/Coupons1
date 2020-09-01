package com.coupons.db;

import java.sql.Connection;

public interface ConnectionMethod<T> {
	T run(Connection conn);
}
