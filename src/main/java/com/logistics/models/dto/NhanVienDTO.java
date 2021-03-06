package com.logistics.models.dto;

import java.util.Date;

import com.logistics.models.TaiKhoan;
import com.logistics.models.enu.ETrangThaiNhanVien;

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
public class NhanVienDTO {
	private Long id;
	private String hoTen;
	private String sdt;
	private String diaChi;
	private String gioiTinh;
	private Date ngaySinh;
	private ETrangThaiNhanVien trangThai;
	private Date lanCuoiDangNhap;
	private String maTram;;
	
	private String quyenHan;
	private TaiKhoan taiKhoan;
}
