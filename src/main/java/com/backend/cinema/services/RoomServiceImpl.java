package com.backend.cinema.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.cinema.domain.Movie;
import com.backend.cinema.domain.Room;
import com.backend.cinema.domain.Seat;
import com.backend.cinema.exceptions.ResourceNotFoundException;
import com.backend.cinema.repositories.RoomRepository;


@Service
public class RoomServiceImpl implements RoomService {
	private RoomRepository roomRepository;

	public RoomServiceImpl(RoomRepository roomRepository) {
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
			throw new  ResourceNotFoundException(" ResourceNotFoundException ");
		}
	}
	
	public List<Room> getAllRooms() {
		return roomRepository.findAll();
	}
}
