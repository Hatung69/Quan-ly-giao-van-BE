package com.logistics.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	private String tenKhach;
	@Column(length = 100)
	private String tenCuaHang;
	@Column(length = 15)
	private String sdt;
	@Column(length = 200)
	private String diaChi;
	@Column(length = 100)
	private String email;
	@Column(length = 15)
	private String cmnd;
	@Column(length = 50)
	private String loaiKhachHang;

	private int soDonHang;//
	
	@Column(length = 15)
	private String soTaiKhoan;
	
	private double tienDoiSoat;//
	
	@CreationTimestamp
	private Date thoiGianKhoiTao;
	@UpdateTimestamp
	private Date thoiGianCapNhat;

}
