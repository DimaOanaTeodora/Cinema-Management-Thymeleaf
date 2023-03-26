package com.backend.cinema.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.cinema.domain.Schedule;
import com.backend.cinema.exception.ScheduleNotFoundException;
import com.backend.cinema.repository.ScheduleRepository;

public interface ScheduleService {

	public Schedule updateSchedule(Schedule oldSchedule, Schedule newSchedule);

	public Schedule getSchedule(Integer id);

}
