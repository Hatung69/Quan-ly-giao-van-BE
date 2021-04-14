package com.logistics.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.logistics.models.EQuyenHan;
import com.logistics.models.NhanVien;
import com.logistics.models.QuyenHan;
import com.logistics.models.dto.NhanVienDTO;
import com.logistics.repositories.QuyenHanRepository;
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
//		List<NhanVienDTO> _dsNhanVien = new ArrayList<NhanVienDTO>();
//		try {
//			List<NhanVien> dsNhanVien = nhanVienService.findAll();
//			if (dsNhanVien.isEmpty()) {
//				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
//			}
//			dsNhanVien.forEach(nv -> {
//				_dsNhanVien.add(new NhanVienDTO(nv.getId(), nv.getHoTen(), nv.getSdt(), nv.getGioiTinh(),
//						nv.getNgaySinh(), nv.getDiaChi(), nv.getTrangThai(), nv.getLanCuoiDangNhap(), null));
//			});
//			return new ResponseEntity<>(_dsNhanVien, HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
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

//		final Optional<NhanVien> _nhanVien = nhanVienService.findById(idNhanVien);
//		try {
//			if (_nhanVien.isPresent()) {
//				_nhanVien.get().setAvatar(decompressBytes(_nhanVien.get().getAvatar()));
//				return new ResponseEntity<>(_nhanVien.get(), HttpStatus.OK);
//			} else {
//				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//			}
//		} catch (Exception e) {
//			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
	}

	// Tạo mới nhân viên
	@PostMapping(value = "/nhan-vien", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<NhanVien> taoNhanVien(@RequestParam("infoNhanVien") String nhanVien,
			@RequestParam(name = "avatarFile", required = false) MultipartFile avatarFile) {
		try {
			Gson gson = new Gson();
			NhanVienDTO _nhanVienDTO = gson.fromJson(nhanVien, NhanVienDTO.class);
			return new ResponseEntity<>(nhanVienService.taoMoiNhanVien(_nhanVienDTO, avatarFile), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
//		try {
//			Gson gson = new Gson();
//			NhanVienDTO nhanVienDTO = gson.fromJson(nhanVien, NhanVienDTO.class);
//			nhanVienDTO.getTaiKhoan().setPassword(encoder.encode(nhanVienDTO.getTaiKhoan().getPassword()));
//			/*
//			 * Tài khoản chưa có ID nhưng sẽ tự insert vì CascadeType.PERSIST, chưa có quyền
//			 * nên h phải set
//			 */
//			Set<QuyenHan> dsQuyen = new HashSet<>();
//			QuyenHan quyenHan = quyenHanRepository.findByTenQuyen(EQuyenHan.ROLE_EMPLOYEE)
//					.orElseThrow(() -> new RuntimeException("Error: Không tìm thấy Quyền!"));
//			dsQuyen.add(quyenHan);
//			nhanVienDTO.getTaiKhoan().setQuyenHan(dsQuyen);
//			NhanVien _nhanVien = new NhanVien(null, nhanVienDTO.getHoTen(), nhanVienDTO.getSdt(),
//					nhanVienDTO.getGioiTinh(), nhanVienDTO.getNgaySinh(), nhanVienDTO.getDiaChi(),
//					nhanVienDTO.getTrangThai(), compressBytes(avatarFile.getBytes()), new Date(), null, null,
//					nhanVienDTO.getTaiKhoan());
//
//			return new ResponseEntity<>(nhanVienService.save(_nhanVien), HttpStatus.CREATED);
//		} catch (Exception e) {
//			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
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
//		final Optional<NhanVien> _nhanVien = nhanVienService.findById(idNhanVien);
//		if (_nhanVien.isPresent()) {
//			try {
//				Gson gson = new Gson();
//				NhanVienDTO nhanVienDTO = gson.fromJson(nhanVien, NhanVienDTO.class);
//				if (avatarFile == null) {
//					System.out.println("File ko cần update");
//					_nhanVien.get().setHoTen(nhanVienDTO.getHoTen());
//					_nhanVien.get().setSdt(nhanVienDTO.getSdt());
//					_nhanVien.get().setGioiTinh(nhanVienDTO.getGioiTinh());
//					_nhanVien.get().setNgaySinh(nhanVienDTO.getNgaySinh());
//					_nhanVien.get().setDiaChi(nhanVienDTO.getDiaChi());
//					_nhanVien.get().setTrangThai(nhanVienDTO.getTrangThai());
//				} else {
//					_nhanVien.get().setHoTen(nhanVienDTO.getHoTen());
//					_nhanVien.get().setSdt(nhanVienDTO.getSdt());
//					_nhanVien.get().setGioiTinh(nhanVienDTO.getGioiTinh());
//					_nhanVien.get().setNgaySinh(nhanVienDTO.getNgaySinh());
//					_nhanVien.get().setDiaChi(nhanVienDTO.getDiaChi());
//					_nhanVien.get().setTrangThai(nhanVienDTO.getTrangThai());
//					_nhanVien.get().setAvatar(compressBytes(avatarFile.getBytes()));
//				}
//				return new ResponseEntity<>(nhanVienService.save(_nhanVien.get()), HttpStatus.OK);
//			} catch (Exception e) {
//				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//			}
//		} else {
//			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//		}

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
