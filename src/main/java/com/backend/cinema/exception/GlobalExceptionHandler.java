package com.backend.cinema.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({ DuplicateUserException.class })
	public ResponseEntity handle(DuplicateUserException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler({ UserNotFoundException.class })
	public ResponseEntity<String> handle(UserNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage() + " at " + LocalDateTime.now());
	}

	@ExceptionHandler({ MovieNotFoundException.class })
	public ResponseEntity<String> handle(MovieNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage() + " at " + LocalDateTime.now());
	}

	@ExceptionHandler({ RoomNotFoundException.class })
	public ResponseEntity<String> handle(RoomNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage() + " at " + LocalDateTime.now());
	}

	@ExceptionHandler({ BroadcastNotFoundException.class })
	public ResponseEntity<String> handle(BroadcastNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage() + " at " + LocalDateTime.now());
	}

	@ExceptionHandler({ ScheduleNotFoundException.class })
	public ResponseEntity<String> handle(ScheduleNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage() + " at " + LocalDateTime.now());
	}
	
	@ExceptionHandler({ ReservationNotFoundException.class })
	public ResponseEntity<String> handle(ReservationNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage() + " at " + LocalDateTime.now());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handle(MethodArgumentNotValidException e) {
		return ResponseEntity.badRequest()
				.body("Invalid value : " + e.getFieldError().getRejectedValue() + " for field "
						+ e.getFieldError().getField() + " with message " + e.getFieldError().getDefaultMessage());
	}
}
