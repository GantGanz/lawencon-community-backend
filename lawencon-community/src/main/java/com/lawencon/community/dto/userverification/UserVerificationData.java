package com.lawencon.community.dto.userverification;

import lombok.Data;

@Data
public class UserVerificationData {

	private String id;
	private Integer version;
	private String userId;
	private String verificationCode;
	private Boolean isActive;
	
}
