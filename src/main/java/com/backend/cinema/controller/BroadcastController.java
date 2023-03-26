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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.cinema.dto.BroadcastRequest;
import com.backend.cinema.mapper.BroadcastMapper;

import com.backend.cinema.model.Broadcast;
import com.backend.cinema.model.Movie;
import com.backend.cinema.model.Reservation;
import com.backend.cinema.model.Room;
import com.backend.cinema.service.BroadcastService;
import com.backend.cinema.service.MovieService;
import com.backend.cinema.service.ReservationService;
import com.backend.cinema.service.RoomService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/broadcasts")
@Api(value = "/broadcasts", tags = "Broadcasts")
public class BroadcastController {

	private BroadcastService broadcastService;
	private RoomService roomService;
	private MovieService movieService;
	private ReservationService reservationService;

	private BroadcastMapper broadcastMapper;

	public BroadcastController(BroadcastService broadcastService, RoomService roomService, MovieService movieService,ReservationService reservationService,
			BroadcastMapper broadcastMapper) {
		this.broadcastService = broadcastService;
		this.roomService = roomService;
		this.movieService = movieService;
		this.reservationService = reservationService;
		this.broadcastMapper = broadcastMapper;
	}

	@PostMapping(path = "/{roomId}/{movieId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Create a broadcast", notes = "Creates a new broadcast based on the information received in the request")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "The Broadcast was successfully created based on the received request"),
			@ApiResponse(code = 400, message = "Validation error on the received request") })
	public ResponseEntity<Broadcast> create(@PathVariable Integer roomId, @PathVariable Integer movieId,
			@Valid @RequestBody @ApiParam(name = "broadcast", value = "Broadcast details", required = true) BroadcastRequest broadcastRequest) {
		Room room = roomService.getRoom(roomId);
		Movie movie = movieService.getMovie(movieId);
		Broadcast savedBroadcast = broadcastService
				.createBroadcast(broadcastMapper.broadcastRequestToBroadcast(broadcastRequest), room, movie);

		return ResponseEntity.created(URI.create("/broadcasts/" + savedBroadcast.getId())).body(savedBroadcast);
	}

	@PutMapping(path = "/{broadcastId}/{newRoomId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Update the room for a broadcast", notes = "Update the room for a broadcast based by room id")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "The broadcast was successfully updated based on the received request") })
	public ResponseEntity<Broadcast> create(@PathVariable Integer broadcastId, @PathVariable Integer newRoomId) {

		Room newRoom = roomService.getRoom(newRoomId);
		Broadcast savedBroadcast = broadcastService.updateBroadcastRoom(broadcastService.getBroadcast(broadcastId),
				newRoom);
		return ResponseEntity.created(URI.create("/schedules/" + savedBroadcast.getId())).body(savedBroadcast);
	}

	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Get details for a broadcast", notes = "Get the details for a broadcast based on the information from the database and the broadcast's id")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "The broadcast was found"),
			@ApiResponse(code = 404, message = "The broadcast was not found") })
	public ResponseEntity<Broadcast> getBroadcast(@PathVariable Integer id) {
		return ResponseEntity.ok().body(broadcastService.getBroadcast(id));
	}

	@DeleteMapping(path = "/{broadcastId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Delete a broadcast", notes = "Delete a broadcast by movie id from the database and it's dependencies")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "The broadcast was found"),
			@ApiResponse(code = 404, message = "The broadcast was not found") })
	public ResponseEntity<String> delete(@PathVariable Integer broadcastId) {
		List<Reservation> reservations = reservationService.getAllReservationsByBroadcast(broadcastId);
		for(Reservation reservation : reservations) {
			reservationService.deleteReservation(reservation.getId());
		}
		broadcastService.deleteBroadcast(broadcastId);
		return ResponseEntity.ok().body("Succesfully deleted");
	}

}
