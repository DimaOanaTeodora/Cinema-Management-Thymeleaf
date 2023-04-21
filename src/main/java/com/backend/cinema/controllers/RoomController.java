package com.backend.cinema.controllers;

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
import com.backend.cinema.services.RoomService;
import com.backend.cinema.services.SeatService;

@RestController
@RequestMapping("/rooms")
public class RoomController {
	private RoomService roomService;
	private SeatService seatService;

	
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Room> create(
			@Valid @RequestBody Room roomRequest) {

		Room savedRoom = roomService.createRoom(roomRequest); // create room
		List<Seat> seats = seatService.createSeats(savedRoom); // create seats
		savedRoom = roomService.saveSeats(seats, savedRoom); // save seats for room
		return ResponseEntity.created(URI.create("/rooms/" + savedRoom.getId())).body(savedRoom);
	}

	@PostMapping(path = "/all", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Room>> createBulk(
			@Valid @RequestBody List<Room> listRoomRequest) {
		for (Room roomRequest : listRoomRequest) {
			Room savedRoom = roomService.createRoom(roomRequest); // create room
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
