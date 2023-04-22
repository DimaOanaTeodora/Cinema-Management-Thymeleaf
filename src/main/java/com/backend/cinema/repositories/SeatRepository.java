package com.backend.cinema.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.cinema.domain.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {

	Optional<Seat> findByNumber(int number);
}
