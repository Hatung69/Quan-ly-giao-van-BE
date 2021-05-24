package com.logistics.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.logistics.models.TramTrungChuyen;

@Repository
public interface TramTrungChuyenRepository extends JpaRepository<TramTrungChuyen, Long>{
	List<TramTrungChuyen> findAllByOrderByThoiGianKhoiTaoDesc();
	Optional<TramTrungChuyen> findByMaTram(String maTram);
}
