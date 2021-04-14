package com.logistics.services;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.logistics.models.NhanVien;
import com.logistics.models.dto.NhanVienDTO;

public interface NhanVienService {
	// Lấy danh sách nhân viên
	public List<NhanVienDTO> layDSNhanVien();

	// Lấy nhân viên theo ID
	public NhanVien layNhanVienTheoID(Long idNhanVien);

	// Tạo mới nhân viên
	public NhanVien taoMoiNhanVien(NhanVienDTO nhanVienTaoMoi, MultipartFile avatarFile);

	// Cập nhật nhân viên
	public NhanVien capNhatNhanVien(NhanVienDTO nhanVienTaoMoi, MultipartFile avatarFile, Long idNhanVien);

// ------------------ Hàm dựng sẵn của JPA -------------------------
	void deleteAll();

	void delete(NhanVien entity);

	void deleteById(Long id);

	long count();

	boolean existsById(Long id);

	Optional<NhanVien> findById(Long id);

	List<NhanVien> findAll();

	NhanVien save(NhanVien entity);
}
