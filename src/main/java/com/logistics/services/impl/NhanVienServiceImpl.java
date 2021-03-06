package com.logistics.services.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.logistics.models.NhanVien;
import com.logistics.models.QuyenHan;
import com.logistics.models.TramTrungChuyen;
import com.logistics.models.dto.NhanVienDTO;
import com.logistics.models.enu.EQuyenHan;
import com.logistics.repositories.NhanVienRepository;
import com.logistics.repositories.QuyenHanRepository;
import com.logistics.repositories.TramTrungChuyenRepository;
import com.logistics.services.NhanVienService;

@Service
public class NhanVienServiceImpl implements NhanVienService {

	@Autowired
	private NhanVienRepository nhanVienRepository;
	@Autowired
	private TramTrungChuyenRepository tramTrungChuyenRepository;
	@Autowired
	private QuyenHanRepository quyenHanRepository;
	@Autowired
	private PasswordEncoder encoder;

	@Override
	public List<NhanVienDTO> layDSNhanVien() {
		List<NhanVien> dsNhanVien = nhanVienRepository.findAll();
		List<NhanVienDTO> _dsNhanVien = new ArrayList<NhanVienDTO>();
		dsNhanVien.forEach(nv -> {
			_dsNhanVien.add(new NhanVienDTO(nv.getId(), nv.getHoTen(), nv.getSdt(), nv.getDiaChi(), nv.getGioiTinh(),
					nv.getNgaySinh(), nv.getTrangThai(), nv.getLanCuoiDangNhap(), nv.getTramTrungChuyen().getTenTram(),
					null, nv.getTaiKhoan()));
//			// Ko lấy ADMIN
//			if (!(nv.getTaiKhoan().getQuyenHan().size() >= 2)) {
//				_dsNhanVien
//				.add(new NhanVienDTO(nv.getId(), nv.getHoTen(), nv.getSdt(), nv.getDiaChi(), nv.getGioiTinh(),
//						nv.getNgaySinh(), nv.getTrangThai(), nv.getLanCuoiDangNhap(), nv.getTramTrungChuyen().getMaTram(), nv.getTaiKhoan()));
//			}
		});
		return _dsNhanVien;
	}

	@Override
	public NhanVien layNhanVienTheoID(Long idNhanVien) {
		NhanVien _nhanVien = nhanVienRepository.findById(idNhanVien)
				.orElseThrow(() -> new RuntimeException("Error: Không tìm thấy Nhân viên!"));
		_nhanVien.setAvatar(decompressBytes(_nhanVien.getAvatar()));
		return _nhanVien;
	}

	public NhanVien layNhanVienTheoIDAcc(Long idTaiKhoan) {
		NhanVien _nhanVien = nhanVienRepository.findNhanVienByTaiKhoanId(idTaiKhoan);
		_nhanVien.setAvatar(decompressBytes(_nhanVien.getAvatar()));
		return _nhanVien;
	}

