package com.backend.cinema.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.cinema.domain.Movie;

public interface MovieService {

	public Movie createMovie(Movie movie);

	public Movie getMovie(Integer id);

	public List<Movie> getAllMovies();
}
