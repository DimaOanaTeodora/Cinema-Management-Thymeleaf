package com.backend.cinema.exception;

public class RoomNotFoundException extends RuntimeException {

	public RoomNotFoundException(int id) {
		super("Room with id " + id + " doesn't exist ");
	}
}
