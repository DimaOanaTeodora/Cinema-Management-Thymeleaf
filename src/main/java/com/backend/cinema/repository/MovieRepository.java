package com.backend.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.cinema.model.Movie;
import com.backend.cinema.model.User;

public interface MovieRepository extends JpaRepository<Movie, Integer>{ 

}
