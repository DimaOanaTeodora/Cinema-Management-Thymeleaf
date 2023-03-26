package com.backend.cinema.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.cinema.exception.MovieNotFoundException;
import com.backend.cinema.model.Movie;
import com.backend.cinema.model.MovieType;
import com.backend.cinema.repository.MovieRepository;


@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

	@InjectMocks
	private MovieService movieService;

	@Mock
	private MovieRepository movieRepository;
	
	@Test
    void movie_create() {
		Movie movie = new Movie();
        movie.setName("1A");
        movie.setType(MovieType.D2);
       
        
        Movie savedMovie = new Movie();
        savedMovie.setName("1A");
        savedMovie.setType(MovieType.D2);
        savedMovie.setId(1);
        when(movieRepository.save(movie)).thenReturn(savedMovie);

        // Act
        Movie result = movieService.createMovie(movie);

        // Assert
        assertNotNull(result);
        assertEquals(savedMovie.getId(), result.getId());
        assertEquals(savedMovie.getName(), result.getName());
        assertEquals(movie.getName(), result.getName());
        
        assertEquals(savedMovie.getType(), result.getType());
        assertEquals(movie.getType(), result.getType());
        
        verify(movieRepository).save(movie);
    }

	@Test
	void whenMovieDoesntExists_getMovie_throwsMovieNotFoundException() {

		// Act
		MovieNotFoundException exception = assertThrows(MovieNotFoundException.class, () -> movieService.getMovie(1));

		// Assert
		assertEquals("Movie with id 1 doesn't exist ", exception.getMessage());

	}

	@Test
	void whenMovieExists_getMovie_returnsTheMovie() {
		// Arrange
		Movie movie = new Movie();
		movie.setId(1);
		when(movieRepository.findById(1)).thenReturn(Optional.of(movie));

		// Act
		Movie result = movieService.getMovie(1);

		// Assert
		assertNotNull(result);
		assertEquals(movie.getId(), result.getId());
	}
}