	@Override
	/*- Nhân viên:
		+ Tài khoản:
			+Quyền hạn*/
	public NhanVien taoMoiNhanVien(NhanVienDTO nhanVienTaoMoi, MultipartFile avatarFile) {
		nhanVienTaoMoi.getTaiKhoan().setPassword(encoder.encode(nhanVienTaoMoi.getTaiKhoan().getPassword()));
		
		Set<QuyenHan> dsQuyen = new HashSet<>();
		QuyenHan nhanVien = quyenHanRepository.findByTenQuyen(EQuyenHan.ROLE_EMPLOYEE)
				.orElseThrow(() -> new RuntimeException("Error: Không tìm thấy Quyền!"));
		dsQuyen.add(nhanVien);
		if (nhanVienTaoMoi.getQuyenHan().equals("ROLE_ADMIN")) {
			QuyenHan admin = quyenHanRepository.findByTenQuyen(EQuyenHan.ROLE_ADMIN)
					.orElseThrow(() -> new RuntimeException("Error: Không tìm thấy Quyền!"));
			dsQuyen.add(admin);
		}
		nhanVienTaoMoi.getTaiKhoan().setQuyenHan(dsQuyen);
		TramTrungChuyen _tram = tramTrungChuyenRepository.findByMaTram(nhanVienTaoMoi.getMaTram())
				.orElseThrow(() -> new RuntimeException("Ko tìm thấy trạm"));

		NhanVien _nhanVien = null;
		try {
			_nhanVien = new NhanVien(null, nhanVienTaoMoi.getHoTen(), nhanVienTaoMoi.getSdt(),
					nhanVienTaoMoi.getDiaChi(), nhanVienTaoMoi.getGioiTinh(), nhanVienTaoMoi.getNgaySinh(),
					nhanVienTaoMoi.getTrangThai(), compressBytes(avatarFile.getBytes()),
					_tram,  nhanVienTaoMoi.getTaiKhoan(),null, null, null);
			return nhanVienRepository.save(_nhanVien);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return _nhanVien;

	}

	@Override
	public NhanVien capNhatNhanVien(NhanVienDTO nhanVienTaoMoi, MultipartFile avatarFile, Long idNhanVien) {
		NhanVien _nhanVien = nhanVienRepository.findById(idNhanVien)
				.orElseThrow(() -> new RuntimeException("Error: Không tìm thấy Nhan viên!"));
		Set<QuyenHan> dsQuyen = new HashSet<>();
		QuyenHan nhanVien = quyenHanRepository.findByTenQuyen(EQuyenHan.ROLE_EMPLOYEE)
				.orElseThrow(() -> new RuntimeException("Error: Không tìm thấy Quyền!"));
		dsQuyen.add(nhanVien);
		if (nhanVienTaoMoi.getQuyenHan().equals("ROLE_ADMIN")) {
			QuyenHan admin = quyenHanRepository.findByTenQuyen(EQuyenHan.ROLE_ADMIN)
					.orElseThrow(() -> new RuntimeException("Error: Không tìm thấy Quyền!"));
			dsQuyen.add(admin);
		}
		_nhanVien.getTaiKhoan().getQuyenHan().removeAll(dsQuyen);
		_nhanVien.getTaiKhoan().setQuyenHan(dsQuyen);
		try {
			if (avatarFile == null) {
				System.out.println("File ko cần update");
				_nhanVien.setHoTen(nhanVienTaoMoi.getHoTen());
				_nhanVien.setSdt(nhanVienTaoMoi.getSdt());
				_nhanVien.setGioiTinh(nhanVienTaoMoi.getGioiTinh());
				_nhanVien.setNgaySinh(nhanVienTaoMoi.getNgaySinh());
				_nhanVien.setDiaChi(nhanVienTaoMoi.getDiaChi());
				_nhanVien.setTrangThai(nhanVienTaoMoi.getTrangThai());
			} else {
				_nhanVien.setHoTen(nhanVienTaoMoi.getHoTen());
				_nhanVien.setSdt(nhanVienTaoMoi.getSdt());
				_nhanVien.setGioiTinh(nhanVienTaoMoi.getGioiTinh());
				_nhanVien.setNgaySinh(nhanVienTaoMoi.getNgaySinh());
				_nhanVien.setDiaChi(nhanVienTaoMoi.getDiaChi());
				_nhanVien.setTrangThai(nhanVienTaoMoi.getTrangThai());
				_nhanVien.setAvatar(compressBytes(avatarFile.getBytes()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nhanVienRepository.save(_nhanVien);
	}

	// ------------------ Hàm dựng sẵn của JPA -------------------------
	@Override
	public NhanVien save(NhanVien entity) {
		return nhanVienRepository.save(entity);
	}

	@Override
	public List<NhanVien> findAll() {
		return nhanVienRepository.findAll();
	}

	@Override
	public Optional<NhanVien> findById(Long id) {
		return nhanVienRepository.findById(id);
	}

	@Override
	public boolean existsById(Long id) {
		return nhanVienRepository.existsById(id);
	}

	@Override
	public long count() {
		return nhanVienRepository.count();
	}

	@Override
	public void deleteById(Long id) {
		nhanVienRepository.deleteById(id);
	}

	@Override
	public void delete(NhanVien entity) {
		nhanVienRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		nhanVienRepository.deleteAll();
	}

	/*
	 * 2 hàm để encode và decode file hình
	 */
	// compress the image bytes before storing it in the database
	public static byte[] compressBytes(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

		return outputStream.toByteArray();
	}

	// uncompress the image bytes before returning it to the angular application
	public static byte[] decompressBytes(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException ioe) {
		} catch (DataFormatException e) {
		}
		return outputStream.toByteArray();
	}
}
