package com.backend.cinema.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.cinema.domain.Broadcast;
import com.backend.cinema.domain.Movie;
import com.backend.cinema.domain.Room;
import com.backend.cinema.domain.Schedule;
import com.backend.cinema.exception.BroadcastNotFoundException;
import com.backend.cinema.exception.ScheduleNotFoundException;
import com.backend.cinema.exception.UserNotFoundException;
import com.backend.cinema.repository.BroadcastRepository;

public interface BroadcastService {

	
	public Broadcast updateBroadcastRoom(Broadcast oldBroadcast, Room newRoom);

	public Broadcast createBroadcast(Broadcast broadcast, Room room, Movie movie);

	public Broadcast getBroadcast(Integer id);

	public void deleteBroadcast(Integer id);

}
