package com.backend.cinema.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.backend.cinema.domain.MovieType;

public class MovieRequest {

	@NotBlank(message = "Name of the movie cannot be null")
	private String name;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Type cannot be null")
	private MovieType type;

	public MovieRequest() {
	}

	public MovieRequest(@NotBlank(message = "Name of the movie cannot be null") String name,
			@NotNull(message = "type cannot be null") MovieType type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MovieType getType() {
		return type;
	}

	public void setType(MovieType type) {
		this.type = type;
	}

}
