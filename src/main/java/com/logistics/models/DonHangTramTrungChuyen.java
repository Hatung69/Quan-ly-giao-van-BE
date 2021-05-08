package com.logistics.models;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "don_hang_tram_trung_chuyen")
public class DonHangTramTrungChuyen {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "don_hang_id")
	@JsonIgnore // Ko ingnore là lúc get nó bị lồng lồng nhau
    private DonHang donHang;
	@ManyToOne
	@JoinColumn(name = "tram_trung_chuyen_id")
	@JsonIgnore // Ko ingnore là lúc get nó bị lồng lồng nhau
    private TramTrungChuyen tramTrungChuyen;
    
 // ----- Lưu giữ chung -----
 	@CreationTimestamp
 	private Date thoiGianKhoiTao;
 	@ManyToOne
 	@JoinColumn(name = "tao_boi")
 	@CreatedBy
 	private NhanVien taoBoi;
}
