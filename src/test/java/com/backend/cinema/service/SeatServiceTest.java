package com.backend.cinema.service;

//import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.quality.Strictness;

import com.backend.cinema.model.Room;
import com.backend.cinema.model.Seat;
import com.backend.cinema.model.Seat;
import com.backend.cinema.repository.SeatRepository;
import com.backend.cinema.repository.SeatRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SeatServiceTest {

	@InjectMocks
	private SeatService seatService;

	@Mock
	private SeatRepository seatRepository;

	@Test
	void seat_create() {
		Room room = new Room();
		room.setName("1A");
		room.setCapacity(2);

		Seat seat = new Seat();
		seat.setNumber(1);
		seat.setRoom(room);

		Seat savedSeat = new Seat();
		savedSeat.setNumber(1);
		savedSeat.setRoom(room);
		savedSeat.setId(1);
		when(seatRepository.save(seat)).thenReturn(savedSeat);

		// Act
		Seat result = seatService.createSeat(seat);

		// Assert
		assertNotNull(result);
		assertEquals(savedSeat.getId(), result.getId());
		assertEquals(savedSeat.getNumber(), result.getNumber());
		assertEquals(seat.getNumber(), result.getNumber());

		assertEquals(savedSeat.getRoom(), result.getRoom());
		assertEquals(seat.getRoom(), result.getRoom());

		verify(seatRepository).save(seat);
	}

	@Test
	void listSeat_create_for_room() {

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

		Seat savedSeat1 = new Seat();
		savedSeat1.setNumber(1);
		savedSeat1.setRoom(room);
		savedSeat1.setId(1);

		Seat savedSeat2 = new Seat();
		savedSeat2.setNumber(2);
		savedSeat2.setRoom(room);
		savedSeat2.setId(2);

		List<Seat> savedSeats = new ArrayList<Seat>();
		savedSeats.add(savedSeat1);
		savedSeats.add(savedSeat2);

		lenient().when(seatRepository.save(seat1)).thenReturn(savedSeat1);

		// Act
		List<Seat> result = seatService.createSeats(room);

		assertEquals(result.size(), 2);

	}

	@Test
	void whenSeatExists_getSeat_returnsTheSeat() {
		// Arrange
		Seat seat = new Seat();
		seat.setId(1);
		when(seatRepository.findById(1)).thenReturn(Optional.of(seat));

		// Act
		Optional<Seat> result = seatService.getSeat(1);

		// Assert
		assertNotNull(result);
		assertTrue(result.isPresent());
		assertEquals(seat.getId(), result.get().getId());
	}

}
