package com.parag.hrs.hotelbooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.parag.hrs.hotelbooking.entity.HotelEntity;
import com.parag.hrs.hotelbooking.entity.RoomEntity;
import com.parag.hrs.hotelbooking.http.RoomCreateRequest;
import com.parag.hrs.hotelbooking.service.RoomService;

@RestController
@RequestMapping(value="/rooms")
public class RoomController {

	@Autowired
	private RoomService roomService;
	
	@PostMapping(value="/save", consumes = "application/json", produces="application/json")
	public RoomEntity save(@RequestBody RoomCreateRequest request) {
		return roomService.save(request);
	}
	
	@GetMapping(value = "/search",produces = "application/json")
	public ResponseEntity<?> search(@RequestParam(value="city", required = false)String city,
			@RequestParam(value="hotel", required = false)String hotel){
		List<HotelEntity> hotels = roomService.search(city, hotel);
		return ResponseEntity.ok(hotels);
	}
}
