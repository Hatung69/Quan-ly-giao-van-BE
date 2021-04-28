package com.logistics.models.dto;

import java.util.Date;

import com.logistics.models.DonHang;
import com.logistics.models.NhanVien;
import com.logistics.models.TramTrungChuyen;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DonHangTramDTO {
	private Long id;
	private DonHang donHang;
	private TramTrungChuyen tramTrungChuyen;

	private Date thoiGianKhoiTao;
	private NhanVien taoBoi;
}
