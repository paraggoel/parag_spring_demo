package com.parag.hrs.hotelbooking.exception;

public class RoomNotFoundException extends RuntimeException {
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

	public RoomNotFoundException(String message) {
		super();
		this.message = message;
	}

	
}
