package com.logistics.services.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.logistics.models.DonHang;
import com.logistics.models.DonHangTramTrungChuyen;
import com.logistics.models.HangHoa;
import com.logistics.models.KhachHang;
import com.logistics.models.TongThongKe;
import com.logistics.models.dto.DonHangDTO;
import com.logistics.models.dto.DonHangTramDTO;
import com.logistics.models.enu.ETrangThaiDonHang;
import com.logistics.repositories.DonHangRepository;
import com.logistics.repositories.KhachHangRepository;
import com.logistics.services.DonHangService;

import net.bytebuddy.utility.RandomString;

@Service
public class DonHangServiceImpl implements DonHangService {

	@Autowired
	private DonHangRepository donHangRepository;
	@Autowired
	private KhachHangRepository khachHangRepository;

	public Map<String, Integer> tongThongKe() {
		List<Integer[]> _obj = donHangRepository.tongThongKe();
		Map<String, Integer> _listTong = new HashMap<>();
		_listTong.put("TongDH", _obj.get(0)[0]);
		_listTong.put("TongKH", _obj.get(0)[1]);
		_listTong.put("TongShipper", _obj.get(0)[2]);
		_listTong.put("TongNhanVien", _obj.get(0)[3]);
		return _listTong;
	}

	@Override
	public List<DonHangDTO> layDSDonHang() {
		List<DonHang> dsDonHang = donHangRepository.findAllByOrderByThoiGianKhoiTaoDesc();
		List<DonHangDTO> _dsDonHang = new ArrayList<DonHangDTO>();
		dsDonHang.forEach(dh -> {
			Set<DonHangTramDTO> dsDonHangTramDTO = new HashSet<>();
			dh.getDsDonHangTram().forEach(e -> {
				dsDonHangTramDTO.add(new DonHangTramDTO(e.getId(), e.getDonHang(), e.getTramTrungChuyen(),
						e.getThoiGianKhoiTao(), e.getTaoBoi()));
			});
			_dsDonHang.add(new DonHangDTO(dh.getId(), dh.getMaDonHang(), dh.getTenNguoiNhan(), dh.getSdtNguoiNhan(),
					dh.getDiaChi(), dh.getKhachHang(), dh.getDsHangHoa(), dh.getShipper(), dh.getNguoiTraPhiShip(),
					dh.getPhiShip(), dh.getTongTienThuHo(), dh.getGhiChu(), null, dh.getThoiGianDuKien(),
					dh.getTrangThai(), dh.getTrangThaiDoiSoat(), dsDonHangTramDTO, dh.getThoiGianKhoiTao(),
					dh.getTaoBoi()));
		});
		return _dsDonHang;
	}

	@Override
	public DonHangDTO layDonHangTheoID(Long idDonHang) {
		DonHang _donHang = donHangRepository.findById(idDonHang)
				.orElseThrow(() -> new RuntimeException("Error: Không tìm thấy Đơn hàng này!"));
		Set<DonHangTramDTO> _dhttc = new HashSet<DonHangTramDTO>();
		_donHang.getDsDonHangTram().forEach(e -> {
			_dhttc.add(new DonHangTramDTO(e.getId(), e.getDonHang(), e.getTramTrungChuyen(), e.getThoiGianKhoiTao(),
					e.getTaoBoi()));
		});
		DonHangDTO _donHangDTO = new DonHangDTO(_donHang.getId(), _donHang.getMaDonHang(), _donHang.getTenNguoiNhan(),
				_donHang.getSdtNguoiNhan(), _donHang.getDiaChi(), _donHang.getKhachHang(), _donHang.getDsHangHoa(),
				_donHang.getShipper(), _donHang.getNguoiTraPhiShip(), _donHang.getPhiShip(),
				_donHang.getTongTienThuHo(), _donHang.getGhiChu(), decompressBytes(_donHang.getAnhDinhKem()),
				_donHang.getThoiGianDuKien(), _donHang.getTrangThai(), _donHang.getTrangThaiDoiSoat(), _dhttc,
				_donHang.getThoiGianKhoiTao(), _donHang.getTaoBoi());
		return _donHangDTO;
	}

