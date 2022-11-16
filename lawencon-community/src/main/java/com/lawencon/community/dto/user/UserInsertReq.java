package com.lawencon.community.dto.user;

import lombok.Data;

@Data
public class UserInsertReq {
	private String fullname;
	private String email;
	private String password;
	private String company;
	private String fileCodes;
	private String extension;
	private String roleId;
	private String positionId;
	private String industryId;
	private Boolean isPremium;

}
