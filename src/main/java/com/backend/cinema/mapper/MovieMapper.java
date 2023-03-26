package com.backend.cinema.mapper;

import org.springframework.stereotype.Component;

import com.backend.cinema.domain.Movie;
import com.backend.cinema.dto.MovieRequest;

@Component
public class MovieMapper {

	public Movie movieRequestToMovie(MovieRequest movieRequest) {

		return new Movie(movieRequest.getName(), movieRequest.getType());
	}
}
