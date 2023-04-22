package com.backend.cinema.domain;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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

	@Size(min = 2, message = "*The name is too short!")
	@Size(max = 10, message = "*The name is too long (<=10 characters)!")
	private String name;

	@Enumerated(EnumType.STRING)
	private MovieType type;

	@OneToMany(mappedBy = "movie")
	private List<Broadcast> broadcasts;

}
