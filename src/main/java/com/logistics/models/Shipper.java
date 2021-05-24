package com.logistics.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.logistics.models.enu.ETrangThaiShipper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "shipper")
public class Shipper {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50)
	private String hoTen;
	@Column(length = 15)
	private String sdt;
	@Column(length = 200)
	private String diaChi;
	@Column(length = 100)
	private String email;
	@Column(length = 15)
	private String cmnd;
	@Enumerated(EnumType.STRING)
	@Column(length = 50)
	private ETrangThaiShipper trangThai;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "shipper", cascade = { CascadeType.DETACH })
	private Set<DonHang> dsDonHang = new HashSet<>();

	// ----- Lưu giữ chung -----
	@CreationTimestamp
	private Date thoiGianKhoiTao;
	@UpdateTimestamp
	private Date thoiGianCapNhat;
}
