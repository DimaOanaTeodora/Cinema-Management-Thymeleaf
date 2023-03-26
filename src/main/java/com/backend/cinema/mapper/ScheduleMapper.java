package com.backend.cinema.mapper;

import org.springframework.stereotype.Component;

import com.backend.cinema.dto.ScheduleRequest;
import com.backend.cinema.model.Schedule;

@Component
public class ScheduleMapper {

	public Schedule scheduleRequestToSchedule(ScheduleRequest scheduleRequest) {

		return new Schedule(scheduleRequest.getStartingHour(), scheduleRequest.getEndingHour(),
				scheduleRequest.getDate());
	}
}
