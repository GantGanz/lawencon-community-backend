package com.lawencon.community.dto.user;

import lombok.Data;

@Data
public class UserUpdateReq {

	private String id;
	private String fullname;
	private String company;
	private String fileCodes;
	private String extension;
	private String positionId;
	private String industryId;
	private Boolean isPremium;
	private Boolean isActive;
	private Integer version;
}
