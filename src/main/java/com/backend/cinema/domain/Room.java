package com.backend.cinema.domain;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "room")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	private int capacity;

	@OneToMany(mappedBy = "room", cascade = CascadeType.ALL) 
	private List<Seat> seats;

	@OneToMany(mappedBy = "room")
	private List<Broadcast> broadcasts;

}
