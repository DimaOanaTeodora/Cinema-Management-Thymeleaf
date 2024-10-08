package com.backend.cinema.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.backend.cinema.domain.Broadcast;
import com.backend.cinema.exceptions.ResourceNotFoundException;
import com.backend.cinema.repositories.BroadcastRepository;
import com.backend.cinema.repositories.MovieRepository;
import com.backend.cinema.repositories.RoomRepository;
import com.backend.cinema.repositories.ScheduleRepository;
import com.backend.cinema.services.BroadcastService;

@Controller
@RequestMapping("/broadcasts/")
public class BroadcastController {

	private BroadcastRepository broadcastRepository;
	private BroadcastService broadcastService;
	private MovieRepository movieRepository;
	private ScheduleRepository scheduleRepository;
	private RoomRepository roomRepository;

	@Autowired
	public BroadcastController(BroadcastRepository broadcastRepository, MovieRepository movieRepository,
			ScheduleRepository scheduleRepository, RoomRepository roomRepository, BroadcastService broadcastService) {
		this.broadcastRepository = broadcastRepository;
		this.movieRepository = movieRepository;
		this.scheduleRepository = scheduleRepository;
		this.roomRepository = roomRepository;
		this.broadcastService = broadcastService;
	}

	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") int id, Model model) {
		model.addAttribute("movies", movieRepository.findAll());
		model.addAttribute("schedules", scheduleRepository.findAll());
		model.addAttribute("rooms", roomRepository.findAll());

		Optional<Broadcast> broadcast = broadcastRepository.findById(id);
		if (!broadcast.isPresent()) {

			throw new ResourceNotFoundException("Broadcast " + id + " not found");
		}

		model.addAttribute("broadcast", broadcast.get());
		return "update-broadcast";
	}

	@PostMapping("/update/{id}")
	public String updateBroadcast(@PathVariable("id") int id, @Valid Broadcast broadcast, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			broadcast.setId(id);
			return "update-broadcast";
		}

		broadcastService.update(broadcast);

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
		broadcastService.create(broadcast);

		return "redirect:/main";
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ResourceNotFoundException.class)
	public ModelAndView handlerNotFoundException(Exception exception) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.getModel().put("exception", exception);
		modelAndView.setViewName("notFoundException");
		return modelAndView;
	}

}
