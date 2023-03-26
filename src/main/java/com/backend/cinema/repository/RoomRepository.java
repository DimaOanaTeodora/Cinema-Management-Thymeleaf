package com.backend.cinema.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.cinema.domain.Room;
import com.backend.cinema.domain.User;

public interface RoomRepository extends JpaRepository<Room, Integer>{

	Optional<Room> findByName(String name);
}
