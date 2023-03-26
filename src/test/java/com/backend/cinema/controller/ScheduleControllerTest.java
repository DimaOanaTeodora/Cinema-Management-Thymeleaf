package com.backend.cinema.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.backend.cinema.dto.ScheduleRequest;
import com.backend.cinema.mapper.ScheduleMapper;
import com.backend.cinema.model.Broadcast;
import com.backend.cinema.model.Schedule;
import com.backend.cinema.service.BroadcastService;
import com.backend.cinema.service.ScheduleService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = ScheduleController.class)
public class ScheduleControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private ScheduleService scheduleService;

	@MockBean
	private BroadcastService broadcastService;

	@MockBean
	private ScheduleMapper scheduleMapper;

	@Test
	public void updateSchedule() throws Exception {
		ScheduleRequest oldSchedule = new ScheduleRequest();
		ScheduleRequest newSchedule = new ScheduleRequest();

		oldSchedule.setDate(new SimpleDateFormat("dd-MM-yyyy").parse("05-07-2023"));
		oldSchedule.setEndingHour(LocalTime.of(12, 12));
		oldSchedule.setStartingHour(LocalTime.of(10, 10));

		newSchedule.setDate(new SimpleDateFormat("dd-MM-yyyy").parse("10-02-2023"));
		newSchedule.setEndingHour(LocalTime.of(11, 12));
		newSchedule.setStartingHour(LocalTime.of(10, 10));

		Schedule schedule1 = new Schedule();
		when(scheduleMapper.scheduleRequestToSchedule(any())).thenReturn(schedule1);
		schedule1 = scheduleMapper.scheduleRequestToSchedule(oldSchedule);
		schedule1.setId(1);

		Schedule schedule2 = new Schedule();
		when(scheduleMapper.scheduleRequestToSchedule(any())).thenReturn(schedule2);
		schedule2 = scheduleMapper.scheduleRequestToSchedule(oldSchedule);
		schedule2.setId(1);

		Broadcast broadcast = new Broadcast(schedule1);
		broadcast.setId(1);
		when(broadcastService.getBroadcast(any())).thenReturn(broadcast);

		when(scheduleService.updateSchedule(any(), any())).thenReturn(schedule2);

		mockMvc.perform(put("/schedules/{broadcastId}", 1).contentType("application/json")
				.content(objectMapper.writeValueAsString(newSchedule)))

				.andExpect(status().isOk()).andExpect(jsonPath("$.date").value(schedule2.getDate()))
				.andExpect(jsonPath("$.endingHour").value(schedule2.getEndingHour()))
				.andExpect(jsonPath("$.startingHour").value(schedule2.getStartingHour()));
	}
}
