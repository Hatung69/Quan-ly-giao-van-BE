package com.logistics.models.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SigninRequest {
	@NotBlank
	private String username;

	@NotBlank
	private String password;
}
