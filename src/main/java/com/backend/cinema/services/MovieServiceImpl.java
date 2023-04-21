package com.backend.cinema.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.cinema.domain.Movie;
import com.backend.cinema.exceptions.ResourceNotFoundException;
import com.backend.cinema.repositories.MovieRepository;


@Service
public class MovieServiceImpl implements MovieService{

	private MovieRepository movieRepository;

	public MovieServiceImpl(MovieRepository movieRepository) {
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
			throw new ResourceNotFoundException(" ResourceNotFoundException ");
		}
	}

	public List<Movie> getAllMovies() {
		return movieRepository.findAll();
	}
}
