package com.lawencon.community.dto.activitymember;

import lombok.Data;

@Data
public class ActivityMemberInsertReq {

	private String activityId;
	private String fileCodes;
	private String extension;
	private String userId;

}
