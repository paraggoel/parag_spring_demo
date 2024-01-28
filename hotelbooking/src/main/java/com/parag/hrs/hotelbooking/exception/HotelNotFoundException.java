package com.parag.hrs.hotelbooking.exception;

public class HotelNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private String message;

	public HotelNotFoundException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
