package com.spring.security.spring_security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.security.spring_security.entity.Role;

public interface RoleRepository_I extends JpaRepository<Role, Long> {
	
	Optional<Role> findByName(String name);

}
