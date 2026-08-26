package com.spring.security.spring_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.security.spring_security.entity.User;

public interface UserRepository_I extends JpaRepository<User, Long> {
	@Query("SELECT u FROM User u WHERE u.username = :username")
	User findByUsername(String username);

	boolean existsByUsername(String username);

}
