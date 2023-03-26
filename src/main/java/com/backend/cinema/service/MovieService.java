package com.backend.cinema.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.cinema.domain.Movie;
import com.backend.cinema.exception.MovieNotFoundException;
import com.backend.cinema.repository.MovieRepository;

public interface MovieService {

	public Movie createMovie(Movie movie);

	public Movie getMovie(Integer id);

	public List<Movie> getAllMovies();
}
