package com.logistics.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import com.logistics.models.NhanVien;
import com.logistics.models.TaiKhoan;
import com.logistics.repositories.NhanVienRepository;
import com.logistics.repositories.TaiKhoanRepository;

public class AuditorAwareImpl implements AuditorAware<NhanVien> {
	@Autowired
	private NhanVienRepository nhanVienRepository;
	
	@Autowired
	private TaiKhoanRepository taiKhoanRepository;

	@Override
	public Optional<NhanVien> getCurrentAuditor() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<TaiKhoan> _taiKhoan= taiKhoanRepository.findByUsername(username);
		Optional<NhanVien> _nhanVien = nhanVienRepository.findByTaiKhoan(_taiKhoan.get());
		return Optional.ofNullable(_nhanVien.get());
	}
}
