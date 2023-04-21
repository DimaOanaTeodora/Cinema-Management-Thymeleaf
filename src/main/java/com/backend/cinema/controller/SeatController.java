package com.backend.cinema.controller;

import java.util.Dictionary;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.cinema.domain.Broadcast;
import com.backend.cinema.domain.Reservation;
import com.backend.cinema.domain.Room;
import com.backend.cinema.domain.Seat;
import com.backend.cinema.service.BroadcastService;
import com.backend.cinema.service.ReservationService;
import com.backend.cinema.service.SeatService;

@RestController
@RequestMapping("/seats")
public class SeatController {

	private SeatService seatService;
	private ReservationService reservationService;
	private BroadcastService broadcastService;

	public SeatController(SeatService seatService, ReservationService reservationService,
			BroadcastService broadcastService) {
		this.seatService = seatService;
		this.reservationService = reservationService;
		this.broadcastService = broadcastService;
	}

	@GetMapping(path = "/{broadcastId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Dictionary<Room, List<Seat>>> getFreeSeats(@PathVariable Integer broadcastId) {
		Broadcast broadcast = broadcastService.getBroadcast(broadcastId);
		List<Reservation> reservations = reservationService.getAllReservationsByBroadcast(broadcastId);
		return ResponseEntity.ok().body(seatService.getFreeSeats(reservations, broadcast));
	}

}
