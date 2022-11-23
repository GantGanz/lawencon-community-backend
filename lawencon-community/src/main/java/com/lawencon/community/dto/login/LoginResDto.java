package com.lawencon.community.dto.login;

import lombok.Data;

@Data
public class LoginResDto {
	private String id;
	private String fullname;
	private String token;
	private String roleCode;
	private String email;
	private String fileId;
	private Boolean isPremium;
}