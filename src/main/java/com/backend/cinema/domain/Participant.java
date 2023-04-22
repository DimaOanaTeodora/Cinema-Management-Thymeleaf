package com.backend.cinema.domain;

import lombok.Data;

import java.util.List;

import javax.persistence.*;

@Data
@Entity
public class Participant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String lastName;
	private String firstName;
	private java.util.Date birthDate;

	@OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
	private List<Product> products;

}
