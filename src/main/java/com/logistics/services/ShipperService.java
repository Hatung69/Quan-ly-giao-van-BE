package com.logistics.services;

import java.util.List;
import java.util.Optional;

import com.logistics.models.Shipper;
import com.logistics.models.dto.ShipperDTO;

public interface ShipperService {
	// Lấy danh sách shipper
	public List<ShipperDTO> layDSShipper();
	
	List<ShipperDTO> timKiemShipper(String keySearch);

	// Lấy shipper theo ID
	public Shipper layShipperTheoID(Long idShipper);

	// Tạo mới shipper
	public Shipper taoMoiShipper(ShipperDTO shipperTaoMoi);

	// Cập nhật shipper
	public Shipper capNhatShipper(ShipperDTO shipperCapNhat, Long idShipper);

// ------------------ Hàm dựng sẵn của JPA -------------------------
	void deleteAll();

	void delete(Shipper entity);

	void deleteById(Long id);

	long count();

	boolean existsById(Long id);

	Optional<Shipper> findById(Long id);

	List<Shipper> findAll();

	Shipper save(Shipper entity);
}
