package com.backend.cinema.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.backend.cinema.domain.Broadcast;
import com.backend.cinema.repositories.BroadcastRepository;
import com.backend.cinema.repositories.MovieRepository;
import com.backend.cinema.repositories.RoomRepository;
import com.backend.cinema.repositories.ScheduleRepository;

@Controller
@RequestMapping("/broadcasts/")
public class BroadcastController {

	private BroadcastRepository broadcastRepository;
	private MovieRepository movieRepository;
	private ScheduleRepository scheduleRepository;
	private RoomRepository roomRepository;

	@Autowired
	public BroadcastController(BroadcastRepository broadcastRepository, MovieRepository movieRepository,
			ScheduleRepository scheduleRepository, RoomRepository roomRepository) {
		this.broadcastRepository = broadcastRepository;
		this.movieRepository = movieRepository;
		this.scheduleRepository = scheduleRepository;
		this.roomRepository = roomRepository;
	}

	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") int id, Model model) {
		model.addAttribute("movies", movieRepository.findAll());
		model.addAttribute("schedules", scheduleRepository.findAll());
		model.addAttribute("rooms", roomRepository.findAll());

		Broadcast broadcast = broadcastRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid broadcast Id:" + id));

		model.addAttribute("broadcast", broadcast);
		return "update-broadcast";
	}

	@PostMapping("/update/{id}")
	public String updateBroadcast(@PathVariable("id") int id, @Valid Broadcast broadcast, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			broadcast.setId(id);
			return "update-broadcast";
		}

		broadcastRepository.save(broadcast);
		return "redirect:/";
	}

	@GetMapping("addBroadcast")
	public String showAddBroadcastForm(Broadcast broadcast, Model model) {
		model.addAttribute("movies", movieRepository.findAll());
		model.addAttribute("schedules", scheduleRepository.findAll());
		model.addAttribute("rooms", roomRepository.findAll());
		return "add-broadcast";
	}

	@PostMapping("/add")
	public String addBroadcast(@Valid Broadcast broadcast, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-broadcast";
		}
		broadcastRepository.save(broadcast);

		return "redirect:/main";
	}

}
