package com.backend.cinema.services;

import org.springframework.stereotype.Service;

import com.backend.cinema.configuration.Log;
import com.backend.cinema.domain.Schedule;
import com.backend.cinema.repositories.ScheduleRepository;

@Service
public class ScheduleServiceImpl implements ScheduleService {
	private ScheduleRepository scheduleRepository;

	public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
		this.scheduleRepository = scheduleRepository;
	}
	
	@Log
	public void create(Schedule schedule) {
		scheduleRepository.save(schedule);
	}

}
