package com.logistics.security.servicse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.logistics.models.TaiKhoan;
import com.logistics.repositories.TaiKhoanRepository;

@Service
public class TaiKhoanDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private TaiKhoanRepository taiKhoanRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		TaiKhoan taiKhoan = taiKhoanRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy tài khoản với username " + username));
		return TaiKhoanDetailsImpl.build(taiKhoan);
	}

}
