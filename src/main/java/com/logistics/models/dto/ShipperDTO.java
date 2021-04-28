package com.logistics.models.dto;

import java.util.Date;

import com.logistics.models.enu.ETrangThaiShipper;

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
public class ShipperDTO {
	private Long id;
	private String hoTen;
	private String sdt;
	private String diaChi;
	private String email;
	private String cmnd;
	private int soDonHangDaNHan;
	private ETrangThaiShipper trangThai;
	
	private Date thoiGianCapNhat;
}
