package com.backend.cinema.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.cinema.domain.Broadcast;
import com.backend.cinema.domain.Movie;
import com.backend.cinema.domain.Room;
import com.backend.cinema.domain.Schedule;
import com.backend.cinema.exceptions.ResourceNotFoundException;
import com.backend.cinema.repositories.BroadcastRepository;


@Service
public class BroadcastServiceImpl implements BroadcastService{

	private BroadcastRepository broadcastRepository;

	public BroadcastServiceImpl(BroadcastRepository broadcastRepository) {
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
			throw new ResourceNotFoundException(" ResourceNotFoundException ");
		}
	}

	public void deleteBroadcast(Integer id) {
		Optional<Broadcast> broadcastOptional = broadcastRepository.findById(id);
		if (broadcastOptional.isPresent()) {
			broadcastRepository.delete(broadcastOptional.get());
		} else {
			throw new  ResourceNotFoundException(" ResourceNotFoundException ");
		}
	}

}
