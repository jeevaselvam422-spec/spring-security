package com.spring.security.spring_security.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.spring_security.dto.RegisterUser;
import com.spring.security.spring_security.entity.Role;
import com.spring.security.spring_security.entity.User;
import com.spring.security.spring_security.repository.RoleRepository_I;
import com.spring.security.spring_security.repository.UserRepository_I;
import com.spring.security.spring_security.security.JwtUtil;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@Log4j2
public class AuthController {

	private AuthenticationManager authenticationManager;
	private JwtUtil jwtUtil;
	private UserRepository_I userRepository;
	private PasswordEncoder passwordEncoder;
	private RoleRepository_I roleRepository;

	public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository_I userRepository,
			PasswordEncoder passwordEncoder, RoleRepository_I roleRepository) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
	}

	@RequestMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody RegisterUser registerUser) {
		log.info("Registering user: {}", registerUser.getUsername());
		if (userRepository.existsByUsername(registerUser.getUsername())) {
			return ResponseEntity.badRequest().body("Error: Username is already taken!");
		}

		User user = new User();
		user.setUsername(registerUser.getUsername());
		user.setPassword(passwordEncoder.encode(registerUser.getPassword()));
		Set<Role> roles = new HashSet<>();
		registerUser.getRoles().forEach(roleName -> {
			roles.add(roleRepository.findByName(roleName)
					.orElseThrow(() -> new RuntimeException("Error: Role not found.")));
		});
		user.setRoles(roles);

		userRepository.save(user);
		log.info("User registered successfully: {}", user.getUsername());

		return ResponseEntity.ok("User registered successfully!");
	}

	@RequestMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody RegisterUser loginRequest) {
		log.info("Logging in user: {}", loginRequest.getUsername());
		try {
			authenticationManager
					.authenticate(new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
							loginRequest.getUsername(), loginRequest.getPassword()));
			return ResponseEntity.ok(jwtUtil.generateToken(loginRequest.getUsername()));
		} catch (org.springframework.security.core.AuthenticationException e) {
			log.error("Authentication failed for user: {}", loginRequest.getUsername());
			return ResponseEntity.status(401).body("Error: Invalid username or password!");
		}
	}

}
