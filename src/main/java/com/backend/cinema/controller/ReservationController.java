package com.backend.cinema.controller;

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

import com.backend.cinema.dto.ReservationRequest;
import com.backend.cinema.mapper.ReservationMapper;
import com.backend.cinema.model.Reservation;
import com.backend.cinema.model.Seat;
import com.backend.cinema.service.BroadcastService;
import com.backend.cinema.service.ReservationService;
import com.backend.cinema.service.SeatService;
import com.backend.cinema.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/reservations")
@Api(value = "/reservations", tags = "Reservations")
public class ReservationController {

	private ReservationService reservationService;
	private UserService userService;
	private BroadcastService broadcastService;
	private SeatService seatService;

	private ReservationMapper reservationMapper;

	public ReservationController(ReservationService reservationService, UserService userService,
			BroadcastService broadcastService, SeatService seatService, ReservationMapper reservationMapper) {
		this.reservationService = reservationService;
		this.userService = userService;
		this.broadcastService = broadcastService;
		this.seatService = seatService;
		this.reservationMapper = reservationMapper;
	}

	@PostMapping(path = "/{userId}/{broadcastId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Create a reservation", notes = "Creates a new reservation based on the information received in the request")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "The Reservation was successfully created based on the received request"),
			@ApiResponse(code = 400, message = "Validation error on the received request") })
	public ResponseEntity<Reservation> create(@PathVariable Integer userId, @PathVariable Integer broadcastId,
			@Valid @RequestBody @ApiParam(name = "reservation", value = "Reservation details", required = true) ReservationRequest reservationRequest) {
		List<Seat> seats = seatService.getSeatsFromList(reservationRequest.getSeatIds());
		Reservation savedReservation = reservationService.createReservation(
				reservationMapper.reservationRequestToReservation(reservationRequest), userService.getUser(userId),
				broadcastService.getBroadcast(broadcastId), seats);
		return ResponseEntity.created(URI.create("/reservations/" + savedReservation.getId())).body(savedReservation);
	}

	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Get details for a reservation", notes = "Get the details for a reservation based on the information from the database and the reservation's id")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "The reservation was found"),
			@ApiResponse(code = 404, message = "The reservation was not found") })
	public ResponseEntity<Reservation> getReservation(@PathVariable Integer id) {
		return ResponseEntity.ok().body(reservationService.getReservation(id));
	}

	@DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Cancel a reservation", notes = "Cancel a reservation by id from the database")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "The reservation was found"),
			@ApiResponse(code = 404, message = "The reservation was not found") })
	public ResponseEntity<String> delete(@PathVariable("id") Integer id) {

		reservationService.deleteReservation(id);

		return ResponseEntity.ok().body("Succesfully canceled");
	}

}
