package com.logistics.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@Table(name = "nhan_vien")
public class NhanVien {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50)
	private String hoTen;
	@Column(length = 15)
	private String sdt;
	@Column(length = 10)
	private String gioiTinh;
	@Temporal(TemporalType.DATE)
	private Date ngaySinh;
	@Column(length = 200)
	private String diaChi;
	@Column(length = 50)
	private String trangThai;

	@Column(name = "picByte", columnDefinition = "LONGBLOB")
	private byte[] avatar;

	@Temporal(TemporalType.TIMESTAMP)// sửa thành time sau
	private Date lanCuoiDangNhap;
	@CreationTimestamp
	private Date thoiGianKhoiTao;
	@UpdateTimestamp
	private Date thoiGianChinhSua;
	
	@OneToOne(cascade = { CascadeType.REMOVE, CascadeType.PERSIST})
	@JoinColumn(name = "tai_khoan_id", nullable = false)
	private TaiKhoan taiKhoan;
}
