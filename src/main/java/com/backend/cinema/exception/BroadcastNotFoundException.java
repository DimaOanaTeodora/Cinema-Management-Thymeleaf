package com.backend.cinema.exception;

public class BroadcastNotFoundException extends RuntimeException {
	
	public BroadcastNotFoundException(int id) {
		super("Broadcast with id " + id + " doesn't exist ");
	}
}
