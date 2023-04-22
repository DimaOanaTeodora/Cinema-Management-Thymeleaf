package com.backend.cinema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.cinema.domain.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer>{ 

}
