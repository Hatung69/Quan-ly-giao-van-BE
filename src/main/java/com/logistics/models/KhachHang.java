package com.logistics.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "khach_hang")
public class KhachHang {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50)
	private String tenKhachHang;
	@Column(length = 15)
	private String sdt;
	@Column(length = 300)
	private String diaChi;
	@Column(length = 50)
	private String email;
	@Column(length = 15)
	private String cmnd;
	@Column(length = 15)
	private String soTaiKhoan;
	@Column(length = 50)
	private String loaiKhachHang;

	@OneToMany(fetch = FetchType.LAZY,mappedBy = "khachHang", cascade = { CascadeType.REMOVE })
	private Set<DonHang> dsDonHang = new HashSet<>();

	// ----- Lưu giữ chung -----
	@CreationTimestamp
	private Date thoiGianKhoiTao;
	@UpdateTimestamp
	private Date thoiGianCapNhat;
}
