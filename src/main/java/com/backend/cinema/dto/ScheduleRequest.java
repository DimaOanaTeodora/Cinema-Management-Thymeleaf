package com.backend.cinema.dto;

import java.time.LocalTime;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Schedule request", description = "Required details needed to create a new schedule")
public class ScheduleRequest {

	@NotNull(message = "Starting hour cannot be null")
	@ApiModelProperty(value = "startingHour", required = true, notes = "The starting hour of the movie", example = "10:00", position = 1)
	@JsonFormat(pattern = "HH:mm")
	private LocalTime startingHour;

	@NotNull(message = "Ending hour cannot be null")
	@ApiModelProperty(value = "endingHour", required = true, notes = "The ending hour of the movie", example = "12:00", position = 2)
	@JsonFormat(pattern = "HH:mm")
	private LocalTime endingHour;

	@NotNull(message = "Date cannot be null")
	@ApiModelProperty(value = "date", required = true, notes = "The date of the movie", example = "05-07-2023", position = 3)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
	private Date date;

	public ScheduleRequest() {
	}

	public ScheduleRequest(@NotBlank(message = "Starting hour cannot be null") LocalTime startingHour,
			@NotBlank(message = "Ending hour cannot be null") LocalTime endingHour,
			@NotBlank(message = "Date cannot be null") Date date) {
		this.startingHour = startingHour;
		this.endingHour = endingHour;
		this.date = date;
	}

	public LocalTime getStartingHour() {
		return startingHour;
	}

	public void setStartingHour(LocalTime startingHour) {
		this.startingHour = startingHour;
	}

	public LocalTime getEndingHour() {
		return endingHour;
	}

	public void setEndingHour(LocalTime endingHour) {
		this.endingHour = endingHour;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
