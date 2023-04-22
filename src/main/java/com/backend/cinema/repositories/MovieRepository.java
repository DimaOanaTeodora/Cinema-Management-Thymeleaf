package com.backend.cinema.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.backend.cinema.domain.Movie;

@Repository
public interface MovieRepository extends PagingAndSortingRepository <Movie, Integer>{ 

}
