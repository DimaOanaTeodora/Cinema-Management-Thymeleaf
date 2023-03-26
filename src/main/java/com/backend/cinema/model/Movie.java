package com.backend.cinema.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "movie")
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	@Enumerated(EnumType.STRING)
	private MovieType type;

	@OneToOne(mappedBy = "movie")
	@JsonIgnore 
	private Broadcast broadcast;

	public Movie() {
	}

	public Movie(String name, MovieType type) {
		this.name = name;
		this.type = type;
	}

	@Override
	public String toString() {
		return "Movie [iId=" + id + ", name=" + name + ", type=" + type + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MovieType getType() {
		return type;
	}

	public void setType(MovieType type) {
		this.type = type;
	}

	public Broadcast getBroadcast() {
		return broadcast;
	}

	public void setBroadcast(Broadcast broadcast) {
		this.broadcast = broadcast;
	}

}
