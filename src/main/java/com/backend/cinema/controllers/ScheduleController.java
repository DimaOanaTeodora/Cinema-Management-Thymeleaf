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

import org.springframework.web.bind.annotation.RequestMapping;

import com.backend.cinema.configuration.Log;
import com.backend.cinema.domain.Schedule;
import com.backend.cinema.repositories.ScheduleRepository;
import com.backend.cinema.services.ScheduleService;

@Controller
@RequestMapping("/schedules/")
public class ScheduleController {

	private ScheduleService scheduleService;
	private ScheduleRepository scheduleRepository;

	@Autowired
	public ScheduleController(ScheduleService scheduleService, ScheduleRepository scheduleRepository) {

		this.scheduleService = scheduleService;
		this.scheduleRepository = scheduleRepository;

	}

	@Log
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model model) {
		model.addAttribute("schedule", scheduleRepository.findById(id));

		return "update-schedule";
	}

	@Log
	@PostMapping("")
	public String saveOrUpdate(@Valid @ModelAttribute Schedule schedule, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "update-schedule";
		}

		scheduleService.create(schedule);

		return "redirect:/main";
	}

}
