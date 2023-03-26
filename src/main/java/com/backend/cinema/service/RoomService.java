package com.backend.cinema.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.cinema.exception.RoomNotFoundException;
import com.backend.cinema.model.Movie;
import com.backend.cinema.model.Room;
import com.backend.cinema.model.Seat;
import com.backend.cinema.repository.RoomRepository;

@Service
public class RoomService {
	private RoomRepository roomRepository;

	public RoomService(RoomRepository roomRepository) {
		this.roomRepository = roomRepository;
	}

	public Room saveSeats(List<Seat> seats, Room room) {
		room.setSeats(seats);
		return roomRepository.save(room);
	}

	public Room createRoom(Room room) {
		return roomRepository.save(room);
	}

	public Room getRoom(Integer id) {
		Optional<Room> roomOptional = roomRepository.findById(id);
		if (roomOptional.isPresent()) {
			return roomOptional.get();
		} else {
			throw new RoomNotFoundException(id);
		}
	}
	
	public List<Room> getAllRooms() {
		return roomRepository.findAll();
	}
}
