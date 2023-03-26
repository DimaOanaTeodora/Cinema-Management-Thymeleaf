package com.backend.cinema.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Seat request", description = "Required details needed to create a new seat")
public class SeatRequest {

	@NotNull(message = "Seat number cannot be null")
	@Min(1)
	@ApiModelProperty(value = "number", required = true, notes = "The seat number", example = "123", position = 1)
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
