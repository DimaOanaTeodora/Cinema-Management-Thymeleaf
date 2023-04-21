package com.backend.cinema.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.backend.cinema.repositories.ReservationRepository;
import com.backend.cinema.repositories.ScheduleRepository;
import com.backend.cinema.repositories.security.UserRepository;
import com.backend.cinema.services.ReservationService;

@Controller
public class MainController {

	@GetMapping("/login")
	public String showLogInForm() {
		return "login";
	}

	@GetMapping("/access_denied")
	public String accessDeniedPage() {
		return "accessDenied";
	}

	private ScheduleRepository scheduleRepository;
	private ReservationRepository reservationRepository;
	private ReservationService reservationService;
	private UserRepository userRepository;

	@Autowired
	public MainController(ScheduleRepository scheduleRepository, ReservationRepository reservationRepository,
			ReservationService reservationService, UserRepository userRepository) {

		this.scheduleRepository = scheduleRepository;
		this.reservationRepository = reservationRepository;
		this.reservationService = reservationService;
		this.userRepository = userRepository;

	}

	@RequestMapping({ "", "/", "/auction" })
	public ModelAndView getHome(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "admin";
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		model.addAttribute("user", scheduleRepository.findAll());
		model.addAttribute("schedules", scheduleRepository.findAll());
		model.addAttribute("currentUser", username);
		//model.addAttribute("reservations", reservationService.getAllReservationsByUsername(username));
		return new ModelAndView("main");
	}

}
