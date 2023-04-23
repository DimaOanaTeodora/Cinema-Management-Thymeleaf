package com.backend.cinema.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.backend.cinema.configuration.Log;
import com.backend.cinema.domain.Movie;
import com.backend.cinema.domain.MovieType;
import com.backend.cinema.repositories.MovieRepository;

@Controller
@RequestMapping("/movies/")
public class MovieController {

	private MovieRepository movieRepository;

	@Autowired
	public MovieController(MovieRepository movieRepository) {

		this.movieRepository = movieRepository;
	}

	@Log
	@GetMapping("addMovie")
	public String showAddMovieForm(Movie movie, Model model) {
		return "add-movie";
	}

	@Log
	@PostMapping("/add")
	public String addMovie(@Valid @ModelAttribute Movie movie, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-movie";
		}
		movieRepository.save(movie);

		return "redirect:/main";
	}
}
