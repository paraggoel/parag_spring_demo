package com.parag.hrs.hotelbooking.controller;

import java.util.List;

import org.aspectj.apache.bcel.classfile.Module.Require;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.parag.hrs.hotelbooking.entity.ReservationEntity;
import com.parag.hrs.hotelbooking.exception.ReservationNotFoundException;
import com.parag.hrs.hotelbooking.exception.RoomNotFoundException;
import com.parag.hrs.hotelbooking.http.ReservationRequest;
import com.parag.hrs.hotelbooking.service.ReservationService;


@RestController
@RequestMapping(path = "/booking")
public class ReservationController {

	Logger logger = LoggerFactory.getLogger(ReservationController.class);
	@Autowired
	private ReservationService reservationService;

	@PostMapping(value = "/reserve",  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ReservationEntity reserve(@RequestBody ReservationRequest request) throws RoomNotFoundException {
		    logger.info("Inside create reservation method of controller");
			return reservationService.reserve(request);
	}

	@PutMapping(value = "/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
	public ReservationEntity cancel(@RequestParam(value = "bookingId", required=true) Long bookingId) throws ReservationNotFoundException {
		 logger.info("Inside cancel reservation method of controller");
		return reservationService.cancel(bookingId);
	}

	@PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ReservationEntity update(@RequestParam(value = "bookingId", required = true) Long bookingId,
			@RequestBody ReservationRequest request)
			throws RoomNotFoundException, ReservationNotFoundException, Exception {
		 logger.info("Inside update reservation method of controller");
		return reservationService.update(bookingId, request);
	}

	@GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ReservationEntity> search(@RequestParam(value = "bookingId", required = false) Long bookingId)
			throws ReservationNotFoundException {
		 logger.info("Inside search reservation method of controller");
		return reservationService.search(bookingId);
	}
}
