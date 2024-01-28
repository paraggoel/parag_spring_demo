package com.parag.hrs.hotelbooking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parag.hrs.hotelbooking.entity.HotelEntity;
import com.parag.hrs.hotelbooking.entity.RoomEntity;
import com.parag.hrs.hotelbooking.http.RoomCreateRequest;
import com.parag.hrs.hotelbooking.repository.HotelRepository;
import com.parag.hrs.hotelbooking.repository.RoomRepository;

import jakarta.transaction.Transactional;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private HotelRepository hotelRepository;

	@Override
	public List<HotelEntity> search(String city, String hotel) {
		Stream<HotelEntity> entities = hotelRepository.findAll().stream();
		if (city != null && !city.isBlank()) {
			entities = entities.filter(r -> city.equals(r.getAddress()));
		} else if (hotel != null && !hotel.isBlank()) {
			entities = entities.filter(h -> hotel.equals(h.getName()));
		}
		return entities.collect(Collectors.toList());

	}

	@Transactional
	public RoomEntity save(RoomCreateRequest request) {
		HotelEntity hotel = null;
		List<RoomEntity> roomList = new ArrayList<>();
		Optional<HotelEntity> hotelOpt = hotelRepository.findAll().stream()
				.filter(h -> h.getAddress().equals(request.getCity()) && h.getName().equals(request.getHotel()))
				.findAny();
		if (hotelOpt.isEmpty()) {
			hotel = new HotelEntity();
			hotel.setAddress(request.getCity());
			hotel.setName(request.getHotel());
			hotelRepository.save(hotel);
		} else {
			hotel = hotelOpt.get();
		}

		RoomEntity room = createRoom(request, hotel);
		roomList.add(room);
		hotel.setRooms(roomList);
		hotel = hotelRepository.save(hotel);
		return room;
	}

	private RoomEntity createRoom(RoomCreateRequest request, HotelEntity hotel) {
		RoomEntity room = new RoomEntity();
		room.setPrice(request.getRoomPrice());
		room.setRoomType(request.getRoomType());
		room.setHotel(hotel);

		return roomRepository.save(room);
	}
}
