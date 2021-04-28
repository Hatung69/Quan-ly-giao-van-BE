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

import com.logistics.models.DonHang;
import com.logistics.models.HangHoa;
import com.logistics.repositories.HangHoaRepository;
import com.logistics.services.DonHangService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class HangHoaController {

	@Autowired
	private HangHoaRepository hangHoaRepository;

	@Autowired
	private DonHangService donHangService;

	// Lấy danh sách hàng hoá
	@GetMapping("/hang-hoa")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<List<HangHoa>> layDSKhachHang() {
		try {
			List<HangHoa> _dsHangHoa = hangHoaRepository.findAll();
			if (_dsHangHoa.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(_dsHangHoa, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Lấy hàng hoá theo ID
	@GetMapping("/hang-hoa/{idHangHoa}")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<HangHoa> layHangHoaTheoID(@PathVariable("idHangHoa") Long idHangHoa) {
		try {
			Optional<HangHoa> _hangHoa = hangHoaRepository.findById(idHangHoa);
			if (!_hangHoa.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(_hangHoa.get(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// Tạo mới hàng hoá
	@PostMapping("/hang-hoa/{idDonHang}")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<HangHoa> taoKhachHang(@RequestBody HangHoa hangHoa,
			@PathVariable("idDonHang") Long idDonHang) {
		try {
			Optional<DonHang> _donHang = donHangService.findById(idDonHang);
			if (!_donHang.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			HangHoa _hangHoa = new HangHoa(null, hangHoa.getTenHang(), hangHoa.getGiaTri(), hangHoa.getSoLuong(),
					hangHoa.getSoLuong(), hangHoa.getMoTa(), _donHang.get());

			return new ResponseEntity<>(hangHoaRepository.save(_hangHoa), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Cập nhật thông tin hàng hoá
	@PutMapping("/hang-hoa/{idHangHoa}")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<HangHoa> capNhatHangHoa(@RequestBody HangHoa hangHoa,
			@PathVariable("idHangHoa") Long idHangHoa) {
		try {
			HangHoa _hangHoa = hangHoaRepository.findById(idHangHoa)
					.orElseThrow(() -> new RuntimeException("Không tìm thấy hàng!"));
			_hangHoa.setTenHang(hangHoa.getTenHang());
			_hangHoa.setGiaTri(hangHoa.getGiaTri());
			_hangHoa.setSoLuong(hangHoa.getSoLuong());
			_hangHoa.setKhoiLuong(hangHoa.getKhoiLuong());
			_hangHoa.setMoTa(hangHoa.getMoTa());
			return new ResponseEntity<>(hangHoaRepository.save(_hangHoa),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Xoá hàng hoá theo ID
	@DeleteMapping("/hang-hoa/{idHangHoa}")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> xoaHangHoa(@PathVariable("idHangHoa") Long idHangHoa) {
		try {
			Optional<HangHoa> _hangHoa = hangHoaRepository.findById(idHangHoa);
			if (!_hangHoa.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			hangHoaRepository.deleteById(idHangHoa);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
