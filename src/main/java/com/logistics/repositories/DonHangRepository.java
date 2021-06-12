package com.logistics.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.logistics.models.DonHang;

@Repository
public interface DonHangRepository extends JpaRepository<DonHang, Long> {
	List<DonHang> findAllByOrderByThoiGianKhoiTaoDesc();

	Optional<DonHang> findByMaDonHang(String maDonHang);

	@Query(value = "SELECT dh.* FROM don_hang dh, khach_hang kh WHERE CONCAT(dh.ma_don_hang,dh.ten_nguoi_nhan, dh.sdt_nguoi_nhan, kh.ten_khach_hang, kh.sdt) LIKE %?1% AND dh.khach_hang_id=kh.id", nativeQuery = true)
	List<DonHang> timeKiemNhanh(String keySearch);

	@Query(value = "SELECT (SELECT COUNT(*)FROM tram_trung_chuyen) AS tong_don_hang, (SELECT COUNT(*)FROM khach_hang) AS tong_khach_hang, (SELECT COUNT(*)FROM shipper)AS tong_shipper, (SELECT COUNT(*)FROM nhan_vien)AS tong_nhan_vien", nativeQuery = true)
	List<Integer[]> tongThongKe();

	List<DonHang>  findByThoiGianKhoiTaoBetween(Date batDau, Date ketThuc);
}
