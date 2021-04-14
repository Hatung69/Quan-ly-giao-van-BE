package com.logistics.models.dto;

import java.util.Date;

import com.logistics.models.TaiKhoan;

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
	private String gioiTinh;
	private Date ngaySinh;
	private String diaChi;
	private String trangThai;
	private Date lanCuoiDangNhap;
	
	private TaiKhoan taiKhoan;
}
