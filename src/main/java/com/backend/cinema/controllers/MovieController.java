package com.backend.cinema.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.cinema.domain.Movie;
import com.backend.cinema.services.MovieService;

@RestController
@RequestMapping("/movies")
public class MovieController {

	private MovieService movieService;

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Movie> create(
			@Valid @RequestBody Movie movie) {
		Movie savedMovie = movieService.createMovie(movie);
		return ResponseEntity.created(URI.create("/movies/" + savedMovie.getId())).body(savedMovie);
	}

	@PostMapping(path = "/all", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Movie>> createBulk(
			@Valid @RequestBody  List<Movie> listMovieRequest) {
		for (Movie movie: listMovieRequest) {
			Movie savedMovie = movieService.createMovie(movie);
		}
		return ResponseEntity.ok().body(movieService.getAllMovies());
	}

	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Movie> getMovie(@PathVariable Integer id) {
		return ResponseEntity.ok().body(movieService.getMovie(id));
	}

}
