package com.lawencon.community.dto.user;

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
	private String industryId;
	private String industryName;
	private String positionId;
	private String positionName;
	private Boolean isPremium;
	private String fileId;
	private Boolean isActive;

}
