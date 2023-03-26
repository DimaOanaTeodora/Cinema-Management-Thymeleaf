package com.backend.cinema.controller;

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

import com.backend.cinema.dto.MovieRequest;
import com.backend.cinema.mapper.MovieMapper;
import com.backend.cinema.model.Movie;
import com.backend.cinema.service.MovieService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/movies")
@Api(value = "/movies", tags = "Movies")
public class MovieController {

	private MovieService movieService;
	private MovieMapper movieMapper;

	public MovieController(MovieService movieService, MovieMapper movieMapper) {
		this.movieService = movieService;
		this.movieMapper = movieMapper;
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Create a movie", notes = "Creates a new movie based on the information received in the request")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "The Movie was successfully created based on the received request"),
			@ApiResponse(code = 400, message = "Validation error on the received request") })
	public ResponseEntity<Movie> create(
			@Valid @RequestBody @ApiParam(name = "movie", value = "Movie details", required = true) MovieRequest movieRequest) {
		Movie savedMovie = movieService.createMovie(movieMapper.movieRequestToMovie(movieRequest));
		return ResponseEntity.created(URI.create("/movies/" + savedMovie.getId())).body(savedMovie);
	}

	@PostMapping(path = "/all", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Create movies", notes = "Creates new movies based on list of movies received in the request")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "The movies were successfully created based on the received request"),
			@ApiResponse(code = 400, message = "Validation error on the received request") })
	public ResponseEntity<List<Movie>> createBulk(
			@Valid @RequestBody @ApiParam(name = "movies", value = "List with movies details", required = true) List<MovieRequest> listMovieRequest) {
		for (MovieRequest movieRequest : listMovieRequest) {
			Movie savedMovie = movieService.createMovie(movieMapper.movieRequestToMovie(movieRequest));
		}
		return ResponseEntity.ok().body(movieService.getAllMovies());
	}

	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Get details for a movie", notes = "Get the details for a movie based on the information from the database and the movie's id")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "The movie was found"),
			@ApiResponse(code = 404, message = "The movie was not found") })
	public ResponseEntity<Movie> getMovie(@PathVariable Integer id) {
		return ResponseEntity.ok().body(movieService.getMovie(id));
	}

}
