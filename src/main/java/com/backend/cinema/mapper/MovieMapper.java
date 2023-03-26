package com.backend.cinema.mapper;

import org.springframework.stereotype.Component;

import com.backend.cinema.dto.MovieRequest;
import com.backend.cinema.model.Movie;

@Component
public class MovieMapper {

	public Movie movieRequestToMovie(MovieRequest movieRequest) {

		return new Movie(movieRequest.getName(), movieRequest.getType());
	}
}
