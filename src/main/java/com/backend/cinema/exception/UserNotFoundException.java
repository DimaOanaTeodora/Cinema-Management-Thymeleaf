package com.backend.cinema.exception;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(int id) {
		super("User with id " + id + " doesn't exist ");
	}
}
