package com.coupons.exceptions;

public class WrongIdException extends Exception {
	private static final long serialVersionUID = 63471L;

	public WrongIdException(String message) {
		super(message);
	}
}
