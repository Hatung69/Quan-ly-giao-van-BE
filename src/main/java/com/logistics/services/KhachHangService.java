package com.logistics.services;

import java.util.List;
import java.util.Optional;

import com.logistics.models.KhachHang;
import com.logistics.models.dto.KhachHangDTO;

public interface KhachHangService {
	// Lấy danh sách khách hàng
	public List<KhachHangDTO> layDSKhachHang();
	
	List<KhachHangDTO> timKiemKH(String keySearch);

	// Lấy khách hàng theo ID
	public KhachHang layKhachHangTheoID(Long idKhachHang);

	// Tạo mới khách hàng
	public KhachHang taoMoiKhachHang(KhachHangDTO khachHangTaoMoi);

	// Cập nhật khách hàng
	public KhachHang capNhatKhachHang(KhachHangDTO khachHangCapNhat,Long idKhachHang);

// ------------------ Hàm dựng sẵn của JPA -------------------------
	void deleteAll();

	void delete(KhachHang entity);

	void deleteById(Long id);

	long count();

	boolean existsById(Long id);

	Optional<KhachHang> findById(Long id);

	List<KhachHang> findAll();

	KhachHang save(KhachHang entity);

}
