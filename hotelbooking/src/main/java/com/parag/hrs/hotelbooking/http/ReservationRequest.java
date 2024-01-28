package com.parag.hrs.hotelbooking.http;

import java.time.LocalDate;


public class ReservationRequest {
	private LocalDate checkInDate;
	private LocalDate checkOutDate;
	private Long roomId;
	public LocalDate getCheckInDate() {
		return checkInDate;
	}
	public ReservationRequest(LocalDate checkInDate, LocalDate checkOutDate, Long roomId) {
		super();
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.roomId = roomId;
	}
	public Long getRoomId() {
		return roomId;
	}
	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate;
	}
	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}
	public void setChechOutDate(LocalDate chechOutDate) {
		this.checkOutDate = chechOutDate;
	}
}
