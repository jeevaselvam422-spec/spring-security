package com.spring.security.spring_security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.spring_security.service.CoreLogic;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private CoreLogic coreLogic;
	
	@RequestMapping("/profile")
	public String getUserProfile() {
		return coreLogic.userLogic();
	}
	
	@GetMapping("/settings")
	public String getUserSettings() {
		return "User settings accessed";
	}

}
