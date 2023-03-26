package com.backend.cinema.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Broadcast request", description = "Required details needed to create a new broadcast")
public class BroadcastRequest {

	@NotNull(message = "Schedule cannot be null")
	@ApiModelProperty(value = "schedule", required = true, notes = "The schedule of the movie", example = "D2", position = 3)
	private ScheduleRequest schedule;

	public BroadcastRequest() {
	}

	public BroadcastRequest(@NotNull(message = "Schedule cannot be null") ScheduleRequest schedule) {
		this.schedule = schedule;
	}

	public ScheduleRequest getSchedule() {
		return schedule;
	}

	public void setSchedule(ScheduleRequest schedule) {
		this.schedule = schedule;
	}

}
