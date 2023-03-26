package com.backend.cinema.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.cinema.exception.MovieNotFoundException;
import com.backend.cinema.model.Movie;
import com.backend.cinema.repository.MovieRepository;

@Service
public class MovieService {

	private MovieRepository movieRepository;

	public MovieService(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	public Movie createMovie(Movie movie) {
		return movieRepository.save(movie);
	}

	public Movie getMovie(Integer id) {
		Optional<Movie> movieOptional = movieRepository.findById(id);
		if (movieOptional.isPresent()) {
			return movieOptional.get();
		} else {
			throw new MovieNotFoundException(id);
		}
	}

	public List<Movie> getAllMovies() {
		return movieRepository.findAll();
	}
}
