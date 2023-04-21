package com.backend.cinema.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.cinema.domain.Reservation;
import com.backend.cinema.domain.Seat;
import com.backend.cinema.services.BroadcastService;
import com.backend.cinema.services.ReservationService;
import com.backend.cinema.services.SeatService;
import com.backend.cinema.services.UserService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

	private ReservationService reservationService;
	private UserService userService;
	private BroadcastService broadcastService;
	private SeatService seatService;

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
