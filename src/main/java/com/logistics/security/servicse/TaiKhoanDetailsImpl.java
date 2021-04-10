package com.logistics.security.servicse;

import java.util.stream.Collectors;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.logistics.models.TaiKhoan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor

@SuppressWarnings("unused")
public class TaiKhoanDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String username;

	private String email;

	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	public static TaiKhoanDetailsImpl build(TaiKhoan taiKhoan) {
		List<GrantedAuthority> authorities = taiKhoan.getQuyenHan().stream()
				.map(quyen -> new SimpleGrantedAuthority(quyen.getTenQuyen().name())).collect(Collectors.toList());

		return new TaiKhoanDetailsImpl(taiKhoan.getId(), taiKhoan.getUsername(), taiKhoan.getEmail(),
				taiKhoan.getPassword(), authorities);
	}

	// -------------------------@Override-------------------
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		TaiKhoanDetailsImpl user = (TaiKhoanDetailsImpl) obj;
		return Objects.equals(id, user.id);
	}
}
