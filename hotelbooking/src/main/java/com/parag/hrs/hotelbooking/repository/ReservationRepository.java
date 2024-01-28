package com.parag.hrs.hotelbooking.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.parag.hrs.hotelbooking.entity.ReservationEntity;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

	/*
	 * @Query(value =
	 * "SELECT * FROM rerservation r where r.room_id=:roomId and r.status like 'CONFIRMED' "
	 * + "and :checkInDate BETWEEN in_date AND out_date " , nativeQuery = true)
	 */
	
	@Query(value = "SELECT * from rerservation r where r.room_id=:roomId and r.status like 'CONFIRMED' AND "
			+ "( (:checkInDate BETWEEN in_date AND out_date ) OR (:checkOutDate  BETWEEN in_date AND out_date ))", nativeQuery = true)
	List<ReservationEntity> findRoomBooking(Long roomId, LocalDate checkInDate, LocalDate checkOutDate) ;

}
