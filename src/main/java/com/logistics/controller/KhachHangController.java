package com.logistics.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logistics.models.KhachHang;
import com.logistics.models.dto.KhachHangDTO;
import com.logistics.services.KhachHangService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class KhachHangController {

	@Autowired
	private KhachHangService khachHangService;

	// Lấy danh sách khách hàng
	@GetMapping("/khach-hang")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<List<KhachHangDTO>> layDSKhachHang() {
		try {
			List<KhachHangDTO> _dsKhachHang = khachHangService.layDSKhachHang();
			if (_dsKhachHang.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(_dsKhachHang, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Lấy khách hàng theo ID
	@GetMapping("/khach-hang/{idKhachHang}")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<KhachHang> layKhachHangTheoID(@PathVariable("idKhachHang") Long idKhachHang) {
		try {
			KhachHang _khachHang = khachHangService.layKhachHangTheoID(idKhachHang);
			if (_khachHang == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(_khachHang, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// Tạo mới khach hàng
	@PostMapping("/khach-hang")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<KhachHang> taoKhachHang(@RequestBody KhachHangDTO khachHangTaoMoi) {
		try {
			return new ResponseEntity<>(khachHangService.taoMoiKhachHang(khachHangTaoMoi), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Cập nhật thông tin khách hàng
	@PutMapping("/khach-hang/{idKhachHang}")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<KhachHang> capNhatKhachHang(@RequestBody KhachHangDTO khachHangCapNhat,
			@PathVariable("idKhachHang") Long idKhachHang) {
		try {
			return new ResponseEntity<>(khachHangService.capNhatKhachHang(khachHangCapNhat, idKhachHang),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Xoá nhân viên theo ID
	@DeleteMapping("/khach-hang/{idKhachHang}")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> xoaKhachHang(@PathVariable("idKhachHang") Long idKhachHang) {
		try {
			Optional<KhachHang> _khachHang = khachHangService.findById(idKhachHang);
			if (!_khachHang.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			khachHangService.deleteById(idKhachHang);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
