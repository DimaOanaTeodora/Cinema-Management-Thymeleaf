package com.backend.cinema.domain;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.backend.cinema.domain.security.Authority;
import com.backend.cinema.domain.security.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "broadcast")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Broadcast {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne // (cascade = CascadeType.ALL)
	@JoinColumn(name = "room_id", nullable = false)
	private Room room;

	@OneToOne // (cascade = CascadeType.ALL)
	@JoinColumn(name = "movie_id", referencedColumnName = "id")
	private Movie movie;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "schedule_id", nullable = false)
	private Schedule schedule;

	
}
