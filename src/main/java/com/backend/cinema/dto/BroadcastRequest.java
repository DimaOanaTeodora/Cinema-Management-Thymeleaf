package com.backend.cinema.dto;

import javax.validation.constraints.NotNull;

public class BroadcastRequest {

	@NotNull(message = "Schedule cannot be null")
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
