package com.lawencon.community.dto.userverification;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserVerificationUpdateReq {

	@NotNull(message = "Id can't be empty!")
	@Size(max = 36, message = "Id is too long!")
	private String id;

	@NotNull(message = "Is Active can't be empty!")
	private Boolean isActive;
}
