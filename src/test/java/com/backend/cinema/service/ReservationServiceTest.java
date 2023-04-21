package com.backend.cinema.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.cinema.domain.Broadcast;
import com.backend.cinema.domain.Reservation;
import com.backend.cinema.domain.Room;
import com.backend.cinema.domain.Seat;
import com.backend.cinema.domain.security.User;
import com.backend.cinema.exception.ReservationNotFoundException;
import com.backend.cinema.repository.ReservationRepository;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

	@InjectMocks
	private ReservationService reservationService;

	@Mock
	private ReservationRepository reservationRepository;

	@Test
	void whenReservationDoesntExist_create() throws ParseException {
		User user = new User(1, "oanadima26@gmail.com", "1234");
		Broadcast broadcast = new Broadcast();
		broadcast.setId(1);
		Room room = new Room();
		room.setName("1A");
		room.setCapacity(2);
		Seat seat1 = new Seat();
		seat1.setNumber(1);
		seat1.setRoom(room);

		Seat seat2 = new Seat();
		seat2.setNumber(2);
		seat2.setRoom(room);

		List<Seat> seats = new ArrayList<Seat>();
		seats.add(seat1);
		seats.add(seat2);

		
		Reservation reservation = new Reservation();
		reservation.setDateRegistered(new SimpleDateFormat("dd-MM-yyyy").parse("05-07-2023"));
		reservation.setNoPersons(2);
		reservation.setBroadcast(broadcast);
		reservation.setReservedSeats(seats);
		reservation.setUser(user);

		Reservation savedReservation = new Reservation();
		savedReservation.setDateRegistered(new SimpleDateFormat("dd-MM-yyyy").parse("05-07-2023"));
		savedReservation.setNoPersons(2);
		savedReservation.setUser(user);
		savedReservation.setBroadcast(broadcast);
		savedReservation.setReservedSeats(seats);
		savedReservation.setId(1);

		when(reservationRepository.save(reservation)).thenReturn(savedReservation);

		// Act
		Reservation result = reservationService.createReservation(reservation, user, broadcast, seats);

		// Assert
		assertNotNull(result);
		assertEquals(savedReservation.getId(), result.getId());
		assertEquals(savedReservation.getNoPersons(), result.getNoPersons());
		assertEquals(reservation.getNoPersons(), result.getNoPersons());


		verify(reservationRepository).save(reservation);
	}

	@Test
	void whenReservationDoesntExists_getReservation_throwsReservationNotFoundException() {

		// Act
		ReservationNotFoundException exception = assertThrows(ReservationNotFoundException.class,
				() -> reservationService.getReservation(1));

		// Assert
		assertEquals("Reservation with id 1 doesn't exist ", exception.getMessage());

	}

	@Test
	void whenReservationExists_getReservation_returnsTheReservation() throws ParseException {
		// Arrange
		User user = new User(1, "oanadima26@gmail.com", "1234");
		Broadcast broadcast = new Broadcast();
		broadcast.setId(1);
		Room room = new Room();
		room.setName("1A");
		room.setCapacity(2);
		Seat seat1 = new Seat();
		seat1.setNumber(1);
		seat1.setRoom(room);

		Seat seat2 = new Seat();
		seat2.setNumber(2);
		seat2.setRoom(room);

		List<Seat> seats = new ArrayList<Seat>();
		seats.add(seat1);
		seats.add(seat2);

		
		Reservation reservation = new Reservation();
		reservation.setDateRegistered(new SimpleDateFormat("dd-MM-yyyy").parse("05-07-2023"));
		reservation.setNoPersons(2);
		reservation.setBroadcast(broadcast);
		reservation.setReservedSeats(seats);
		reservation.setUser(user);
		reservation.setId(1);
		when(reservationRepository.findById(1)).thenReturn(Optional.of(reservation));

		// Act
		Reservation result = reservationService.getReservation(1);

		// Assert
		assertNotNull(result);
		assertEquals(reservation.getId(), result.getId());
	}

	@Test
	public void delete_reservation() throws ParseException {
		User user = new User(1, "oanadima26@gmail.com", "1234");
		Broadcast broadcast = new Broadcast();
		broadcast.setId(1);
		Room room = new Room();
		room.setName("1A");
		room.setCapacity(2);
		Seat seat1 = new Seat();
		seat1.setNumber(1);
		seat1.setRoom(room);

		Seat seat2 = new Seat();
		seat2.setNumber(2);
		seat2.setRoom(room);

		List<Seat> seats = new ArrayList<Seat>();
		seats.add(seat1);
		seats.add(seat2);

		
		Reservation reservation = new Reservation();
		reservation.setDateRegistered(new SimpleDateFormat("dd-MM-yyyy").parse("05-07-2023"));
		reservation.setNoPersons(2);
		reservation.setBroadcast(broadcast);
		reservation.setReservedSeats(seats);
		reservation.setUser(user);
		reservation.setId(1);
		when(reservationRepository.findById(1)).thenReturn(Optional.of(reservation));

		reservationService.deleteReservation(1);

		when(reservationRepository.findById(1)).thenReturn(Optional.empty());
		// Act
		ReservationNotFoundException exception = assertThrows(ReservationNotFoundException.class,
				() -> reservationService.getReservation(1));

		// Assert
		assertEquals("Reservation with id 1 doesn't exist ", exception.getMessage());

	}

}
