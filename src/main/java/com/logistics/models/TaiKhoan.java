package com.logistics.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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

@Entity
@Table(name = "tai_khoan", uniqueConstraints = { @UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email") })
public class TaiKhoan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	private String password;

	private String email;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "taiKhoan_quyenHan", joinColumns = @JoinColumn(name = "tai_khoan_id"), inverseJoinColumns = @JoinColumn(name = "quyen_han_id"))
	private Set<QuyenHan> quyenHan = new HashSet<QuyenHan>();

	public TaiKhoan(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}
}
