package com.parag.hrs.hotelbooking.exception;

public class ReservationNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;
	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ReservationNotFoundException(String message) {
		super();
		this.message = message;
	}

	
}
