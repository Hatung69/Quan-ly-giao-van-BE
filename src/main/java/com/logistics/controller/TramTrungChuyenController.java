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

import com.logistics.models.TramTrungChuyen;
import com.logistics.models.dto.TramTrungChuyenDTO;
import com.logistics.services.TramTrungChuyenService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TramTrungChuyenController {

	@Autowired
	private TramTrungChuyenService tramTrungChuyenService;
	
	// Lấy danh sách trạm
	@GetMapping("/tram-trung-chuyen")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<TramTrungChuyen>> layDSTramTrungChuyen() {
		try {
			List<TramTrungChuyen> _dsTram = tramTrungChuyenService.layDSTramTrungChuyen();
			if (_dsTram.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(_dsTram, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Lấy trạm theo ID
	@GetMapping("/tram-trung-chuyen/{idTram}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<TramTrungChuyen> layKhachHangTheoID(@PathVariable("idTram") Long idTram) {
		try {
			TramTrungChuyen _tramTrungChuyen = tramTrungChuyenService.layTramTrungChuyenTheoID(idTram);
			if (_tramTrungChuyen == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(_tramTrungChuyen, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// Tạo mới trạm
	@PostMapping("/tram-trung-chuyen")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<TramTrungChuyen> taoKhachHang(@RequestBody TramTrungChuyenDTO tramTaoMoi) {
		try {
			return new ResponseEntity<>(tramTrungChuyenService.taoMoiTramTrungChuyen(tramTaoMoi), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Cập nhật thông tin Trạm
	@PutMapping("/tram-trung-chuyen/{idTram}")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<TramTrungChuyen> capNhatKhachHang(@RequestBody TramTrungChuyenDTO tramCapNhat,
			@PathVariable("idTram") Long idTram) {
		try {
			return new ResponseEntity<>(tramTrungChuyenService.capNhatTramTrungChuyen(tramCapNhat, idTram),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Xoá nhân trạm theo ID
	@DeleteMapping("/tram-trung-chuyen/{idTram}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> xoaTramTheoID(@PathVariable("idTram") Long idTram) {
		try {
			Optional<TramTrungChuyen> _tram = tramTrungChuyenService.findById(idTram);
			if (!_tram.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			tramTrungChuyenService.deleteById(idTram);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
