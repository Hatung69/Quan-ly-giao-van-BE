package com.logistics.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.logistics.models.Shipper;

@Repository
public interface ShipperRepository extends JpaRepository<Shipper, Long>  {
	List<Shipper> findAllByOrderByThoiGianKhoiTaoDesc();
}
