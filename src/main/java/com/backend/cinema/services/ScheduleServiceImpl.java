package com.backend.cinema.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.cinema.domain.Schedule;
import com.backend.cinema.exceptions.ResourceNotFoundException;
import com.backend.cinema.repositories.ScheduleRepository;


@Service
public class ScheduleServiceImpl implements ScheduleService{
	private ScheduleRepository scheduleRepository;

	public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
		this.scheduleRepository = scheduleRepository;
	}

	

}
