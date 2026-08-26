package com.spring.security.spring_security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.spring_security.service.CoreLogic;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	@Autowired
	private CoreLogic coreLogic;
	
	@RequestMapping("/dashboard")
	public String getAdminDashboard() {
		return coreLogic.adminLogic();
	}
	
	@GetMapping("/settings")
	public String getAdminSettings() {
		return "Admin settings accessed";
	}

}
