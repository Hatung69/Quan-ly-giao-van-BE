package com.logistics.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "hang_hoa")
public class HangHoa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 100)
	private String tenHang;
	private double giaTri;
	private int soLuong;
	private int khoiLuong;
	@Column(length = 200)
	private String moTa;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.DETACH})
	@JoinColumn(name = "don_hang_id", nullable = false)
	@JsonIgnore // Ko ingnore là lúc get nó bị lồng lồng nhau
	private DonHang donHang;
}
