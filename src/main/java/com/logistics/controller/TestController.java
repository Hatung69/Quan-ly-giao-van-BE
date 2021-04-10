package com.logistics.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")

public class TestController {
	@GetMapping("/all")
	public String allAccess() {
		return "Nội dung này là cho khách vãng lai có thể có xem";
	}

	@GetMapping("/employee")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public String userAccess() {
		return "Nội dung cho nhân viên";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Nội dung cho Admin";
	}
}
