package com.backend.cinema.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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

import com.backend.cinema.configuration.Log;
import com.backend.cinema.domain.Broadcast;
import com.backend.cinema.domain.Reservation;
import com.backend.cinema.domain.Seat;
import com.backend.cinema.domain.security.User;
import com.backend.cinema.exceptions.ResourceNotFoundException;

import com.backend.cinema.repositories.security.UserRepository;
import com.backend.cinema.services.BroadcastService;
import com.backend.cinema.services.ReservationService;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

	private BroadcastService broadcastService;
	private UserRepository userRepository;
	private ReservationService reservationService;

	@Autowired
	public ReservationController(BroadcastService broadcastService, UserRepository userRepository,
			ReservationService reservationService) {
		this.broadcastService = broadcastService;
		this.userRepository = userRepository;
		this.reservationService = reservationService;
	}

	@Log
	@GetMapping("addReservation")
	public String showAddReservationForm(Reservation reservation, Model model) {
		List<Broadcast> broadcasts = broadcastService.setBroadcastFreeSeats();

		model.addAttribute("broadcasts", broadcasts);

		List<Seat> seats = new ArrayList<Seat>();
		for (int i = 1; i <= 5; i++) {
			Seat s = new Seat();
			s.setId(i);
			s.setNumber(i);
			seats.add(s);
		}

		model.addAttribute("categoriesAll", seats);

		String username = null;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			username = authentication.getName();
		}

		Optional<User> currentUser = userRepository.findByUsername(username);
		if (currentUser.isEmpty()) {
			throw new ResourceNotFoundException("You need to be logged");
		}

		return "add-reservation";
	}

	@Log
	@PostMapping("add")
	public String addReservation(@Valid Reservation reservation, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-reservation";
		}

		reservationService.create(reservation);

		return "redirect:/main";
	}

	@Log
	@GetMapping("/delete/{id}")
	public String deleteReservation(@PathVariable("id") int id, Model model) {
		reservationService.delete(id);
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
