package com.backend.cinema.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
public class RoomRequest {

	@NotBlank(message = "Name cannot be null")
	private String name;

	@NotNull(message = "Capacity cannot be null")
	@Min(1)
	private int capacity;

	public RoomRequest() {
	}

	public RoomRequest(@NotBlank(message = "Name cannot be null") String name,
			@NotNull(message = "Capacity cannot be null") @Min(1) int capacity) {
		super();
		this.name = name;
		this.capacity = capacity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

}
