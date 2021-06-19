package com.logistics.services;

import java.util.List;
import java.util.Optional;

import com.logistics.models.TramTrungChuyen;
import com.logistics.models.dto.TramTrungChuyenDTO;

public interface TramTrungChuyenService {
	
	// Lấy danh sách trạm trung chuyển
	public List<TramTrungChuyen> layDSTramTrungChuyen();

	// Lấy trạm trung chuyển theo ID
	public TramTrungChuyen layTramTrungChuyenTheoID(Long idTram);

	// Tạo mới trạm trung chuyển
	public TramTrungChuyen taoMoiTramTrungChuyen(TramTrungChuyenDTO tramTrungChuyenTaoMoi);

	// Cập nhật trạm trung chuyển
	public TramTrungChuyen capNhatTramTrungChuyen(TramTrungChuyenDTO tramTrungChuyenCapNhat,Long idTram);

// ------------------ Hàm dựng sẵn của JPA -------------------------
	void deleteAll();

	void delete(TramTrungChuyen entity);

	void deleteById(Long id);

	long count();

	boolean existsById(Long id);

	Optional<TramTrungChuyen> findById(Long id);

	List<TramTrungChuyen> findAll();

	TramTrungChuyen save(TramTrungChuyen entity);
}
