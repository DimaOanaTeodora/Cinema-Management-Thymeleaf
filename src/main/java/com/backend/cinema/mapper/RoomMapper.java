package com.backend.cinema.mapper;

import org.springframework.stereotype.Component;

import com.backend.cinema.dto.RoomRequest;
import com.backend.cinema.model.Room;

@Component
public class RoomMapper {

	public Room roomRequestToRoom(RoomRequest roomRequest) {

		return new Room(roomRequest.getName(), roomRequest.getCapacity());
	}
}
