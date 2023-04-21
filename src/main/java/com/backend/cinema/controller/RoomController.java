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

import com.backend.cinema.domain.Room;
import com.backend.cinema.domain.Seat;
import com.backend.cinema.dto.RoomRequest;
import com.backend.cinema.mapper.RoomMapper;
import com.backend.cinema.service.RoomService;
import com.backend.cinema.service.SeatService;

@RestController
@RequestMapping("/rooms")
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
	public ResponseEntity<Room> create(
			@Valid @RequestBody RoomRequest roomRequest) {

		Room savedRoom = roomService.createRoom(roomMapper.roomRequestToRoom(roomRequest)); // create room
		List<Seat> seats = seatService.createSeats(savedRoom); // create seats
		savedRoom = roomService.saveSeats(seats, savedRoom); // save seats for room
		return ResponseEntity.created(URI.create("/rooms/" + savedRoom.getId())).body(savedRoom);
	}

	@PostMapping(path = "/all", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Room>> createBulk(
			@Valid @RequestBody List<RoomRequest> listRoomRequest) {
		for (RoomRequest roomRequest : listRoomRequest) {
			Room savedRoom = roomService.createRoom(roomMapper.roomRequestToRoom(roomRequest)); // create room
			List<Seat> seats = seatService.createSeats(savedRoom); // create seats
			savedRoom = roomService.saveSeats(seats, savedRoom);
		}
		return ResponseEntity.ok().body(roomService.getAllRooms());
	}

	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Room> getRoom(@PathVariable Integer id) {
		return ResponseEntity.ok().body(roomService.getRoom(id));
	}
}
