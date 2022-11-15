package com.lawencon.community.dto.activitymember;

import lombok.Data;

@Data
public class ActivityMemberData {

	private String id;
	private Integer version;
	private Boolean isApproved;
	private String activityId;
	private String activityName;
	private String fileId;
	private String userId;
	private String userName;
	private Boolean isActive;
	
}
