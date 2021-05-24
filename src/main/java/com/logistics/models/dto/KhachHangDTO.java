package com.logistics.models.dto;

import java.util.Date;

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
public class KhachHangDTO {
	private Long id;
	private String tenKhachHang;
	private String sdt;
	private String diaChi;
	private String email;
	private String cmnd;
	private String soTaiKhoan;
	private int soDonHang = 0;
	private String loaiKhachHang;

	private Date thoiGianCapNhat;
}
