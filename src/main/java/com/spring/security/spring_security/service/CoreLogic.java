package com.spring.security.spring_security.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class CoreLogic {
	
	@PreAuthorize("hasRole('ADMIN')")
	public String adminLogic() {
		return "Admin logic executed";
	}
	
	@PreAuthorize("hasRole('USER')")
	public String userLogic() {
		return "User logic executed";
	}

}
