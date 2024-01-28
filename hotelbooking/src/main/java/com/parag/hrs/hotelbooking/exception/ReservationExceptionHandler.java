package com.parag.hrs.hotelbooking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ReservationExceptionHandler {

	@ExceptionHandler(HotelNotFoundException.class)
	public ProblemDetail handleHotelNotFoundException(HotelNotFoundException ex) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
	}
	
	@ExceptionHandler(RoomNotFoundException.class)
	public ProblemDetail handleRoomNotFoundException(RoomNotFoundException ex) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,ex.getMessage());
	}
	
	@ExceptionHandler(ReservationNotFoundException.class)
	public ProblemDetail handleReservationNotFoundException(ReservationNotFoundException ex) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,ex.getMessage());
	}
	
	@ExceptionHandler(Exception.class)
	public ProblemDetail handleGenericException(ReservationNotFoundException ex) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage());
	}
}
