package com.backend.cinema.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class SeatRequest {

	@NotNull(message = "Seat number cannot be null")
	@Min(1)
	private int number;

	public SeatRequest() {
	}

	public SeatRequest(int number) {
		this.number = number;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

}
