package com.logistics.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.logistics.models.KhachHang;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Long> {
	List<KhachHang> findAllByOrderByThoiGianKhoiTaoDesc();
	Optional<KhachHang> findBySdt(String sdt);
}
