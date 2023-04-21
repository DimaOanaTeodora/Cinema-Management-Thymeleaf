package com.backend.cinema.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.cinema.domain.Broadcast;
import com.backend.cinema.domain.Category;
import com.backend.cinema.domain.Schedule;
import com.backend.cinema.domain.Schedule;
import com.backend.cinema.repositories.ScheduleRepository;
import com.backend.cinema.services.BroadcastService;
import com.backend.cinema.services.ScheduleService;

@Controller
@RequestMapping("/schedules/")
public class ScheduleController {
	private ScheduleService scheduleService;
	private BroadcastService broadcastService;

	private ScheduleRepository scheduleRepository;

	@Autowired
	public ScheduleController(ScheduleRepository scheduleRepository) {

		this.scheduleRepository = scheduleRepository;

	}

	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model model) {
		model.addAttribute("schedule", scheduleRepository.findById(id));

		return "update-schedule";
	}

	@PostMapping("")
	public String saveOrUpdate(@Valid @ModelAttribute Schedule schedule, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "update-schedule";
		}

		scheduleRepository.save(schedule);
		

		return "redirect:/main";
	}

	/*
	 * @PostMapping("edit/{id}") public String updateSchedule(@PathVariable("id")
	 * int id, @Valid Schedule schedule, BindingResult result, Model model) { if
	 * (result.hasErrors()) { schedule.setId(id); return "update-schedule"; }
	 * 
	 * scheduleRepository.save(schedule); model.addAttribute("schedules",
	 * scheduleRepository.findAll()); return "index"; }
	 */

	@PutMapping(path = "/{broadcastId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Schedule> updateSchedule(@PathVariable Integer broadcastId,
			@Valid @RequestBody Schedule scheduleRequest) {
		Broadcast broadcast = broadcastService.getBroadcast(broadcastId);
		Schedule savedSchedule = scheduleService.updateSchedule(broadcast.getSchedule(), scheduleRequest);
		return ResponseEntity.ok().body(savedSchedule);
	}

}
