package com.backend.cinema.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.cinema.domain.Room;

public interface RoomRepository extends JpaRepository<Room, Integer>{

	Optional<Room> findByName(String name);
}
