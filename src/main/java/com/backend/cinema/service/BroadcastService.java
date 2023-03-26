package com.backend.cinema.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.cinema.exception.BroadcastNotFoundException;
import com.backend.cinema.exception.ScheduleNotFoundException;
import com.backend.cinema.exception.UserNotFoundException;
import com.backend.cinema.model.Broadcast;
import com.backend.cinema.model.Movie;
import com.backend.cinema.model.Room;
import com.backend.cinema.model.Schedule;
import com.backend.cinema.model.User;
import com.backend.cinema.repository.BroadcastRepository;

@Service
public class BroadcastService {

	private BroadcastRepository broadcastRepository;

	public BroadcastService(BroadcastRepository broadcastRepository) {
		this.broadcastRepository = broadcastRepository;
	}

	public Broadcast updateBroadcastRoom(Broadcast oldBroadcast, Room newRoom) {
		oldBroadcast.setRoom(newRoom);
		return broadcastRepository.save(oldBroadcast);
	}

	public Broadcast createBroadcast(Broadcast broadcast, Room room, Movie movie) {
		broadcast.setRoom(room);
		broadcast.setMovie(movie);
		return broadcastRepository.save(broadcast);
	}

	public Broadcast getBroadcast(Integer id) {
		Optional<Broadcast> broadcastOptional = broadcastRepository.findById(id);
		if (broadcastOptional.isPresent()) {
			return broadcastOptional.get();
		} else {
			throw new BroadcastNotFoundException(id);
		}
	}

	public void deleteBroadcast(Integer id) {
		Optional<Broadcast> broadcastOptional = broadcastRepository.findById(id);
		if (broadcastOptional.isPresent()) {
			broadcastRepository.delete(broadcastOptional.get());
		} else {
			throw new BroadcastNotFoundException(id);
		}
	}

}
