package com.logistics.models.dto;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.logistics.models.enu.ETrangThaiTram;

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
public class TramTrungChuyenDTO {
	private Long id;
	private String maTram;
	private String tenTram;
	private String diaChi;
	private String sdt;
	private ETrangThaiTram trangThai;
	private String moTa;

	private Date thoiGianKhoiTao;
}
