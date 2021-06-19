package com.logistics.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.logistics.models.Shipper;

@Repository
public interface ShipperRepository extends JpaRepository<Shipper, Long>  {
	List<Shipper> findAllByOrderByThoiGianKhoiTaoDesc();
	
	@Query(value = "SELECT * FROM shipper WHERE CONCAT(ho_ten,sdt,dia_chi) LIKE %?1%", nativeQuery = true)
	List<Shipper> timeKiemShipper(String keySearch);
}
