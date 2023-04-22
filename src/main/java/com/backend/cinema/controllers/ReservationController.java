package com.backend.cinema.controllers;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.backend.cinema.domain.Broadcast;
import com.backend.cinema.domain.Reservation;
import com.backend.cinema.domain.Seat;
import com.backend.cinema.domain.security.User;
import com.backend.cinema.exceptions.ResourceNotFoundException;
import com.backend.cinema.repositories.BroadcastRepository;
import com.backend.cinema.repositories.MovieRepository;
import com.backend.cinema.repositories.ReservationRepository;
import com.backend.cinema.repositories.RoomRepository;
import com.backend.cinema.repositories.ScheduleRepository;
import com.backend.cinema.repositories.security.UserRepository;
import com.backend.cinema.services.BroadcastService;
import com.backend.cinema.services.ReservationService;
import com.backend.cinema.services.SeatService;
import com.backend.cinema.services.UserService;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

	private UserService userService;
	private BroadcastService broadcastService;
	private ReservationService reservationService;

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

		model.addAttribute("multiCheckboxAllValues", getMultiCheckboxAllValues());
		
		String username = null;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    username = authentication.getName();
		    System.out.println("---------- reservation controller current logged user is " + username);
		}

		Optional<User> currentUser = userRepository.findByUsername(username); 
		if (currentUser.isEmpty()) 
		{
			throw new ResourceNotFoundException("You need to be logged");
		}


		return "add-reservation";
	}

	/*
	 * @GetMapping("list") public String showUpdateForm(Model model) {
	 * model.addAttribute("broadcasts", userRepository.findAll()); return "main"; }
	 */

	@PostMapping("add")
	public String addReservation(@Valid Reservation reservation, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-reservation";
		}
		String username = null;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    username = authentication.getName();
		    System.out.println("---------- 2 reservation controller current logged user is " + username);
		}

		Optional<User> currentUser = userRepository.findByUsername(username); 
		if (!currentUser.isEmpty()) {
			System.out.println("---------- 2 reservation controller current user is " + currentUser.get().getUsername());
			reservation.setUser(currentUser.get());

		} else {
			throw new ResourceNotFoundException("You need to be logged");
		}

		reservation.setDateRegistered(new Date());
		
		 System.out.println("---------- 2 LOG " + reservation.getSeats().size());
		
		reservation.setNoPersons(2);

		reservation = reservationRepository.save(reservation);
		
		/*List<Reservation> userReservations = currentUser.get().getReservations();
		userReservations.add(reservation);
		currentUser.get().setReservations(userReservations);
		userRepository.save(currentUser.get());*/
		//reservationRepository.save(reservation);

		return "main";
	}

	@GetMapping("/delete/{id}")
	public String deleteReservation(@PathVariable("id") int id, Model model) {
		Reservation reservation = reservationRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid reservation Id:" + id));
		reservationRepository.delete(reservation);
		return "main";
	}

	/*
	 * @PostMapping(path = "/{userId}/{broadcastId}", consumes = {
	 * MediaType.APPLICATION_JSON_VALUE }, produces = {
	 * MediaType.APPLICATION_JSON_VALUE }) public ResponseEntity<Reservation>
	 * create(@PathVariable Integer userId, @PathVariable Integer broadcastId,
	 * 
	 * @Valid @RequestBody Reservation reservationRequest) { List<Seat> seats =
	 * seatService.getSeatsFromList(reservationRequest.getSeatIds()); Reservation
	 * savedReservation = reservationService.createReservation(reservationRequest,
	 * userService.getUser(userId), broadcastService.getBroadcast(broadcastId),
	 * seats);
	 * 
	 * return ResponseEntity.created(URI.create("/reservations/" +
	 * savedReservation.getId())).body(savedReservation); }
	 */

	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Reservation> getReservation(@PathVariable Integer id) {
		return ResponseEntity.ok().body(reservationService.getReservation(id));
	}

	@DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> delete(@PathVariable("id") Integer id) {

		reservationService.deleteReservation(id);

		return ResponseEntity.ok().body("Succesfully canceled");
	}

}
