package com.logistics.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.logistics.models.DonHang;
import com.logistics.models.DonHangTramTrungChuyen;
import com.logistics.models.Shipper;
import com.logistics.models.TramTrungChuyen;
import com.logistics.models.dto.DonHangDTO;
import com.logistics.models.enu.ETrangThaiDonHang;
import com.logistics.models.enu.ETrangThaiShipper;
import com.logistics.services.DonHangService;
import com.logistics.services.ShipperService;
import com.logistics.services.TramTrungChuyenService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class DonHangController {

	@Autowired
	private DonHangService donHangService;
	@Autowired
	private TramTrungChuyenService tramTrungChuyenService;
	@Autowired
	private ShipperService shipperService;

	// Lấy danh sách đơn hàng
	@GetMapping("/don-hang")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<List<DonHangDTO>> layDSKhachHang() {
		try {
			List<DonHangDTO> _dsDonHang = donHangService.layDSDonHang();
			if (_dsDonHang.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(_dsDonHang, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Lấy đơn hàng theo ID
	@GetMapping("/don-hang/{idDonHang}")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<DonHangDTO> layDonHangTheoID(@PathVariable("idDonHang") Long idDonHang) {
		try {
			DonHangDTO _donHang = donHangService.layDonHangTheoID(idDonHang);
			if (_donHang == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(_donHang, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Lấy đơn hàng theo Mã đơn hàng
	@GetMapping("/don-hang/ma-don/{maDonHang}")
	public ResponseEntity<DonHangDTO> layDonHangTheoID(@PathVariable("maDonHang") String maDonHang) {
		try {
			DonHangDTO _donHang = donHangService.layDonHangTheoMaDonHang(maDonHang);
			if (_donHang == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(_donHang, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Tìm đơn nhanh
	@GetMapping("/don-hang/tim-kiem-nhanh")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<List<DonHangDTO>> timeKiemNhanh(@RequestParam("keySearch") String keySearch) {
		try {
			List<DonHangDTO> _dsDonHang = donHangService.timeKiemNhanh(keySearch);
			if (_dsDonHang.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(_dsDonHang, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Tạo mới đơn hàng
	@PostMapping(value = "/don-hang", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<DonHang> taoDonHang(@RequestParam("infoDonHang") String donHangTaoMoi,
			@RequestParam(name = "fileAnhDinhKem", required = false) MultipartFile fileAnhDinhKem) {
		try {
			Gson gson = new Gson();
			DonHangDTO _donHangDTO = gson.fromJson(donHangTaoMoi, DonHangDTO.class);
			_donHangDTO.getDsHangHoa().forEach(d -> {
				d.getTenHang();
			});
			return new ResponseEntity<>(donHangService.taoMoiDonHang(_donHangDTO, fileAnhDinhKem), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Cập nhật thông tin đơn hàng
	@PutMapping(value = "/don-hang/{idDonHang}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<DonHang> capNhatDonHang(@RequestParam("infoDonHang") String donHangcapNhat,
			@RequestParam(name = "fileAnhDinhKem", required = false) MultipartFile fileAnhDinhKem,
			@PathVariable("idDonHang") Long idDonHang) {
		try {
			Gson gson = new Gson();

			DonHangDTO _donHangDTO = gson.fromJson(donHangcapNhat, DonHangDTO.class);
			return new ResponseEntity<>(donHangService.capNhatDonHang(_donHangDTO, fileAnhDinhKem, idDonHang),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Tiếp nhận trạm cho đơn hàng
	boolean temp = false;

	@GetMapping("/don-hang/them-tram/{idDonHang}/{idTram}")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> themTramChoDonHang(@PathVariable("idDonHang") Long idDonHang,
			@PathVariable("idTram") Long idTram) {
		try {

			DonHang _donHang = donHangService.findById(idDonHang)
					.orElseThrow(() -> new RuntimeException("Error: Không tìm thấy Đơn hàng!"));
			TramTrungChuyen _tramTrungChuyen = tramTrungChuyenService.findById(idTram)
					.orElseThrow(() -> new RuntimeException("Error: Không tìm thấy trạm!"));
			_donHang.getDsDonHangTram().forEach(e -> {
				if (e.getTramTrungChuyen().getId() == _tramTrungChuyen.getId()) {
					temp = true;
				}
			});
			if (temp || _donHang.getTrangThai().equals(ETrangThaiDonHang.Cho_xac_nhan)) {
				temp = false;
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			DonHangTramTrungChuyen _donHangTramTrungChuyen= new DonHangTramTrungChuyen();
			_donHangTramTrungChuyen.setDonHang(_donHang);
			_donHangTramTrungChuyen.setTramTrungChuyen(_tramTrungChuyen);
			
			_donHang.themDonHangTramTrungChuyen(_donHangTramTrungChuyen);
			donHangService.save(_donHang);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Tiếp nhận trạm cho nhiều đơn hàng
	@PostMapping("/don-hang/them-tram/{idTram}")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> themTramChoDSDonHang(@RequestBody List<DonHang> dsDonHang,
			@PathVariable("idTram") Long idTram) {
		List<Long> idsDonHang = new ArrayList<>();
		try {
			dsDonHang.forEach(dh -> {
				idsDonHang.add(dh.getId());
			});
			List<DonHang> _dsDonHang = donHangService.findAllById(idsDonHang);
			TramTrungChuyen _tramTrungChuyen = tramTrungChuyenService.findById(idTram)
					.orElseThrow(() -> new RuntimeException("Error: Không tìm thấy trạm!"));
			DonHangTramTrungChuyen _donHangTramTrungChuyen;
			for (DonHang _donHang : _dsDonHang) {
				_donHang.getDsDonHangTram().forEach(e -> {
					if (e.getTramTrungChuyen().getId() == _tramTrungChuyen.getId()) {
						temp = true;
					}
				});
				if (temp || _donHang.getTrangThai().equals(ETrangThaiDonHang.Cho_xac_nhan)) {
					temp = false;
					continue;
				}
				_donHangTramTrungChuyen = new DonHangTramTrungChuyen(null, _donHang, _tramTrungChuyen, null, null);
				_donHang.themDonHangTramTrungChuyen(_donHangTramTrungChuyen);
				donHangService.save(_donHang);
			}
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Điều phối đơn hàng cho Shipper
	@GetMapping("/don-hang/dieu-phoi/{idDonHang}/{idShipper}")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> dieuPhoiDonHang(@PathVariable("idDonHang") Long idDonHang,
			@PathVariable("idShipper") Long idShipper) {
		try {
			DonHang _donHang = donHangService.findById(idDonHang)
					.orElseThrow(() -> new RuntimeException("Error: Không tìm thấy Đơn hàng!"));
			Shipper _shipper = shipperService.findById(idShipper)
					.orElseThrow(() -> new RuntimeException("Error: Không tìm thấy shipper!"));
			if (_donHang.getShipper() != null)
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);// Hiện đang có Shipper
			_donHang.setTrangThai(ETrangThaiDonHang.Dang_giao);
			_shipper.setTrangThai(ETrangThaiShipper.Dang_giao_hang);
			_donHang.setShipper(_shipper);
			_shipper.getDsDonHang().add(_donHang);
			donHangService.save(_donHang);
			shipperService.save(_shipper);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Thay đổi Shipper
	@GetMapping("/don-hang/thay-doi-shipper/{idDonHang}/{idShipperMoi}/{idShipperCu}")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> thayDoiShipper(@PathVariable("idDonHang") Long idDonHang,
			@PathVariable("idShipperMoi") Long idShipperMoi, @PathVariable("idShipperCu") Long idShipperCu) {
		try {
			DonHang _donHang = donHangService.findById(idDonHang)
					.orElseThrow(() -> new RuntimeException("Error: Không tìm thấy Đơn hàng!"));
			Shipper _shipperMoi = shipperService.findById(idShipperMoi)
					.orElseThrow(() -> new RuntimeException("Error: Không tìm thấy shipper!"));
			Shipper _shipperCu = shipperService.findById(idShipperCu)
					.orElseThrow(() -> new RuntimeException("Error: Không tìm thấy shipper!"));
			for (DonHang dh : _shipperMoi.getDsDonHang()) {
				if (dh.getId() == _donHang.getId()) {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);// Shipper đang có đơn này rồi
				}
			}
			_shipperCu.getDsDonHang().remove(_donHang);
			_donHang.setShipper(_shipperMoi);
			_shipperMoi.getDsDonHang().add(_donHang);
			donHangService.save(_donHang);
			shipperService.save(_shipperCu);
			shipperService.save(_shipperMoi);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Điều phối cho danh sách đơn hàng
	@PostMapping("/don-hang/dieu-phoi/{idShipper}")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> dieuPhoiDsDonHang(@RequestBody List<DonHang> dsDonHang,
			@PathVariable("idShipper") Long idShipper) {
		List<Long> idsDonHang = new ArrayList<>();
		try {
			dsDonHang.forEach(dh -> {
				idsDonHang.add(dh.getId());
			});
			List<DonHang> _dsDonHang = donHangService.findAllById(idsDonHang);
			Shipper _shipper = shipperService.findById(idShipper)
					.orElseThrow(() -> new RuntimeException("Error: Không tìm thấy trạm!"));
			for (DonHang _donHang : _dsDonHang) {
				if (_donHang.getShipper() != null || _donHang.getTrangThai().equals(ETrangThaiDonHang.Cho_xac_nhan)) {
					continue;
				}
				_donHang.setTrangThai(ETrangThaiDonHang.Dang_giao);
				_shipper.setTrangThai(ETrangThaiShipper.Dang_giao_hang);
				_donHang.setShipper(_shipper);
				_shipper.getDsDonHang().add(_donHang);
				donHangService.save(_donHang);
				shipperService.save(_shipper);
			}
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Xác nhận nhiều Đơn hàng
	@PostMapping("/don-hang/xac-nhan")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> xacNhanNhieuDonHang(@RequestBody List<DonHang> dsDonHang) {
		List<Long> idsDonHang = new ArrayList<>();
		try {
			dsDonHang.forEach(dh -> {
				idsDonHang.add(dh.getId());
			});
			List<DonHang> _dsDonHang = donHangService.findAllById(idsDonHang);
			_dsDonHang.forEach(donHang -> {
				if (donHang.getTrangThai().equals(ETrangThaiDonHang.Cho_xac_nhan)) {
					donHang.setTrangThai(ETrangThaiDonHang.Cho_giao_hang);
					donHangService.save(donHang);
				}
			});
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Xoá hàng hoạt Đơn hàng
	@PostMapping("/don-hang/xoa-tat-ca")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> xoaNhieuDonHang(@RequestBody List<DonHang> dsDonHang) {
		List<Long> idsDonHang = new ArrayList<>();
		try {
			dsDonHang.forEach(dh -> {
				idsDonHang.add(dh.getId());
			});
			donHangService.deleteAllById(idsDonHang);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Xoá đơn hàng theo ID
	@DeleteMapping("/don-hang/{idDonHang}")
	@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> xoaDonHang(@PathVariable("idDonHang") Long idDonHang) {
		try {
			Optional<DonHang> _donHang = donHangService.findById(idDonHang);
			if (!_donHang.isPresent()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			donHangService.deleteById(idDonHang);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	// Tổng thống kê
//	@GetMapping("/don-hang/thong-ke")
//	@PreAuthorize("hasRole('ADMIN')")
//	public ResponseEntity<Map<String, Integer>> tongThongKe() {
//		try {
//			Map<String, Integer> _obj = donHangService.tongThongKe();
//			if (_obj == null) {
//				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//			}
//			return new ResponseEntity<>(_obj, HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

	// Thống kê theo thời gian
	@GetMapping("/don-hang/thong-ke/{batDau}/{ketThuc}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<DonHangDTO>> thongKeTheoThoiGian(@PathVariable("batDau") Date batDau,
			@PathVariable("ketThuc") Date ketThuc) {
		try {
			List<DonHangDTO> _dsDonHang = donHangService.thongKeTheoThoiGian(batDau, ketThuc);
			if (_dsDonHang.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(_dsDonHang, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
