package com.backend.cinema.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.cinema.dto.RoomRequest;
import com.backend.cinema.mapper.RoomMapper;
import com.backend.cinema.model.Room;
import com.backend.cinema.model.Seat;
import com.backend.cinema.service.RoomService;
import com.backend.cinema.service.SeatService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/rooms")
@Api(value = "/rooms", tags = "Rooms")
public class RoomController {
	private RoomService roomService;
	private RoomMapper roomMapper;
	private SeatService seatService;

	public RoomController(RoomService roomService, RoomMapper roomMapper, SeatService seatService) {
		this.roomService = roomService;
		this.roomMapper = roomMapper;
		this.seatService = seatService;
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Create a room", notes = "Creates a new room based on the information received in the request")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "The Room was successfully created based on the received request"),
			@ApiResponse(code = 400, message = "Validation error on the received request") })
	public ResponseEntity<Room> create(
			@Valid @RequestBody @ApiParam(name = "room", value = "Room details", required = true) RoomRequest roomRequest) {

		Room savedRoom = roomService.createRoom(roomMapper.roomRequestToRoom(roomRequest)); // create room
		List<Seat> seats = seatService.createSeats(savedRoom); // create seats
		savedRoom = roomService.saveSeats(seats, savedRoom); // save seats for room
		return ResponseEntity.created(URI.create("/rooms/" + savedRoom.getId())).body(savedRoom);
	}

	@PostMapping(path = "/all", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Create rooms", notes = "Creates new rooms based on list of rooms received in the request")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "The rooms were successfully created based on the received request"),
			@ApiResponse(code = 400, message = "Validation error on the received request") })
	public ResponseEntity<List<Room>> createBulk(
			@Valid @RequestBody @ApiParam(name = "rooms", value = "List with rooms details", required = true) List<RoomRequest> listRoomRequest) {
		for (RoomRequest roomRequest : listRoomRequest) {
			Room savedRoom = roomService.createRoom(roomMapper.roomRequestToRoom(roomRequest)); // create room
			List<Seat> seats = seatService.createSeats(savedRoom); // create seats
			savedRoom = roomService.saveSeats(seats, savedRoom);
		}
		return ResponseEntity.ok().body(roomService.getAllRooms());
	}

	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Get details for a room", notes = "Get the details for a room based on the information from the database and the room's id")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "The room was found"),
			@ApiResponse(code = 404, message = "The room was not found") })
	public ResponseEntity<Room> getRoom(@PathVariable Integer id) {
		return ResponseEntity.ok().body(roomService.getRoom(id));
	}
}
