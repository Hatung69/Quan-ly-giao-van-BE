package com.logistics.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.logistics.models.enu.ETrangThaiNhanVien;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "nhan_vien")
public class NhanVien {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50)
	private String hoTen;
	@Column(length = 15)
	private String sdt;
	@Column(length = 200)
	private String diaChi;
	@Column(length = 10)
	private String gioiTinh;
	@Temporal(TemporalType.DATE)
	private Date ngaySinh;
	@Enumerated(EnumType.STRING)
	@Column(length = 50)
	private ETrangThaiNhanVien trangThai;
	@Column(name = "avatar", columnDefinition = "LONGBLOB")
	private byte[] avatar;//

	@OneToOne(cascade = { CascadeType.REMOVE, CascadeType.PERSIST })
	@JoinColumn(name = "tai_khoan_id", nullable = false)
	private TaiKhoan taiKhoan;

	// Trạm chung chuyển
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.DETACH })
	@JoinColumn(name = "tram_trung_chuyen_id", nullable = true)
	@JsonIgnore // Ko ingnore là lúc get nó bị lồng lồng nhau
	private TramTrungChuyen tramTrungChuyen;

	// ----- Lưu giữ chung -----
	@Temporal(TemporalType.TIMESTAMP)
	private Date lanCuoiDangNhap;
	@CreationTimestamp
	private Date thoiGianKhoiTao;
	@UpdateTimestamp
	private Date thoiGianChinhSua;

}
