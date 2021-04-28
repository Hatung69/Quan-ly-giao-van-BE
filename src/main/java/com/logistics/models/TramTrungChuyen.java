package com.logistics.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.logistics.models.enu.ETrangThaiDonHang;
import com.logistics.models.enu.ETrangThaiTram;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "tram_trung_chuyen")
public class TramTrungChuyen {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 50, unique = true)
	private String maTram;

	@Column(length = 100)
	private String tenTram;
	@Column(length = 300)
	private String diaChi;
	@Enumerated(EnumType.STRING)
	@Column(length = 50)
	private ETrangThaiTram trangThai;
	@Column(length = 300)
	private String moTa;

	// ----- Đơn hàng -----
	@OneToMany(mappedBy = "tramTrungChuyen")
	private Set<DonHangTramTrungChuyen> dsDonHangTram = new HashSet<>();
	
	// ----- Nhân viên -----
	@OneToMany(mappedBy = "tramTrungChuyen", cascade = CascadeType.ALL)
	private Set<NhanVien> dsNhanVien = new HashSet<>();

	// ----- Lưu giữ chung -----
	@CreationTimestamp
	private Date thoiGianKhoiTao;
	@UpdateTimestamp
	private Date thoiGianCapNhat;
	@ManyToOne
	@JoinColumn(name = "tao_boi")
	@CreatedBy
	private NhanVien taoBoi;
	@ManyToOne
	@JoinColumn(name = "cap_nhat_boi")
	@LastModifiedBy
	private NhanVien capNhatBoi;
}
