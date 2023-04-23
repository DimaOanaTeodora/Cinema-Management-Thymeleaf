package com.backend.cinema.services;

import com.backend.cinema.domain.Broadcast;
import com.backend.cinema.domain.Movie;
import com.backend.cinema.domain.MovieType;
import com.backend.cinema.domain.Room;
import com.backend.cinema.domain.Schedule;
import com.backend.cinema.repositories.BroadcastRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BroadcastServiceTest {
	@Mock
	BroadcastRepository broadcastRepository;

	@InjectMocks
	BroadcastServiceImpl broadcastService;

	@Test
	public void create() throws java.text.ParseException {
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
		Broadcast result = broadcastService.create(broadcast);

		// Assert
		assertNotNull(result);
		assertEquals(savedBroadcast.getId(), result.getId());
		assertEquals(savedBroadcast.getMovie().getId(), result.getMovie().getId());

		assertEquals(savedBroadcast.getSchedule().getId(), result.getSchedule().getId());

		assertEquals(savedBroadcast.getRoom().getId(), result.getRoom().getId());

		verify(broadcastRepository).save(broadcast);
	}

}
