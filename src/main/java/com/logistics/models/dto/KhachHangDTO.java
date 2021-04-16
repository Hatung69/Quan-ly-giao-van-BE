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
	private String tenKhach;
	private String tenCuaHang;
	private String sdt;
	private String diaChi;
	private String email;
	private String cmnd;
	private String loaiKhachHang;
	private int soDonHang;// số đơn hàng sẽ đc update và phụ thuộc vào đơn hàng, nên ko cho set đâu
	private String soTaiKhoan;
	private double tienDoiSoat;// tương tự tiền đối soát
	
	private Date thoiGianCapNhat;
}
