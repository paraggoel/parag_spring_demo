package com.parag.hrs.hotelbooking.service;

import java.util.List;

import com.parag.hrs.hotelbooking.entity.ReservationEntity;
import com.parag.hrs.hotelbooking.exception.ReservationNotFoundException;
import com.parag.hrs.hotelbooking.exception.RoomNotFoundException;
import com.parag.hrs.hotelbooking.http.ReservationRequest;

public interface ReservationService {
	
	public ReservationEntity reserve(ReservationRequest request) throws RoomNotFoundException;
	
	public ReservationEntity cancel(Long bookingId) throws ReservationNotFoundException;
	
	public ReservationEntity update(Long bookingId,ReservationRequest request) throws ReservationNotFoundException, RoomNotFoundException;
	
	public List<ReservationEntity> search(Long bookingId)  throws ReservationNotFoundException;

	//public ReservationEntity findRoomBooking(ReservationRequest request);
}
