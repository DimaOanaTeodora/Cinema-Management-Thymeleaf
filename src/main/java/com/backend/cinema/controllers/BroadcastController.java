package com.backend.cinema.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.backend.cinema.domain.Broadcast;
import com.backend.cinema.domain.Category;
import com.backend.cinema.domain.Movie;
import com.backend.cinema.domain.Broadcast;
import com.backend.cinema.domain.Reservation;
import com.backend.cinema.domain.Room;
import com.backend.cinema.domain.Schedule;
import com.backend.cinema.domain.security.Authority;
import com.backend.cinema.domain.security.User;
import com.backend.cinema.exceptions.ResourceNotFoundException;
import com.backend.cinema.repositories.BroadcastRepository;
import com.backend.cinema.repositories.MovieRepository;
import com.backend.cinema.repositories.RoomRepository;
import com.backend.cinema.repositories.ScheduleRepository;
import com.backend.cinema.repositories.security.AuthorityRepository;
import com.backend.cinema.repositories.security.UserRepository;
import com.backend.cinema.services.BroadcastService;
import com.backend.cinema.services.MovieService;
import com.backend.cinema.services.ReservationService;
import com.backend.cinema.services.RoomService;

@Controller
@RequestMapping("/broadcasts/")
public class BroadcastController {

	private BroadcastService broadcastService;
	private RoomService roomService;
	private MovieService movieService;
	private ReservationService reservationService;
	
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
	
	
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model model) {
		model.addAttribute("movies", movieRepository.findAll());
		model.addAttribute("schedules", scheduleRepository.findAll());
		model.addAttribute("rooms", roomRepository.findAll());
		model.addAttribute("broadcast", broadcastRepository.findById(id));

		return "update-broadcast";
	}

	@PostMapping("/update/{id}")
	public String saveOrUpdate(@PathVariable("id") int id, @Valid @ModelAttribute Broadcast broadcast, BindingResult bindingResult, Model model) {
		model.addAttribute("broadcast", broadcastRepository.findById(id));

		System.out.println("------------ save or update " + broadcast.getId());
		if (bindingResult.hasErrors()) {
			broadcast.setId(id);
			return "update-broadcast";
		}
		System.out.println("------------Before saving " + broadcast.getId());
		broadcastRepository.save(broadcast);
		System.out.println("------------After saving " + broadcast.getId());

		return "redirect:/main";
	}

	@GetMapping("addBroadcast")
	public String showAddBroadcastForm(Broadcast broadcast, Model model) {
		model.addAttribute("movies", movieRepository.findAll());
		model.addAttribute("schedules", scheduleRepository.findAll());
		model.addAttribute("rooms", roomRepository.findAll());
		return "add-broadcast";
	}


	/*
	 * @GetMapping("list") public String showUpdateForm(Model model) {
	 * model.addAttribute("broadcasts", userRepository.findAll()); return "main"; }
	 */

	@PostMapping("add")
	public String addBroadcast(@Valid Broadcast broadcast, BindingResult result, Model model) {
		System.out.println("------------Log " + broadcast.getId());
		if (result.hasErrors()) {
			return "add-broadcast";
		}
		broadcastRepository.save(broadcast);

		return "main";
	}
	
	@PostMapping(path = "/{roomId}/{movieId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Broadcast> create(@PathVariable Integer roomId, @PathVariable Integer movieId,
			@Valid @RequestBody Broadcast broadcast) {
		Room room = roomService.getRoom(roomId);
		Movie movie = movieService.getMovie(movieId);
		Broadcast savedBroadcast = broadcastService.createBroadcast(broadcast, room, movie);

		return ResponseEntity.created(URI.create("/broadcasts/" + savedBroadcast.getId())).body(savedBroadcast);
	}

	@PutMapping(path = "/{broadcastId}/{newRoomId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Broadcast> create(@PathVariable Integer broadcastId, @PathVariable Integer newRoomId) {

		Room newRoom = roomService.getRoom(newRoomId);
		Broadcast savedBroadcast = broadcastService.updateBroadcastRoom(broadcastService.getBroadcast(broadcastId),
				newRoom);
		return ResponseEntity.created(URI.create("/schedules/" + savedBroadcast.getId())).body(savedBroadcast);
	}

	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Broadcast> getBroadcast(@PathVariable Integer id) {
		return ResponseEntity.ok().body(broadcastService.getBroadcast(id));
	}

	@DeleteMapping(path = "/{broadcastId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> delete(@PathVariable Integer broadcastId) {
		List<Reservation> reservations = reservationService.getAllReservationsByBroadcast(broadcastId);
		for (Reservation reservation : reservations) {
			reservationService.deleteReservation(reservation.getId());
		}
		broadcastService.deleteBroadcast(broadcastId);
		return ResponseEntity.ok().body("Succesfully deleted");
	}

}
