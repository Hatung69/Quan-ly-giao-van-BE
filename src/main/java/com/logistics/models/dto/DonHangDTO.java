package com.logistics.models.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.logistics.models.DiaChiDonHang;
import com.logistics.models.HangHoa;
import com.logistics.models.KhachHang;
import com.logistics.models.NhanVien;
import com.logistics.models.Shipper;
import com.logistics.models.enu.ETrangThaiDonHang;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class DonHangDTO {
	private Long id;
	private String maDonHang;

	// ----- Thông tin người nhận -----
	private String tenNguoiNhan;
	private String sdtNguoiNhan;
	private DiaChiDonHang diaChi;

	// ----- Thông tin người gửi (Khách hàng) -----
	private KhachHang khachHang;

	// ----- Thông tin hàng hoá -----
	private Set<HangHoa> dsHangHoa = new HashSet<HangHoa>();

	// ----- Thông tin shipper vận hàng -----
	private Shipper shipper;

	// ----- Thu tiền hộ -----
	private String nguoiTraPhiShip;
	private double phiShip;
	private double tongTienThuHo;

	// ----- Thông tin phụ -----
	private String ghiChu;
	private byte[] anhDinhKem;
	private Date thoiGianDuKien;
	private ETrangThaiDonHang trangThai;
	
	private Set<DonHangTramDTO> dsDonHangTram = new HashSet<>();

	private Date thoiGianKhoiTao;
	private NhanVien nhanVien;
}
