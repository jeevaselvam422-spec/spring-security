package com.spring.security.spring_security.security;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.security.spring_security.repository.UserRepository_I;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

	@Autowired
	private UserRepository_I userRepository;

	public String generateToken(String username) {
		return io.jsonwebtoken.Jwts.builder().setSubject(username)
				.claim("roles",
						userRepository.findByUsername(username).getRoles().stream().map(role -> role.getName())
								.collect(Collectors.joining(",")))
				.signWith(secretKey).setExpiration(new java.util.Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.compact();
	}

	public String extractUsername(String token) {
		return io.jsonwebtoken.Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody()
				.getSubject();
	}

	public Set<String> extractRoles(String token) {
		String rolesString = io.jsonwebtoken.Jwts.parserBuilder().setSigningKey(secretKey).build()
				.parseClaimsJws(token).getBody().get("roles", String.class);
		if (rolesString == null || rolesString.isEmpty()) {
			return new HashSet<>();
		} else {
			List<String> rolesList = Arrays.asList(rolesString.split(","));
			return new HashSet<>(rolesList);
		}
	}
	
	public boolean validateToken(String token, String username) {
		String extractedUsername = extractUsername(token);
		return (extractedUsername.equals(username) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		try {
			java.util.Date expirationDate = io.jsonwebtoken.Jwts.parserBuilder().setSigningKey(secretKey).build()
					.parseClaimsJws(token).getBody().getExpiration();
			return expirationDate.before(new java.util.Date());
		} catch (io.jsonwebtoken.ExpiredJwtException e) {
			return false;
		}
	}

}
