package com.logistics.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.logistics.models.NhanVien;
import com.logistics.models.TaiKhoan;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Long> {
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	Optional<NhanVien> findByTaiKhoan(TaiKhoan taiKhoan);

	@Query(value = "SELECT * FROM nhan_vien WHERE tai_khoan_id = ?1", nativeQuery = true)
	NhanVien findNhanVienByTaiKhoanId(Long idTaiKhoan);
}
