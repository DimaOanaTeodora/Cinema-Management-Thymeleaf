package com.backend.cinema.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.cinema.domain.Broadcast;
import com.backend.cinema.domain.Schedule;
import com.backend.cinema.dto.ScheduleRequest;
import com.backend.cinema.mapper.ScheduleMapper;
import com.backend.cinema.service.BroadcastService;
import com.backend.cinema.service.ScheduleService;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
	private ScheduleService scheduleService;
	private BroadcastService broadcastService;

	private ScheduleMapper scheduleMapper;

	public ScheduleController(ScheduleService scheduleService, BroadcastService broadcastService,
			ScheduleMapper scheduleMapper) {
		this.scheduleService = scheduleService;
		this.broadcastService = broadcastService;
		this.scheduleMapper = scheduleMapper;
	}

	@PutMapping(path = "/{broadcastId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Schedule> updateSchedule(@PathVariable Integer broadcastId,
			@Valid @RequestBody ScheduleRequest scheduleRequest) {
		Broadcast broadcast = broadcastService.getBroadcast(broadcastId);
		Schedule savedSchedule = scheduleService.updateSchedule(broadcast.getSchedule(),
				scheduleMapper.scheduleRequestToSchedule(scheduleRequest));
		return ResponseEntity.ok().body(savedSchedule);
	}

}