	public List<DonHangDTO> thongKeTheoThoiGian(Date batDau, Date ketThuc) {
		List<DonHang> dsDonHang = donHangRepository.findByThoiGianKhoiTaoBetween(batDau, ketThuc);
		List<DonHangDTO> _dsDonHang = new ArrayList<DonHangDTO>();
		dsDonHang.forEach(dh -> {
			_dsDonHang.add(new DonHangDTO(dh.getId(), dh.getMaDonHang(), dh.getTenNguoiNhan(), dh.getSdtNguoiNhan(),
					dh.getDiaChi(), null, dh.getDsHangHoa(), null, dh.getNguoiTraPhiShip(),
					dh.getPhiShip(), dh.getTongTienThuHo(), dh.getGhiChu(), null, dh.getThoiGianDuKien(),
					dh.getTrangThai(), dh.getTrangThaiDoiSoat(), null, dh.getThoiGianKhoiTao(),
					null));
		});
		return _dsDonHang;
	}

	@Override
	public DonHangDTO layDonHangTheoMaDonHang(String maDonHang) {
		DonHang _donHang = donHangRepository.findByMaDonHang(maDonHang)
				.orElseThrow(() -> new RuntimeException("Error: Không tìm thấy Đơn hàng này!"));
		Set<DonHangTramDTO> _dhttc = new HashSet<DonHangTramDTO>();
		_donHang.getDsDonHangTram().forEach(e -> {
			_dhttc.add(new DonHangTramDTO(e.getId(), e.getDonHang(), e.getTramTrungChuyen(), e.getThoiGianKhoiTao(),
					e.getTaoBoi()));
		});
		DonHangDTO _donHangDTO = new DonHangDTO(_donHang.getId(), _donHang.getMaDonHang(), _donHang.getTenNguoiNhan(),
				_donHang.getSdtNguoiNhan(), _donHang.getDiaChi(), _donHang.getKhachHang(), _donHang.getDsHangHoa(),
				_donHang.getShipper(), _donHang.getNguoiTraPhiShip(), _donHang.getPhiShip(),
				_donHang.getTongTienThuHo(), _donHang.getGhiChu(), decompressBytes(_donHang.getAnhDinhKem()),
				_donHang.getThoiGianDuKien(), _donHang.getTrangThai(), _donHang.getTrangThaiDoiSoat(), _dhttc,
				_donHang.getThoiGianKhoiTao(), _donHang.getTaoBoi());
		return _donHangDTO;
	}

	@Override
	public List<DonHangDTO> timeKiemNhanh(String keySearch) {
		List<DonHang> dsDonHang = donHangRepository.timeKiemNhanh(keySearch);
		List<DonHangDTO> _dsDonHang = new ArrayList<DonHangDTO>();
		dsDonHang.forEach(dh -> {
			Set<DonHangTramDTO> dsDonHangTramDTO = new HashSet<>();
			dh.getDsDonHangTram().forEach(e -> {
				dsDonHangTramDTO.add(new DonHangTramDTO(e.getId(), e.getDonHang(), e.getTramTrungChuyen(),
						e.getThoiGianKhoiTao(), e.getTaoBoi()));
			});
			_dsDonHang.add(new DonHangDTO(dh.getId(), dh.getMaDonHang(), dh.getTenNguoiNhan(), dh.getSdtNguoiNhan(),
					dh.getDiaChi(), dh.getKhachHang(), dh.getDsHangHoa(), dh.getShipper(), dh.getNguoiTraPhiShip(),
					dh.getPhiShip(), dh.getTongTienThuHo(), dh.getGhiChu(), null, dh.getThoiGianDuKien(),
					dh.getTrangThai(), dh.getTrangThaiDoiSoat(), dsDonHangTramDTO, dh.getThoiGianKhoiTao(),
					dh.getTaoBoi()));
		});
		return _dsDonHang;
	}

