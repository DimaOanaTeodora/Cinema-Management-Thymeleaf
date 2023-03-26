package com.backend.cinema.exception;

public class MovieNotFoundException extends RuntimeException {

	public MovieNotFoundException(int id) {
		super("Movie with id " + id + " doesn't exist ");
	}
}
