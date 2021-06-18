package com.logistics.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.logistics.models.DonHang;
import com.logistics.models.dto.DonHangDTO;

public interface DonHangService {
	// Lấy danh sách đơn hàng
	public List<DonHangDTO> layDSDonHang();

	// Lấy đơn hàng theo ID
	public DonHangDTO layDonHangTheoID(Long idDonHang);

	List<DonHangDTO> timeKiemNhanh(String keySearch);

	List<DonHangDTO> thongKeTheoThoiGian(Date batDau, Date ketThuc);

	// Tạo mới đơn hàng
	public DonHang taoMoiDonHang(DonHangDTO donHangTaoMoi, MultipartFile fileAnhDinhKem, Long idTram);

	// Cập nhật đơn hàng
	public DonHang capNhatDonHang(DonHangDTO donHangCapNhat, MultipartFile fileAnhDinhKem, Long idDonHang);

	DonHangDTO layDonHangTheoMaDonHang(String maDonHang);

	public Map<String, Integer> tongThongKe();

// ------------------ Hàm dựng sẵn của JPA -------------------------
	void deleteAll();

	void deleteAllById(List<Long> ids);

	void delete(DonHang entity);

	void deleteById(Long id);

	long count();

	boolean existsById(Long id);

	Optional<DonHang> findById(Long id);

	public List<DonHang> findAllById(Iterable<Long> ids);

	List<DonHang> findAll();

	DonHang save(DonHang entity);

}