	@Override
	/*- Đơn hàng:
		+ DiaChiDonHang:
		+ KhachHang
		+ Set<HangHoa>
		+ Shipper
	*/
	public DonHang taoMoiDonHang(DonHangDTO donHangTaoMoi, MultipartFile fileAnhDinhKem) {
		String maDonHang = "DH-" + RandomString.make(10);
		Optional<KhachHang> _khachHang = khachHangRepository.findBySdt(donHangTaoMoi.getKhachHang().getSdt());
		DonHang _donHang = new DonHang();
		try {
			_donHang.setMaDonHang(maDonHang);
			_donHang.setTenNguoiNhan(donHangTaoMoi.getTenNguoiNhan());
			_donHang.setSdtNguoiNhan(donHangTaoMoi.getSdtNguoiNhan());
			_donHang.setDiaChi(donHangTaoMoi.getDiaChi());
			if (_khachHang.isPresent()) {
				_khachHang.get().getDsDonHang().add(_donHang);
				_donHang.setKhachHang(_khachHang.get());// Set cái có sẵn
			} else {
				donHangTaoMoi.getKhachHang().setLoaiKhachHang("Cá nhân");
				_donHang.setKhachHang(donHangTaoMoi.getKhachHang());// Tạo mới
			}
			donHangTaoMoi.getDsHangHoa().forEach(hh -> {
				hh.setDonHang(_donHang);
				_donHang.getDsHangHoa().add(hh);
			});
			_donHang.setNguoiTraPhiShip(donHangTaoMoi.getNguoiTraPhiShip());
			_donHang.setPhiShip(donHangTaoMoi.getPhiShip());
			_donHang.setTongTienThuHo(donHangTaoMoi.getTongTienThuHo());
			_donHang.setGhiChu(donHangTaoMoi.getGhiChu());
			_donHang.setThoiGianDuKien(donHangTaoMoi.getThoiGianDuKien());
			_donHang.setAnhDinhKem(compressBytes(fileAnhDinhKem.getBytes()));
			_donHang.setTrangThai(ETrangThaiDonHang.Cho_xac_nhan);
			_donHang.setTrangThaiDoiSoat("Chưa đối soát");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return donHangRepository.save(_donHang);
	}

	@Override
	public DonHang capNhatDonHang(DonHangDTO donHangCapNhat, MultipartFile fileAnhDinhKem, Long idDonHang) {
		DonHang _donHang = donHangRepository.findById(idDonHang)
				.orElseThrow(() -> new RuntimeException("Error: Không tìm thấy Đơn hàng!"));
		try {
			if (fileAnhDinhKem == null) {
				_donHang.setTenNguoiNhan(donHangCapNhat.getTenNguoiNhan());
				_donHang.setSdtNguoiNhan(donHangCapNhat.getSdtNguoiNhan());
				_donHang.setDiaChi(donHangCapNhat.getDiaChi());
				_donHang.setNguoiTraPhiShip(donHangCapNhat.getNguoiTraPhiShip());
				_donHang.setPhiShip(donHangCapNhat.getPhiShip());
				_donHang.setTongTienThuHo(donHangCapNhat.getTongTienThuHo());
				_donHang.setTrangThai(donHangCapNhat.getTrangThai());
				_donHang.setGhiChu(donHangCapNhat.getGhiChu());
			} else {
				_donHang.setTenNguoiNhan(donHangCapNhat.getTenNguoiNhan());
				_donHang.setSdtNguoiNhan(donHangCapNhat.getSdtNguoiNhan());
				_donHang.setDiaChi(donHangCapNhat.getDiaChi());
				_donHang.setNguoiTraPhiShip(donHangCapNhat.getNguoiTraPhiShip());
				_donHang.setPhiShip(donHangCapNhat.getPhiShip());
				_donHang.setTongTienThuHo(donHangCapNhat.getTongTienThuHo());
				_donHang.setTrangThai(donHangCapNhat.getTrangThai());
				_donHang.setGhiChu(donHangCapNhat.getGhiChu());
				_donHang.setAnhDinhKem(compressBytes(fileAnhDinhKem.getBytes()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return donHangRepository.save(_donHang);
	}

	// ------------------ Hàm dựng sẵn của JPA -------------------------
	@Override
	public DonHang save(DonHang entity) {
		return donHangRepository.save(entity);
	}

	@Override
	public List<DonHang> findAll() {
		return donHangRepository.findAll();
	}

	public List<DonHang> findAllById(Iterable<Long> ids) {
		return donHangRepository.findAllById(ids);
	}

	@Override
	public Optional<DonHang> findById(Long id) {
		return donHangRepository.findById(id);
	}

	@Override
	public boolean existsById(Long id) {
		return donHangRepository.existsById(id);
	}

	@Override
	public long count() {
		return donHangRepository.count();
	}

	@Override
	public void deleteById(Long id) {
		donHangRepository.deleteById(id);
	}

	@Override
	public void delete(DonHang entity) {
		donHangRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		donHangRepository.deleteAll();
	}

	@Override
	public void deleteAllById(List<Long> ids) {
		donHangRepository.deleteAllById(ids);
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
