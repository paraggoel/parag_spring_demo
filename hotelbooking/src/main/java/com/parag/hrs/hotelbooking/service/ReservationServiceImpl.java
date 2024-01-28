package com.parag.hrs.hotelbooking.service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parag.hrs.hotelbooking.MessageConstants;
import com.parag.hrs.hotelbooking.controller.ReservationController;
import com.parag.hrs.hotelbooking.entity.ReservationEntity;
import com.parag.hrs.hotelbooking.entity.RoomEntity;
import com.parag.hrs.hotelbooking.enums.BookingStatus;
import com.parag.hrs.hotelbooking.exception.ReservationNotFoundException;
import com.parag.hrs.hotelbooking.exception.RoomNotFoundException;
import com.parag.hrs.hotelbooking.http.ReservationRequest;
import com.parag.hrs.hotelbooking.repository.ReservationRepository;
import com.parag.hrs.hotelbooking.repository.RoomRepository;

@Service
public class ReservationServiceImpl implements ReservationService {

	Logger logger = LoggerFactory.getLogger(ReservationController.class);
	
	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private RoomRepository repository;

	@Override
	@Transactional
	public ReservationEntity reserve(ReservationRequest request) throws RoomNotFoundException {
		return reservationRepository.save(requestToEntityMapper(request,null));
	}

	@Transactional
	@Override
	public ReservationEntity cancel(Long bookingId) throws ReservationNotFoundException {
		ReservationEntity resernationEntity = null;
		Optional<ReservationEntity> reservationOpt = reservationRepository.findById(bookingId);
		if (reservationOpt.isPresent()) {
			resernationEntity = reservationOpt.get();
			resernationEntity.setStatus(BookingStatus.CANCELED.toString());
			resernationEntity = reservationRepository.save(resernationEntity);
		} else {
			logger.info("Reservation not found by this id {0}", bookingId);
			throw new ReservationNotFoundException(MessageConstants.INVALID_RESERVATION_ID);
		}

		return resernationEntity;
	}

	@Transactional
	@Override
	public ReservationEntity update(Long bookingId, ReservationRequest request)
			throws ReservationNotFoundException, RoomNotFoundException {
		if (reservationRepository.existsById(bookingId)) {
			return reservationRepository.save(requestToEntityMapper(request,bookingId));
		} else {
			throw new ReservationNotFoundException(MessageConstants.INVALID_RESERVATION_ID);
		}
	}

	@Override
	public List<ReservationEntity> search(Long bookingId) throws ReservationNotFoundException {
		List<ReservationEntity> entities = null;
		Optional<ReservationEntity> reservationOpt = null;
		if (bookingId == null) {
			entities = reservationRepository.findAll();
		} else {
			reservationOpt = reservationRepository.findById(bookingId);
			if (reservationOpt.isPresent()) {
				entities = new ArrayList<ReservationEntity>();
				entities.add(reservationOpt.get());
			} else {
				logger.info("Reservation not found by this id {0}", bookingId);
				throw new ReservationNotFoundException(MessageConstants.INVALID_RESERVATION_ID);
			}

		}
		return entities;
	}

	/**
	 * 
	 * @param request
	 * @return Boolean - Room available or not
	 */
	public Boolean isRoomAvailable(ReservationRequest request, Long reservationId) {
		Boolean isavailable = Boolean.FALSE;
		List<ReservationEntity> entities = reservationRepository.findRoomBooking(request.getRoomId(),
				request.getCheckInDate(), request.getCheckOutDate());
		if (entities.isEmpty()) {
			isavailable = Boolean.TRUE;
		} else {
			if(reservationId != null) {
				entities = entities.stream().filter(e -> e.getId() != reservationId).collect(Collectors.toList());
			}
			
			if (entities.size() > 1) {
				isavailable = Boolean.FALSE;
			}
		}

		return isavailable;

	}

	private ReservationEntity requestToEntityMapper(ReservationRequest request, Long reservationId) {
		ReservationEntity reservationEntity = null;
		Optional<RoomEntity> roomOpt = repository.findById(request.getRoomId());
		if (roomOpt.isPresent()) {
			if (isRoomAvailable(request, reservationId)) {
				if(reservationId != null) {
					reservationEntity = reservationRepository.getReferenceById(reservationId);
				}else {
					reservationEntity = new ReservationEntity();
				}
				
				reservationEntity.setInDate(request.getCheckInDate());
				reservationEntity.setOutDate(request.getCheckOutDate());
				reservationEntity.setRoomId(request.getRoomId());
				reservationEntity.setStatus(BookingStatus.CONFIRMED.toString());

				long daysBetween = ChronoUnit.DAYS.between(request.getCheckInDate(), request.getCheckOutDate());
				reservationEntity.setAmount(roomOpt.get().getPrice() * daysBetween);
			} else {
				throw new RoomNotFoundException(MessageConstants.ROOM_UNAVAILABLE);
			}
		} else {
			throw new RoomNotFoundException(MessageConstants.INVALID_ROOM_ID);
		}
		return reservationEntity;
	}
}
