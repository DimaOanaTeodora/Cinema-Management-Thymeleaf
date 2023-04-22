package com.backend.cinema.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

	
	@GetMapping("addReservation")
	public String showAddReservationForm(Reservation reservation, Model model) {
		List<Broadcast> broadcasts = broadcastRepository.findAll();
		for(Broadcast broadcast : broadcasts) {
			//List<Seat> seats = seatService.getFreeSeatsForBrodcast(broadcast);
			String freeSeats = "";
			/*for(Seat s : seats) {
				freeSeats = freeSeats + s.toString() + "; "; 
			}*/
			broadcast.setFreeSeats(freeSeats);
		}
		model.addAttribute("broadcasts", broadcasts);
		
		//model.addAttribute("freeSeats", seatService.getFreeSeatsForBrodcast(reservation.getBroadcast()));
		
		List<Seat> seats = new ArrayList<Seat>();
		for(int i = 1; i <= 5; i++)
		{
			Seat s = new Seat();
			s.setId(i);
			s.setNumber(i);
			seats.add(s);
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("allSeats", seats);
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "admin";
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		
		Optional<User> currentUser = userRepository.findByUsername(username);
		if(!currentUser.isEmpty())
		{
			reservation.setUser(currentUser.get());
		}
		else {
			throw new ResourceNotFoundException("user not found");
		}

		reservationRepository.save(reservation);
		
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
		reservationRepository.save(reservation);

		return "main";
	}
	

	/*TODO
	 * @PostMapping(path = "/{userId}/{broadcastId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Reservation> create(@PathVariable Integer userId, @PathVariable Integer broadcastId,
			@Valid @RequestBody Reservation reservationRequest) {
		List<Seat> seats = seatService.getSeatsFromList(reservationRequest.getSeatIds());
		Reservation savedReservation = reservationService.createReservation(
				reservationRequest, userService.getUser(userId),
				broadcastService.getBroadcast(broadcastId), seats);
		return ResponseEntity.created(URI.create("/reservations/" + savedReservation.getId())).body(savedReservation);
	}*/

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
