package com.lawencon.community.dto.activitymember;

import lombok.Data;

@Data
public class ActivityMemberUpdateReq {

	private String id;
	private Boolean isApproved;
	private Boolean isActive;
	private Integer version;
}
