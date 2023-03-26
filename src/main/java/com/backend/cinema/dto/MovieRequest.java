package com.backend.cinema.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.backend.cinema.model.MovieType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Movie request", description = "Required details needed to create a new movie")
public class MovieRequest {

	@NotBlank(message = "Name of the movie cannot be null")
	@ApiModelProperty(value = "name", required = true, notes = "The name of the movie", example = "Avatar 2", position = 1)
	private String name;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Type cannot be null")
	@ApiModelProperty(value = "type", required = true, notes = "The type of the movie", example = "D2", position = 2)
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
