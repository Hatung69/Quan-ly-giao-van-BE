package com.logistics.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logistics.models.EQuyenHan;
import com.logistics.models.QuyenHan;
import com.logistics.models.TaiKhoan;
import com.logistics.models.dto.SigninRequest;
import com.logistics.models.dto.SignupRequest;
import com.logistics.payload.response.JwtResponse;
import com.logistics.payload.response.MessageResponse;
import com.logistics.repositories.QuyenHanRepository;
import com.logistics.repositories.TaiKhoanRepository;
import com.logistics.security.jwt.JwtUtils;
import com.logistics.security.servicse.TaiKhoanDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	TaiKhoanRepository taiKhoanRepository;

	@Autowired
	QuyenHanRepository quyenHanRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	// Đăng nhập
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody SigninRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		TaiKhoanDetailsImpl taiKhoanDetails = (TaiKhoanDetailsImpl) authentication.getPrincipal();
		List<String> quyenHan = taiKhoanDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, taiKhoanDetails.getId(), taiKhoanDetails.getUsername(),
				taiKhoanDetails.getEmail(), quyenHan));
	}

	// Đăng ký
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (taiKhoanRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username đã tồn tại!"));
		}

		if (taiKhoanRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email đã được sử dụng"));
		}

		// Create new user's account
		TaiKhoan user = new TaiKhoan(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getQuyenHan();
		Set<QuyenHan> roles = new HashSet<>();

		if (strRoles == null || strRoles.isEmpty()) {
			QuyenHan employeeRole = quyenHanRepository.findByTenQuyen(EQuyenHan.ROLE_EMPLOYEE)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(employeeRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					QuyenHan adminRole = quyenHanRepository.findByTenQuyen(EQuyenHan.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);
					break;
				default:
					QuyenHan employeeRole = quyenHanRepository.findByTenQuyen(EQuyenHan.ROLE_EMPLOYEE)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(employeeRole);
				}
			});
		}

		user.setQuyenHan(roles);
		taiKhoanRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("Đăng ký tài khoản thành công!"));
	}
}
