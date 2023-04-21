package com.backend.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.cinema.domain.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer>{ 

}
