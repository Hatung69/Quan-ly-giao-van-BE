package com.logistics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.logistics.models.KhachHang;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Long>  {
	
}