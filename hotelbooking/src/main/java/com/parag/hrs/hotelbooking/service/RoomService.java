package com.parag.hrs.hotelbooking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.parag.hrs.hotelbooking.entity.HotelEntity;
import com.parag.hrs.hotelbooking.entity.RoomEntity;
import com.parag.hrs.hotelbooking.http.RoomCreateRequest;

@Service
public interface RoomService {
   public List<HotelEntity> search(String city, String hotel);
   public RoomEntity save(RoomCreateRequest request);
}
