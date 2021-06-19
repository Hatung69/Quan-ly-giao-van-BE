package com.logistics.models.dto;

import java.util.Date;

import com.logistics.models.DonHang;
import com.logistics.models.NhanVien;

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
public class DonHangTramDTO {
	private Long id;
	private DonHang donHang;
	private TramTrungChuyenDTO tramTrungChuyen;

	private Date thoiGianKhoiTao;
	private NhanVien taoBoi;
}
