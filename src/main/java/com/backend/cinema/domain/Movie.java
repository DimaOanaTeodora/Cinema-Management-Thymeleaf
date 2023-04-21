package com.backend.cinema.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "movie")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	@Enumerated(EnumType.STRING)
	private MovieType type;

	@OneToOne(mappedBy = "movie")
	private Broadcast broadcast;

}
