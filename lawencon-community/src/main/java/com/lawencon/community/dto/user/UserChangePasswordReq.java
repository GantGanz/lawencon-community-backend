package com.lawencon.community.dto.user;

import lombok.Data;

@Data
public class UserChangePasswordReq {

	private String oldPassword;
	private String newPassword;
	private Integer version;
}
