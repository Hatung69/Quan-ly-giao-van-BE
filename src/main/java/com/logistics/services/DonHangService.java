package com.logistics.services;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.logistics.models.DonHang;
import com.logistics.models.dto.DonHangDTO;

public interface DonHangService {
	// Lấy danh sách đơn hàng
	public List<DonHangDTO> layDSDonHang();

	// Lấy đơn hàng theo ID
	public DonHangDTO layDonHangTheoID(Long idDonHang);

	// Tạo mới đơn hàng
	public DonHang taoMoiDonHang(DonHangDTO donHangTaoMoi, MultipartFile fileAnhDinhKem);

	// Cập nhật đơn hàng
	public DonHang capNhatDonHang(DonHangDTO donHangCapNhat, MultipartFile fileAnhDinhKem, Long idDonHang);
	
	DonHangDTO layDonHangTheoMaDonHang(String maDonHang);

// ------------------ Hàm dựng sẵn của JPA -------------------------
	void deleteAll();

	void delete(DonHang entity);

	void deleteById(Long id);

	long count();

	boolean existsById(Long id);

	Optional<DonHang> findById(Long id);

	List<DonHang> findAll();

	DonHang save(DonHang entity);

	
}
