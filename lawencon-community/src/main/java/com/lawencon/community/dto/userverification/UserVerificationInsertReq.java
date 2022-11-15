package com.lawencon.community.dto.userverification;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserVerificationInsertReq {
		
	@NotEmpty(message = "User Id can't be empty!")
	@Size(max = 36, message = "User Id is too long!")
	private String userId;
	
	@NotEmpty(message = "Verification Code can't be empty!")
	@Size(max = 5, message = "Verification Code is too long!")
	private String verificationCode;
}