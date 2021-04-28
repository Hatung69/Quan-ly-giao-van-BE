package com.logistics.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.logistics.models.NhanVien;
import com.logistics.services.impl.AuditorAwareImpl;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class PersistentConfig {
	@Bean
	public AuditorAware<NhanVien> auditorProvider() {
		return new AuditorAwareImpl();
	}
}
