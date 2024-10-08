package com.backend.cinema.domain.security;

import lombok.*;
import javax.persistence.*;

import com.backend.cinema.domain.Reservation;

import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class User {

	@Id
	private String username;
	private String password;

	@Singular
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "username"), inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
	private Set<Authority> authorities;

	@OneToMany(mappedBy = "user")
	private List<Reservation> reservations;

	@Builder.Default
	private Boolean enabled = true;

	@Builder.Default
	private Boolean accountNotExpired = true;

	@Builder.Default
	private Boolean accountNotLocked = true;

	@Builder.Default
	private Boolean credentialsNotExpired = true;

}
