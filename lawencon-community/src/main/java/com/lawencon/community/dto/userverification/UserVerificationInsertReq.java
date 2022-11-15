package com.lawencon.community.dto.userverification;

import lombok.Data;

@Data
public class UserVerificationInsertReq {
		
	private String userId;
	private String verificationCode;
	
}