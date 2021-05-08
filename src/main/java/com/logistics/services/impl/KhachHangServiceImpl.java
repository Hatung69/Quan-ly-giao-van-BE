package com.logistics.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logistics.models.KhachHang;
import com.logistics.models.dto.KhachHangDTO;
import com.logistics.repositories.KhachHangRepository;
import com.logistics.services.KhachHangService;

@Service
public class KhachHangServiceImpl implements KhachHangService {

	@Autowired
	private KhachHangRepository khachHangRepository;

	@Override
	public List<KhachHangDTO> layDSKhachHang() {
		List<KhachHang> dsKhachHang = khachHangRepository.findAllByOrderByThoiGianKhoiTaoDesc();
		List<KhachHangDTO> _dsKhachHang = new ArrayList<KhachHangDTO>();
		dsKhachHang.forEach(kh -> {
			_dsKhachHang.add(new KhachHangDTO(kh.getId(), kh.getTenKhachHang(), kh.getTenCuaHang(), kh.getSdt(),
					kh.getDiaChi(), kh.getEmail(), kh.getCmnd(), kh.getSoTaiKhoan(), kh.getDsDonHang().size(),
					kh.getLoaiKhachHang(), kh.getThoiGianCapNhat()));
		});
		return _dsKhachHang;
	}

	@Override
	public List<KhachHangDTO> timKiemKH(String keySearch) {
		List<KhachHang> dsKhachHang = khachHangRepository.timeKiemKhachHang(keySearch);
		List<KhachHangDTO> _dsKhachHang = new ArrayList<KhachHangDTO>();
		dsKhachHang.forEach(kh -> {
			_dsKhachHang.add(new KhachHangDTO(kh.getId(), kh.getTenKhachHang(), kh.getTenCuaHang(), kh.getSdt(),
					kh.getDiaChi(), kh.getEmail(), kh.getCmnd(), kh.getSoTaiKhoan(), kh.getDsDonHang().size(),
					kh.getLoaiKhachHang(), kh.getThoiGianCapNhat()));
		});
		return _dsKhachHang;
	}

	@Override
	public KhachHang layKhachHangTheoID(Long idKhachHang) {
		KhachHang _khachHang = khachHangRepository.findById(idKhachHang)
				.orElseThrow(() -> new RuntimeException("Error: Không tìm thấy Khách hàng!"));
		return _khachHang;
	}

	@Override
	public KhachHang taoMoiKhachHang(KhachHangDTO khachHangTaoMoi) {
		KhachHang _khachHang = new KhachHang(null, khachHangTaoMoi.getTenKhachHang(), khachHangTaoMoi.getTenCuaHang(),
				khachHangTaoMoi.getSdt(), khachHangTaoMoi.getDiaChi(), khachHangTaoMoi.getEmail(),
				khachHangTaoMoi.getCmnd(), khachHangTaoMoi.getSoTaiKhoan(), khachHangTaoMoi.getLoaiKhachHang(), null,
				null, null, null, null);
		return khachHangRepository.save(_khachHang);
	}

	@Override
	public KhachHang capNhatKhachHang(KhachHangDTO khachHangCapNhat, Long idKhachHang) {
		KhachHang _khaHang = khachHangRepository.findById(idKhachHang)
				.orElseThrow(() -> new RuntimeException("Error: Không tìm thấy Khách hàng này!"));
		_khaHang.setTenKhachHang(khachHangCapNhat.getTenKhachHang());
		_khaHang.setTenCuaHang(khachHangCapNhat.getTenCuaHang());
		_khaHang.setSdt(khachHangCapNhat.getSdt());
		_khaHang.setDiaChi(khachHangCapNhat.getDiaChi());
		_khaHang.setEmail(khachHangCapNhat.getEmail());
		_khaHang.setCmnd(khachHangCapNhat.getCmnd());
		_khaHang.setSoTaiKhoan(khachHangCapNhat.getSoTaiKhoan());
		_khaHang.setLoaiKhachHang(khachHangCapNhat.getLoaiKhachHang());

		return khachHangRepository.save(_khaHang);
	}

	// ------------------ Hàm dựng sẵn của JPA -------------------------
	@Override
	public KhachHang save(KhachHang entity) {
		return khachHangRepository.save(entity);
	}

	@Override
	public List<KhachHang> findAll() {
		return khachHangRepository.findAll();
	}

	@Override
	public Optional<KhachHang> findById(Long id) {
		return khachHangRepository.findById(id);
	}

	@Override
	public boolean existsById(Long id) {
		return khachHangRepository.existsById(id);
	}

	@Override
	public long count() {
		return khachHangRepository.count();
	}

	@Override
	public void deleteById(Long id) {
		khachHangRepository.deleteById(id);
	}

	@Override
	public void delete(KhachHang entity) {
		khachHangRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		khachHangRepository.deleteAll();
	}

}
