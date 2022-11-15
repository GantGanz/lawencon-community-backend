package com.lawencon.community.dto.user;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class UserInsertReq {
	
	private String fullname;
	private String email;
	private String company;
	private String fileCodes;
	private String extension;
	private String roleId;
	private String positionId;
	private BigDecimal wallet;
	private Boolean isPremium;
	
}
