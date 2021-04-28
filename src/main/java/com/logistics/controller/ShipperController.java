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

import com.logistics.models.Shipper;
import com.logistics.models.dto.ShipperDTO;
import com.logistics.services.ShipperService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ShipperController {

	@Autowired
	private ShipperService shipperService;

	// Lấy danh sách shipper
	@GetMapping("/shipper")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<List<ShipperDTO>> layDSShipper() {
		try {
			List<ShipperDTO> _dsShipper = shipperService.layDSShipper();
			if (_dsShipper.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(_dsShipper, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Lấy shipper theo ID
	@GetMapping("/shipper/{idShipper}")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<Shipper> layShipperTheoID(@PathVariable("idShipper") Long idShipper) {
		try {
			Shipper _shipper = shipperService.layShipperTheoID(idShipper);
			if (_shipper == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(_shipper, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// Tạo mới shipper
	@PostMapping("/shipper")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<Shipper> taoShipper(@RequestBody ShipperDTO shipperTaoMoi) {
		try {
			return new ResponseEntity<>(shipperService.taoMoiShipper(shipperTaoMoi), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Cập nhật thông tin Shipper
	@PutMapping("/shipper/{idShipper}")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<Shipper> capNhatShipper(@RequestBody ShipperDTO shipperCapNhat,
			@PathVariable("idShipper") Long idKhachHang) {
		try {
			return new ResponseEntity<>(shipperService.capNhatShipper(shipperCapNhat, idKhachHang),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Xoá shipper theo ID
	@DeleteMapping("/shipper/{idShipper}")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> xoaShipper(@PathVariable("idShipper") Long idShipper) {
		try {
			Optional<Shipper> _khachHang = shipperService.findById(idShipper);
			if (!_khachHang.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			shipperService.deleteById(idShipper);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
