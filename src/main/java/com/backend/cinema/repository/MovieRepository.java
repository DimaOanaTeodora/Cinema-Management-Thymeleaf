package com.backend.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.cinema.domain.Movie;
import com.backend.cinema.domain.User;

public interface MovieRepository extends JpaRepository<Movie, Integer>{ 

}
