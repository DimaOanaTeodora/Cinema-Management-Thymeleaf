package com.backend.cinema.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.cinema.domain.Schedule;

public interface ScheduleService {

	public Schedule updateSchedule(Schedule oldSchedule, Schedule newSchedule);

	public Schedule getSchedule(Integer id);

}
