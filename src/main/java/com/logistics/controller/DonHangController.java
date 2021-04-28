package com.logistics.controller;

import java.util.List;
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
import com.logistics.models.KhachHang;
import com.logistics.models.TramTrungChuyen;
import com.logistics.models.dto.DonHangDTO;
import com.logistics.models.dto.KhachHangDTO;
import com.logistics.models.dto.NhanVienDTO;
import com.logistics.services.DonHangService;
import com.logistics.services.KhachHangService;
import com.logistics.services.TramTrungChuyenService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class DonHangController {

	@Autowired
	private DonHangService donHangService;
	@Autowired
	private TramTrungChuyenService tramTrungChuyenService;

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
		@PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
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

	// Thêm trạm cho đơn hàng
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
			if (temp) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			DonHangTramTrungChuyen _donHangTramTrungChuyen = new DonHangTramTrungChuyen(null, _donHang,
					_tramTrungChuyen, null, null);
			_donHang.themDonHangTramTrungChuyen(_donHangTramTrungChuyen);
			donHangService.save(_donHang);
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
}
