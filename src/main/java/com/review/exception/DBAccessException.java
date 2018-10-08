package com.review.exception;

public class DBAccessException extends Exception {

	public DBAccessException() {
		super("Exception while accessing DB");
	}

	public DBAccessException(String message) {
		super(message);
	}
}
