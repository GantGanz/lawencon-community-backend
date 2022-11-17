package com.lawencon.community.dto.user;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class UserUpdateReq {

	private String id;
	private String fullname;
	private String company;
	private String fileCodes;
	private String extension;
	private String roleId;
	private String positionId;
	private String industryId;
	private BigDecimal wallet;
	private Boolean isPremium;
	private Boolean isActive;
	private Integer version;
}
