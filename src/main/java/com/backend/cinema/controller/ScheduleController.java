package com.backend.cinema.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.cinema.dto.ScheduleRequest;
import com.backend.cinema.mapper.ScheduleMapper;
import com.backend.cinema.model.Broadcast;
import com.backend.cinema.model.Schedule;
import com.backend.cinema.service.BroadcastService;
import com.backend.cinema.service.ScheduleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/schedules")
@Api(value = "/schedules", tags = "Schedules")
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
	@ApiOperation(value = "Update a schedule", notes = "Update a schedule based on a broadcast id")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "The schedule was successfully updated based on the received request"),
			@ApiResponse(code = 400, message = "Validation error on the received request") })
	public ResponseEntity<Schedule> updateSchedule(@PathVariable Integer broadcastId,
			@Valid @RequestBody @ApiParam(name = "schedule", value = "Schedule details", required = true) ScheduleRequest scheduleRequest) {
		Broadcast broadcast = broadcastService.getBroadcast(broadcastId);
		Schedule savedSchedule = scheduleService.updateSchedule(broadcast.getSchedule(),
				scheduleMapper.scheduleRequestToSchedule(scheduleRequest));
		return ResponseEntity.ok().body(savedSchedule);
	}

}
