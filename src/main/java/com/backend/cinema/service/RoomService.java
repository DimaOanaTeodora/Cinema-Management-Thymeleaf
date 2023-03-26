package com.backend.cinema.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.cinema.domain.Movie;
import com.backend.cinema.domain.Room;
import com.backend.cinema.domain.Seat;
import com.backend.cinema.exception.RoomNotFoundException;
import com.backend.cinema.repository.RoomRepository;

public interface RoomService {

	public Room saveSeats(List<Seat> seats, Room room);

	public Room createRoom(Room room);

	public Room getRoom(Integer id);
	
	public List<Room> getAllRooms();
}
