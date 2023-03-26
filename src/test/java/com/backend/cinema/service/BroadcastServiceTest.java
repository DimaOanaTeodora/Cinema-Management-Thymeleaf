package com.backend.cinema.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.cinema.domain.Broadcast;
import com.backend.cinema.domain.Movie;
import com.backend.cinema.domain.MovieType;
import com.backend.cinema.domain.Room;
import com.backend.cinema.domain.Schedule;
import com.backend.cinema.exception.BroadcastNotFoundException;
import com.backend.cinema.repository.BroadcastRepository;

@ExtendWith(MockitoExtension.class)
public class BroadcastServiceTest {

	@InjectMocks
	private BroadcastService broadcastService;

	@Mock
	private BroadcastRepository broadcastRepository;

	@Test
	void whenBroadcastDoesntExist_create() throws ParseException {
		Movie movie = new Movie();
		movie.setName("1A");
		movie.setType(MovieType.D2);

		Room room = new Room();
		room.setName("1A");
		room.setCapacity(2);

		Schedule schedule = new Schedule();
		schedule.setDate(new SimpleDateFormat("dd-MM-yyyy").parse("05-07-2023"));
		schedule.setEndingHour(LocalTime.of(12, 12));
		schedule.setStartingHour(LocalTime.of(10, 10));

		Broadcast broadcast = new Broadcast();
		broadcast.setMovie(movie);
		broadcast.setRoom(room);
		broadcast.setSchedule(schedule);

		/// ----------------------------------------------
		Movie savedMovie = new Movie();
		savedMovie.setName("1A");
		savedMovie.setType(MovieType.D2);
		savedMovie.setId(1);

		Room savedRoom = new Room();
		savedRoom.setName("1A");
		savedRoom.setCapacity(2);
		savedRoom.setId(1);

		Schedule savedSchedule = new Schedule();
		savedSchedule.setDate(new SimpleDateFormat("dd-MM-yyyy").parse("05-07-2023"));
		savedSchedule.setEndingHour(LocalTime.of(12, 12));
		savedSchedule.setStartingHour(LocalTime.of(10, 10));
		savedSchedule.setId(1);

		Broadcast savedBroadcast = new Broadcast();
		savedBroadcast.setMovie(savedMovie);
		savedBroadcast.setRoom(savedRoom);
		savedBroadcast.setSchedule(savedSchedule);
		savedBroadcast.setId(1);

		when(broadcastRepository.save(broadcast)).thenReturn(savedBroadcast);

		// Act
		Broadcast result = broadcastService.createBroadcast(broadcast, room, movie);

		// Assert
		assertNotNull(result);
		assertEquals(savedBroadcast.getId(), result.getId());
		assertEquals(savedBroadcast.getMovie().getId(), result.getMovie().getId());

		assertEquals(savedBroadcast.getSchedule().getId(), result.getSchedule().getId());

		assertEquals(savedBroadcast.getRoom().getId(), result.getRoom().getId());

		verify(broadcastRepository).save(broadcast);
	}

	@Test
	void whenBroadcastDoesntExists_getBroadcast_throwsBroadcastNotFoundException() {

		// Act
		BroadcastNotFoundException exception = assertThrows(BroadcastNotFoundException.class,
				() -> broadcastService.getBroadcast(1));

		// Assert
		assertEquals("Broadcast with id 1 doesn't exist ", exception.getMessage());

	}

	@Test
	void whenBroadcastExists_getBroadcast_returnsTheBroadcast() throws ParseException {
		// Arrange
		Movie movie = new Movie();
		movie.setName("1A");
		movie.setType(MovieType.D2);

		Room room = new Room();
		room.setName("1A");
		room.setCapacity(2);

		Schedule schedule = new Schedule();
		schedule.setDate(new SimpleDateFormat("dd-MM-yyyy").parse("05-07-2023"));
		schedule.setEndingHour(LocalTime.of(12, 12));
		schedule.setStartingHour(LocalTime.of(10, 10));

		Broadcast broadcast = new Broadcast();
		broadcast.setMovie(movie);
		broadcast.setRoom(room);
		broadcast.setSchedule(schedule);

		when(broadcastRepository.findById(1)).thenReturn(Optional.of(broadcast));

		// Act
		Broadcast result = broadcastService.getBroadcast(1);

		// Assert
		assertNotNull(result);
		assertEquals(broadcast.getId(), result.getId());
	}

	@Test
	public void delete_broadcast() throws ParseException {
		Movie movie = new Movie();
		movie.setName("1A");
		movie.setType(MovieType.D2);

		Room room = new Room();
		room.setName("1A");
		room.setCapacity(2);

		Schedule schedule = new Schedule();
		schedule.setDate(new SimpleDateFormat("dd-MM-yyyy").parse("05-07-2023"));
		schedule.setEndingHour(LocalTime.of(12, 12));
		schedule.setStartingHour(LocalTime.of(10, 10));

		Broadcast broadcast = new Broadcast();
		broadcast.setMovie(movie);
		broadcast.setRoom(room);
		broadcast.setSchedule(schedule);

		when(broadcastRepository.findById(1)).thenReturn(Optional.of(broadcast));

		broadcastService.deleteBroadcast(1);

		when(broadcastRepository.findById(1)).thenReturn(Optional.empty());
		// Act
		BroadcastNotFoundException exception = assertThrows(BroadcastNotFoundException.class,
				() -> broadcastService.getBroadcast(1));

		// Assert
		assertEquals("Broadcast with id 1 doesn't exist ", exception.getMessage());

	}
	
	@Test
	void updateSchedule_returnsNewTheSchedule() throws ParseException {
		// Arrange
		Room oldRoom = new Room();
		oldRoom.setName("1A");
		oldRoom.setCapacity(2);
		
		Broadcast oldBroadcast = new Broadcast();
		oldBroadcast.setRoom(oldRoom);
		oldBroadcast.setId(1);
		
		//------------------------------------
		
		Room newRoom = new Room();
		newRoom.setName("2A");
		newRoom.setCapacity(3);

		Broadcast newBroadcast = new Broadcast();
		newBroadcast.setRoom(newRoom);
		newBroadcast.setId(1);

		when(broadcastRepository.save(oldBroadcast)).thenReturn(newBroadcast);

		// Act
		Broadcast result = broadcastService.updateBroadcastRoom(oldBroadcast, newRoom);

		// Assert
		assertNotNull(result);
		assertEquals(oldBroadcast.getId(), result.getId());

		verify(broadcastRepository).save(oldBroadcast);

	}
}
