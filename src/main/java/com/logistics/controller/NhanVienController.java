package com.logistics.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.logistics.models.NhanVien;
import com.logistics.models.TramTrungChuyen;
import com.logistics.models.dto.NhanVienDTO;
import com.logistics.models.dto.TramTrungChuyenDTO;
import com.logistics.services.NhanVienService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class NhanVienController {

	@Autowired
	private NhanVienService nhanVienService;

	// Lấy danh sách nhân viên
	@GetMapping("/nhan-vien")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<NhanVienDTO>> layDSNhanvien() {
		try {
			List<NhanVienDTO> _dsNhanVien = nhanVienService.layDSNhanVien();
			if (_dsNhanVien.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(_dsNhanVien, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Lấy nhân viên theo ID
	@GetMapping("/nhan-vien/{idNhanVien}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<NhanVien> layNhanVienTheoID(@PathVariable("idNhanVien") Long idNhanVien) {
		try {
			NhanVien _nhanVien = nhanVienService.layNhanVienTheoID(idNhanVien);
			if (_nhanVien == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(_nhanVien, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// Lấy ID Trạm trung chuyển từ nhân viên theo Id Tài khoản
	@GetMapping("/nhan-vien/tai-khoan/{idTaiKhoan}")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<TramTrungChuyenDTO> layIDTramTTCTheoIDTaiKHoan(@PathVariable("idTaiKhoan") Long idTaiKhoan) {
		try {
			NhanVien _nhanVien = nhanVienService.layNhanVienTheoIDAcc(idTaiKhoan);
			if (_nhanVien == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			TramTrungChuyenDTO _chuyenDTO = new TramTrungChuyenDTO(_nhanVien.getTramTrungChuyen().getId(),
					_nhanVien.getTramTrungChuyen().getMaTram(), _nhanVien.getTramTrungChuyen().getTenTram(),
					_nhanVien.getTramTrungChuyen().getDiaChi(), _nhanVien.getTramTrungChuyen().getSdt(),
					_nhanVien.getTramTrungChuyen().getTrangThai(), _nhanVien.getTramTrungChuyen().getMoTa(),
					_nhanVien.getTramTrungChuyen().getThoiGianKhoiTao());
			return new ResponseEntity<>(_chuyenDTO, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// Lấy nhân viên theo Id Tài khoản
	@GetMapping("/nhan-vien/tai-khoan-id/{idTaiKhoan}")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<NhanVien> layNhanVienIDTaiKHoan(@PathVariable("idTaiKhoan") Long idTaiKhoan) {
		try {
			NhanVien _nhanVien = nhanVienService.layNhanVienTheoIDAcc(idTaiKhoan);
			if (_nhanVien == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(_nhanVien, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Tạo mới nhân viên
	@PostMapping(value = "/nhan-vien", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<NhanVien> taoNhanVien(@RequestParam("infoNhanVien") String nhanVien,
			@RequestParam(name = "avatarFile", required = false) MultipartFile avatarFile) {
		try {
			System.out.println(nhanVien);
			Gson gson = new Gson();
			NhanVienDTO _nhanVienDTO = gson.fromJson(nhanVien, NhanVienDTO.class);
			return new ResponseEntity<>(nhanVienService.taoMoiNhanVien(_nhanVienDTO, avatarFile), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Cập nhật thông tin nhân viên
	@PutMapping(value = "/nhan-vien/{idNhanVien}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<NhanVien> capNhatNhanVien(@RequestParam("infoNhanVien") String nhanVien,
			@RequestParam(name = "avatarFile", required = false) MultipartFile avatarFile,
			@PathVariable("idNhanVien") Long idNhanVien) {
		try {
			Gson gson = new Gson();

			NhanVienDTO _nhanVienDTO = gson.fromJson(nhanVien, NhanVienDTO.class);

			return new ResponseEntity<>(nhanVienService.capNhatNhanVien(_nhanVienDTO, avatarFile, idNhanVien),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Xoá nhân viên theo ID
	@DeleteMapping("/nhan-vien/{idNhanVien}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> xoaNhanVien(@PathVariable("idNhanVien") Long idNhanVien) {
		try {
			Optional<NhanVien> nhanVienTemp = nhanVienService.findById(idNhanVien);
			if (!nhanVienTemp.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			nhanVienService.deleteById(idNhanVien);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
