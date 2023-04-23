package com.backend.cinema.repositories;

import com.backend.cinema.domain.Movie;
import com.backend.cinema.domain.Movie;
import com.backend.cinema.domain.MovieType;
import com.backend.cinema.domain.Movie;
import com.backend.cinema.domain.Reservation;
import com.backend.cinema.domain.Room;
import com.backend.cinema.domain.Schedule;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("mysql")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
@Slf4j
public class MovieRepositoryTest {

	@Autowired
	private MovieRepository movieRepository;

	@Test
	@Order(1)
	public void addMovie() {
	
		Movie movie = new Movie();
		movie.setName("Avatar");
		movie.setType(MovieType.D2);

		movieRepository.save(movie);
	}

	@Test
	@Order(2)
	public void findById() {
		Optional<Movie> movie = movieRepository.findById(1);
		assertFalse(movie.isEmpty());
		log.info("findById ...");
		log.info(movie.get().getName());
	}

}
