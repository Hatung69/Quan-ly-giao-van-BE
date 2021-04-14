package com.logistics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.logistics.models.NhanVien;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Long>  {
}
