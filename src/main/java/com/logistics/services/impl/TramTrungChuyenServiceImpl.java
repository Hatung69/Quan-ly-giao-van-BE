package com.logistics.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logistics.models.TramTrungChuyen;
import com.logistics.models.dto.TramTrungChuyenDTO;
import com.logistics.repositories.TramTrungChuyenRepository;
import com.logistics.services.TramTrungChuyenService;

import net.bytebuddy.utility.RandomString;

@Service
public class TramTrungChuyenServiceImpl implements TramTrungChuyenService {

	@Autowired
	private TramTrungChuyenRepository tramTrungChuyenRepository;

	@Override
	public List<TramTrungChuyen> layDSTramTrungChuyen() {
		List<TramTrungChuyen> dsCacTram = tramTrungChuyenRepository.findAllByOrderByThoiGianKhoiTaoDesc();
//		List<KhachHangDTO> _dsKhachHang = new ArrayList<KhachHangDTO>();
//		dsKhachHang.forEach(kh -> {
//			_dsKhachHang.add(new KhachHangDTO(kh.getId(), kh.getTenKhachHang(), kh.getTenCuaHang(), kh.getSdt(),
//					kh.getDiaChi(), kh.getEmail(), kh.getCmnd(), kh.getSoTaiKhoan(),kh.getDsDonHang().size(), kh.getLoaiKhachHang(),
//					kh.getThoiGianCapNhat()));
//		});
		return dsCacTram;
	}

	@Override
	public TramTrungChuyen layTramTrungChuyenTheoID(Long idTram) {
		TramTrungChuyen _tramTrungChuyen = tramTrungChuyenRepository.findById(idTram)
				.orElseThrow(() -> new RuntimeException("Error: Không tìm thấy trạm!"));
		return _tramTrungChuyen;
	}

	@Override
	public TramTrungChuyen taoMoiTramTrungChuyen(TramTrungChuyenDTO tramTrungChuyenTaoMoi) {
		String maTram = "TTC-" + RandomString.make(10);
		TramTrungChuyen _tramTrungChuyen = new TramTrungChuyen(null, maTram, tramTrungChuyenTaoMoi.getTenTram(),
				tramTrungChuyenTaoMoi.getDiaChi(), tramTrungChuyenTaoMoi.getTrangThai(),
				tramTrungChuyenTaoMoi.getMoTa(), null, null, null, null, null, null);
		return tramTrungChuyenRepository.save(_tramTrungChuyen);
	}

	@Override
	public TramTrungChuyen capNhatTramTrungChuyen(TramTrungChuyenDTO tramTrungChuyenCapNhat, Long idTram) {
		TramTrungChuyen _tramTrungChuyen = tramTrungChuyenRepository.findById(idTram)
				.orElseThrow(() -> new RuntimeException("Error: Không tìm thấy trạm!"));
		_tramTrungChuyen.setTenTram(tramTrungChuyenCapNhat.getTenTram());
		_tramTrungChuyen.setDiaChi(tramTrungChuyenCapNhat.getDiaChi());
		_tramTrungChuyen.setTrangThai(tramTrungChuyenCapNhat.getTrangThai());
		_tramTrungChuyen.setMoTa(tramTrungChuyenCapNhat.getMoTa());

		return tramTrungChuyenRepository.save(_tramTrungChuyen);
	}

	// ------------------ Hàm dựng sẵn của JPA -------------------------
	@Override
	public TramTrungChuyen save(TramTrungChuyen entity) {
		return tramTrungChuyenRepository.save(entity);
	}

	@Override
	public List<TramTrungChuyen> findAll() {
		return tramTrungChuyenRepository.findAll();
	}

	@Override
	public Optional<TramTrungChuyen> findById(Long id) {
		return tramTrungChuyenRepository.findById(id);
	}

	@Override
	public boolean existsById(Long id) {
		return tramTrungChuyenRepository.existsById(id);
	}

	@Override
	public long count() {
		return tramTrungChuyenRepository.count();
	}

	@Override
	public void deleteById(Long id) {
		tramTrungChuyenRepository.deleteById(id);
	}

	@Override
	public void delete(TramTrungChuyen entity) {
		tramTrungChuyenRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		tramTrungChuyenRepository.deleteAll();
	}

}
