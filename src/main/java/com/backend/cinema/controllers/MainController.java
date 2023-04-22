package com.backend.cinema.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.backend.cinema.repositories.BroadcastRepository;
import com.backend.cinema.repositories.MovieRepository;
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
	private BroadcastRepository broadcastRepository;
	private MovieRepository movieRepository;

	@Autowired
	public MainController(ScheduleRepository scheduleRepository, ReservationRepository reservationRepository,
			ReservationService reservationService, UserRepository userRepository,
			BroadcastRepository broadcastRepository, MovieRepository movieRepository) {

		this.scheduleRepository = scheduleRepository;
		this.reservationRepository = reservationRepository;
		this.reservationService = reservationService;
		this.userRepository = userRepository;
		this.broadcastRepository = broadcastRepository;
		this.movieRepository = movieRepository;

	}

	@GetMapping("/main")
	public String showMainPage(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = null;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			username = authentication.getName();
			System.out.println("---------- current logged user is " + username);
		}

		model.addAttribute("user", scheduleRepository.findAll());
		model.addAttribute("broadcasts", broadcastRepository.findAll());
		model.addAttribute("movies", movieRepository.findAll());
		model.addAttribute("currentUser", username);

		if (username != null && !userRepository.findByUsername(username).isEmpty()) {
			System.out.println("---------- current not empty " + username);
			model.addAttribute("reservations", reservationService.getAllReservationsByUsername(username));
		}

		return "main";
	}

	@RequestMapping({ "", "/", "/auction" })
	public ModelAndView getHome(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = null;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			username = authentication.getName();
			System.out.println("---------- current logged user is " + username);
		}

		model.addAttribute("user", scheduleRepository.findAll());
		model.addAttribute("broadcasts", broadcastRepository.findAll());
		model.addAttribute("movies", movieRepository.findAll());
		model.addAttribute("currentUser", username);

		if (username != null && !userRepository.findByUsername(username).isEmpty()) {
			System.out.println("---------- current not empty " + username);
			model.addAttribute("reservations", reservationService.getAllReservationsByUsername(username));
		}
		return new ModelAndView("main");
	}

}
