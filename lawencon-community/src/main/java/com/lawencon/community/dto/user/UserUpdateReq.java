package com.lawencon.community.dto.user;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserUpdateReq {

	@NotNull(message = "Id can't be empty!")
	@Size(max = 36, message = "Id is too long!")
	private String id;
	
	@NotEmpty(message = "fullname can't be empty!")
	@Size(max = 50, message = "Fullname is too long!")
	private String fullname;
	
	@NotEmpty(message = "company can't be empty!")
	@Size(max = 50, message = "Company is too long!")
	private String company;
	
	@NotEmpty(message = "File Codes can't be empty!")
	private String fileCodes;
	
	@NotEmpty(message = "Extension can't be empty!")
	@Size(max = 5, message = "Extension is too long!")
	private String extension;
	
	@NotEmpty(message = "Role Id can't be empty!")
	@Size(max = 36, message = "Role Id is too long!")
	private String roleId;
	
	@NotEmpty(message = "Position Id can't be empty!")
	@Size(max = 36, message = "Position Id is too long!")
	private String positionId;
	
	@NotNull(message = "Wallet can't be empty!")
	private BigDecimal wallet;
	
	@NotNull(message = "Is Premium can't be empty!")
	private Boolean isPremium;

	@NotNull(message = "Is Active can't be empty!")
	private Boolean isActive;
	
	@NotNull(message = "Version can't be empty!")
	private Integer version;
}
