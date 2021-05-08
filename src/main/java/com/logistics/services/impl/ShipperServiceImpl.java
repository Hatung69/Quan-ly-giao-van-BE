package com.logistics.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logistics.models.KhachHang;
import com.logistics.models.Shipper;
import com.logistics.models.dto.KhachHangDTO;
import com.logistics.models.dto.ShipperDTO;
import com.logistics.repositories.ShipperRepository;
import com.logistics.services.ShipperService;

@Service
public class ShipperServiceImpl implements ShipperService {

	@Autowired
	private ShipperRepository shipperRepository;

	@Override
	public List<ShipperDTO> layDSShipper() {
		List<Shipper> dsShipper = shipperRepository.findAllByOrderByThoiGianKhoiTaoDesc();
		List<ShipperDTO> _dsShipper = new ArrayList<ShipperDTO>();
		dsShipper.forEach(sp -> {
			_dsShipper.add(new ShipperDTO(sp.getId(), sp.getHoTen(), sp.getSdt(), sp.getDiaChi(), sp.getEmail(),
					sp.getCmnd(), 0, sp.getTrangThai(),sp.getDsDonHang().size(), sp.getThoiGianCapNhat()));
		});
		return _dsShipper;
	}

	@Override
	public List<ShipperDTO> timKiemShipper(String keySearch) {
		List<Shipper> dsShipper = shipperRepository.timeKiemShipper(keySearch);
		List<ShipperDTO> _dsShipper = new ArrayList<ShipperDTO>();
		dsShipper.forEach(sp -> {
			_dsShipper.add(new ShipperDTO(sp.getId(), sp.getHoTen(), sp.getSdt(), sp.getDiaChi(), sp.getEmail(),
					sp.getCmnd(), 0, sp.getTrangThai(),sp.getDsDonHang().size(), sp.getThoiGianCapNhat()));
		});
		return _dsShipper;
	}

	@Override
	public Shipper layShipperTheoID(Long idShipper) {
		Shipper _shipper = shipperRepository.findById(idShipper)
				.orElseThrow(() -> new RuntimeException("Error: Không tìm thấy Shipper!"));
		return _shipper;
	}

	@Override
	public Shipper taoMoiShipper(ShipperDTO shipperTaoMoi) {
		Shipper _shipper = new Shipper(null, shipperTaoMoi.getHoTen(), shipperTaoMoi.getSdt(),
				shipperTaoMoi.getDiaChi(), shipperTaoMoi.getEmail(), shipperTaoMoi.getCmnd(),
				shipperTaoMoi.getTrangThai(), null, null, null, null, null);
		return shipperRepository.save(_shipper);
	}

	@Override
	public Shipper capNhatShipper(ShipperDTO shipperCapNhat, Long idShipper) {
		Shipper _shipper = shipperRepository.findById(idShipper)
				.orElseThrow(() -> new RuntimeException("Error: Không tìm thấy Quyền!"));
		_shipper.setHoTen(shipperCapNhat.getHoTen());
		_shipper.setDiaChi(shipperCapNhat.getDiaChi());
		_shipper.setSdt(shipperCapNhat.getSdt());
		_shipper.setCmnd(shipperCapNhat.getCmnd());
		_shipper.setEmail(shipperCapNhat.getEmail());
		_shipper.setTrangThai(shipperCapNhat.getTrangThai());

		return shipperRepository.save(_shipper);
	}

	// ------------------ Hàm dựng sẵn của JPA -------------------------
	@Override
	public Shipper save(Shipper entity) {
		return shipperRepository.save(entity);
	}

	@Override
	public List<Shipper> findAll() {
		return shipperRepository.findAll();
	}

	@Override
	public Optional<Shipper> findById(Long id) {
		return shipperRepository.findById(id);
	}

	@Override
	public boolean existsById(Long id) {
		return shipperRepository.existsById(id);
	}

	@Override
	public long count() {
		return shipperRepository.count();
	}

	@Override
	public void deleteById(Long id) {
		shipperRepository.deleteById(id);
	}

	@Override
	public void delete(Shipper entity) {
		shipperRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		shipperRepository.deleteAll();
	}

}
