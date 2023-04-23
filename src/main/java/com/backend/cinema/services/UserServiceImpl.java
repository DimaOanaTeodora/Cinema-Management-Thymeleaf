package com.backend.cinema.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.cinema.configuration.Log;
import com.backend.cinema.domain.security.Authority;
import com.backend.cinema.domain.security.User;
import com.backend.cinema.repositories.security.AuthorityRepository;
import com.backend.cinema.repositories.security.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private AuthorityRepository authorityRepository;
	private PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, AuthorityRepository authorityRepository,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.authorityRepository = authorityRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Log
	public void create(User user) {

		Authority adminRole = authorityRepository.save(Authority.builder().role("ROLE_ADMIN").build());

		User newUser = User.builder().username(user.getUsername()).password(passwordEncoder.encode(user.getPassword()))
				.authority(adminRole).build();

		userRepository.save(newUser);
	}

}
