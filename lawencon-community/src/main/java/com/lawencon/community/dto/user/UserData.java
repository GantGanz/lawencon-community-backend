package com.lawencon.community.dto.user;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class UserData {

	private String id;
	private Integer version;
	private String fullname;
	private String email;
	private String company;
	private String roleId;
	private String roleName;
	private String positionId;
	private String positionName;
	private BigDecimal wallet;
	private Boolean isPremium;
	private Long fileId;
	private Boolean isActive;
	
}
