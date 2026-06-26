package com.spring.security.spring_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.security.spring_security.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	@Query("SELECT r FROM Role r WHERE r.name = :name")
	Role findByName(String name);

}
