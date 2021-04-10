package com.logistics.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.logistics.models.EQuyenHan;
import com.logistics.models.QuyenHan;

@Repository
public interface QuyenHanRepository extends JpaRepository<QuyenHan, Integer> {
	
	Optional<QuyenHan> findByTenQuyen(EQuyenHan tenQuyen);
}
