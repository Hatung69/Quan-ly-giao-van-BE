package com.logistics.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.logistics.models.DonHang;

@Repository
public interface DonHangRepository extends JpaRepository<DonHang, Long>{
	List<DonHang> findAllByOrderByThoiGianKhoiTaoDesc();
	Optional<DonHang> findByMaDonHang(String maDonHang);
}
