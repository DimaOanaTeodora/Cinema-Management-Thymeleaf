package com.backend.cinema.exception;

public class ReservationNotFoundException extends RuntimeException {

	public ReservationNotFoundException(int id) {
		super("Reservation with id " + id + " doesn't exist ");
	}
}
