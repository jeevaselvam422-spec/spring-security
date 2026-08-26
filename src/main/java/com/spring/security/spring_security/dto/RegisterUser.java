package com.spring.security.spring_security.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUser {
	
	private String username;
	private String password;
	private String email;
	private Set<String> roles;

}
