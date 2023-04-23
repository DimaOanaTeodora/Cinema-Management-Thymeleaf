package com.backend.cinema.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import com.backend.cinema.configuration.Log;
import com.backend.cinema.domain.Broadcast;
import com.backend.cinema.domain.Category;
import com.backend.cinema.domain.Reservation;
import com.backend.cinema.domain.Seat;
import com.backend.cinema.domain.security.User;
import com.backend.cinema.exceptions.ResourceNotFoundException;
import com.backend.cinema.repositories.BroadcastRepository;
import com.backend.cinema.repositories.ReservationRepository;

import com.backend.cinema.repositories.security.UserRepository;

import com.backend.cinema.services.SeatService;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

	private SeatService seatService;
	private BroadcastRepository broadcastRepository;
	private UserRepository userRepository;
	private ReservationRepository reservationRepository;

	@Autowired
	public ReservationController(BroadcastRepository broadcastRepository, UserRepository userRepository,
			ReservationRepository reservationRepository, SeatService seatService) {
		this.broadcastRepository = broadcastRepository;
		this.userRepository = userRepository;
		this.reservationRepository = reservationRepository;
		this.seatService = seatService;
	}

	@ModelAttribute("multiCheckboxAllValues")
	public List<Seat> getMultiCheckboxAllValues() {

		List<Seat> seats = new ArrayList<Seat>();
		for (int i = 1; i <= 5; i++) {
			Seat s = new Seat();
			s.setId(i);
			s.setNumber(i);
			seats.add(s);
		}
		return seats;
	}

	@Log
	@GetMapping("addReservation")
	public String showAddReservationForm(Reservation reservation, Model model) {
		List<Broadcast> broadcasts = broadcastRepository.findAll();
		for (Broadcast broadcast : broadcasts) {
			List<Seat> seats = seatService.getFreeSeatsForBrodcast(broadcast);
			String freeSeats = "";
			for (Seat s : seats) {
				freeSeats = freeSeats + s.getNumber() + "; ";
			}
			broadcast.setFreeSeats(freeSeats);
		}
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
		String username = null;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			username = authentication.getName();
		}

		Optional<User> currentUser = userRepository.findByUsername(username);
		if (!currentUser.isEmpty()) {
			reservation.setUser(currentUser.get());

		} else {
			throw new ResourceNotFoundException("You need to be logged");
		}

		reservation.setDateRegistered(new Date());

		reservation.setNoPersons(reservation.getReservedSeats().size());

		reservation = reservationRepository.save(reservation);

		return "redirect:/main";
	}

	@Log
	@GetMapping("/delete/{id}")
	public String deleteReservation(@PathVariable("id") int id, Model model) {
		Reservation reservation = reservationRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid reservation Id:" + id));
		reservationRepository.delete(reservation);
		return "redirect:/main";
	}

}
