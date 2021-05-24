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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.logistics.models.enu.ETrangThaiDonHang;

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
@Table(name = "don_hang")
public class DonHang {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 50, unique = true)
	private String maDonHang;

	// ----- Thông tin người nhận -----
	@Column(length = 50)
	private String tenNguoiNhan;
	@Column(length = 15)
	private String sdtNguoiNhan;
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@JoinColumn(name = "dia_chi_don_hang_id", nullable = false)
	private DiaChiDonHang diaChi;

	// ----- Thông tin người gửi (Khách hàng) -----
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.DETACH })
	@JoinColumn(name = "khach_hang_id", nullable = false)
	@JsonIgnore // Ko ingnore là lúc get nó bị lồng lồng nhau
	private KhachHang khachHang;

	// ----- Thông tin hàng hoá -----
	@OneToMany(mappedBy = "donHang", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	private Set<HangHoa> dsHangHoa = new HashSet<HangHoa>();

	// ----- Thông tin shipper vận hàng -----
	@ManyToOne(cascade = { CascadeType.PERSIST,CascadeType.DETACH })
	@JoinColumn(name = "shipper_id", nullable = true)
	@JsonIgnore // Ko ingnore là lúc get nó bị lồng lồng nhau
	private Shipper shipper;

	// ----- Thu tiền hộ -----
	@Column(length = 50)
	private String nguoiTraPhiShip;
	private double phiShip;
	private double tongTienThuHo;

	// ----- Thông tin phụ -----
	@Column(name = "anh_dinh_kem", columnDefinition = "LONGBLOB")
	private byte[] anhDinhKem;
	@Enumerated(EnumType.STRING)
	@Column(length = 50)
	private ETrangThaiDonHang trangThai;
	@Column(length = 300)
	private String ghiChu;
	
	@Temporal(TemporalType.DATE)
	private Date thoiGianDuKien;

	// ----- Trạm trung chuyển -----
	@OneToMany(mappedBy = "donHang", cascade = {CascadeType.ALL})
	private Set<DonHangTramTrungChuyen> dsDonHangTram = new HashSet<>();

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

	public void themDonHangTramTrungChuyen(DonHangTramTrungChuyen donHangTramTrungChuyen) {
		this.dsDonHangTram.add(donHangTramTrungChuyen);
	}
}
