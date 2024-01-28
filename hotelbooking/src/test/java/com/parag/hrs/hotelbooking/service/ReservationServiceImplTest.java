package com.parag.hrs.hotelbooking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parag.hrs.hotelbooking.entity.ReservationEntity;
import com.parag.hrs.hotelbooking.entity.RoomEntity;
import com.parag.hrs.hotelbooking.enums.BookingStatus;
import com.parag.hrs.hotelbooking.exception.ReservationNotFoundException;
import com.parag.hrs.hotelbooking.exception.RoomNotFoundException;
import com.parag.hrs.hotelbooking.http.ReservationRequest;
import com.parag.hrs.hotelbooking.repository.ReservationRepository;
import com.parag.hrs.hotelbooking.repository.RoomRepository;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceImplTest {

	
	@Mock
	private RoomRepository roomRepository;

	@Mock
	private ReservationRepository reservationRepository;

	@InjectMocks
	ReservationService service = new ReservationServiceImpl();

	@Test
	public void testReserve() throws RoomNotFoundException {
		ReservationRequest request = new ReservationRequest(LocalDate.of(2024, 8, 8), LocalDate.of(2024, 8, 12), 1L);
		java.util.Optional<RoomEntity> opt = java.util.Optional.of(createRoom());
		when(roomRepository.findById(request.getRoomId())).thenReturn(opt);

		ReservationEntity entity1 = new ReservationEntity();
		entity1.setStatus(BookingStatus.CONFIRMED.toString());
		entity1.setRoomId(1L);
		entity1.setInDate(request.getCheckInDate());
		entity1.setOutDate(request.getCheckOutDate());
		entity1.setAmount(1000.00);
		when(reservationRepository.save(Mockito.any(ReservationEntity.class))).thenAnswer(i -> i.getArguments()[0]);
		assertNotNull(service.reserve(request));
	}

	@Test
	public void testUpdateForException() {
		when(reservationRepository.existsById(11L)).thenReturn(Boolean.FALSE);
		
		Throwable e = null;
		try {
			service.update(11L,new ReservationRequest(LocalDate.of(2024, 01,2), LocalDate.of(2024, 01,8), 1L));
		} catch (Throwable ex) {
			e = ex;
		}
		assertTrue(e instanceof ReservationNotFoundException);

	}

	private RoomEntity createRoom() {
		RoomEntity entity = new RoomEntity();
		entity.setId(1L);
		entity.setPrice(100.00);
		entity.setRoomType("Standard");
		return entity;
	}

	@Test
	public void testCancel() throws ReservationNotFoundException {
		Optional<ReservationEntity> opt = Optional.of(getReservationList().get(0));
		when(reservationRepository.findById(1L)).thenReturn(opt);
		assertTrue(opt.isPresent());
		ReservationEntity entity = opt.get();
		entity.setStatus(BookingStatus.CANCELED.toString());
		when(reservationRepository.save(Mockito.any(ReservationEntity.class))).thenAnswer(i -> i.getArguments()[0]);
		assertNotNull(service.cancel(1L));
		assertEquals(service.cancel(1L).getStatus(), BookingStatus.CANCELED.toString());
	}

	@Test
	public void testCancelForException() {
		Optional<ReservationEntity> opt = Optional.empty();
		when(reservationRepository.findById(11L)).thenReturn(opt);
		assertFalse(opt.isPresent());
		Throwable e = null;

		try {
			service.cancel(11L);
		} catch (Throwable ex) {
			e = ex;
		}
		assertTrue(e instanceof ReservationNotFoundException);

	}

	@Test
	public void testUpdate() throws ReservationNotFoundException {
		ReservationRequest request = new ReservationRequest(LocalDate.of(2024, 1, 8), LocalDate.of(2024, 1, 18), 1L);
		when(reservationRepository.existsById(1L)).thenReturn(Boolean.TRUE);
		when(roomRepository.findById(1L)).thenReturn(Optional.of(createRoom()));
		assertTrue(reservationRepository.existsById(1L));
		when(reservationRepository.getReferenceById(1L)).thenReturn(getReservationList().get(0));
		when(reservationRepository.save(Mockito.any(ReservationEntity.class))).thenAnswer(i -> i.getArguments()[0]);
		assertNotNull(service.update(1L, request));
	}
	
	@Test
	public void testReserveForException() {
		Optional<RoomEntity> opt = Optional.empty();
		when(roomRepository.findById(11L)).thenReturn(opt);
		assertFalse(opt.isPresent());
		Throwable e = null;
		try {
			service.reserve(new ReservationRequest(LocalDate.of(2024, 01,2), LocalDate.of(2024, 01,8), 11L));
		} catch (Throwable ex) {
			e = ex;
		}
		assertTrue(e instanceof RoomNotFoundException);

	}

	@Test
	public void testSearchWhenBookingIdNull() throws ReservationNotFoundException {
		Mockito.when(reservationRepository.findAll()).thenReturn(getReservationList());
		assertNotNull(service.search(null));
		assertEquals(2, service.search(null).size());

	}

	@Test
	public void testSearchWhenBookingIdNotNull() throws ReservationNotFoundException {
		List<ReservationEntity> entities = getReservationList();
		Optional<ReservationEntity> optional = entities.stream().filter(r -> r.getId() == 1L).findFirst();
		Mockito.when(reservationRepository.findById(1L)).thenReturn(optional);
		assertTrue(optional.isPresent());
		assertEquals(1, service.search(1L).size());
	}

	@Test
	public void testSearchForException() {
		Optional<ReservationEntity> opt = Optional.empty();
		when(reservationRepository.findById(11L)).thenReturn(opt);
		assertFalse(opt.isPresent());
		Throwable e = null;
		try {
			service.search(11L);
		} catch (Throwable ex) {
			e = ex;
		}
		assertTrue(e instanceof ReservationNotFoundException);

	}

	//@Test
	public void testIsRoomAvailableForRoomAvailableTrue() {
		List<ReservationEntity> entities = Collections.emptyList();
		ReservationRequest request = new ReservationRequest(LocalDate.of(2024, 8, 8), LocalDate.of(2024, 8, 20), 1L);
		when(reservationRepository.findRoomBooking(request.getRoomId(),request.getCheckInDate(), request.getCheckOutDate())).thenReturn(entities);
		assertTrue(entities.isEmpty());
		
	}

	private List<ReservationEntity> getReservationList() {
		List<ReservationEntity> reservationEntities = new ArrayList<ReservationEntity>();
		ReservationEntity entity1 = new ReservationEntity();
		entity1.setStatus(BookingStatus.CONFIRMED.toString());
		entity1.setRoomId(1L);
		entity1.setInDate(LocalDate.of(2024, 1, 8));
		entity1.setOutDate(LocalDate.of(2024, 1, 18));
		entity1.setAmount(1000.00);
		entity1.setId(1L);

		ReservationEntity entity2 = new ReservationEntity();
		entity2.setStatus(BookingStatus.CONFIRMED.toString());
		entity2.setRoomId(1L);
		entity2.setInDate(LocalDate.of(2024, 1, 18));
		entity2.setOutDate(LocalDate.of(2024, 1, 28));
		entity2.setAmount(1000.00);
		entity2.setId(2L);

		reservationEntities.add(entity2);
		reservationEntities.add(entity1);

		return reservationEntities;

	}
	
	@Test
	public void testRequestToEntityMapperForReservationIdNotNull() {
		Long reservationId = 1L;
		ReservationRequest request = new ReservationRequest(LocalDate.of(2024, 1, 9), LocalDate.of(2024, 1, 20), 1L);
		Optional<RoomEntity> opt = Optional.of(createRoom());
		//when(roomRepository.findById(request.getRoomId())).thenReturn(opt);
		assertTrue(opt.isPresent());
		assertNotNull(reservationId);
	}
	
}
