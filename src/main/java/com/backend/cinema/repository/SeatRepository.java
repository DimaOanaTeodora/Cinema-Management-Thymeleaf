package com.backend.cinema.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.cinema.model.Seat;

public interface SeatRepository extends JpaRepository<Seat, Integer> {

	Optional<Seat> findByNumber(int number);
}
