package com.lawencon.community.dto.activitymember;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class ActivityMemberInsertReq {

	@NotEmpty(message = "Activity Id can't be empty!")
	private String activityId;
	
	@NotEmpty(message = "File Codes can't be empty!")
	private String fileCodes;
	
	@NotEmpty(message = "Extension can't be empty!")
	private String extension;
	
	@NotEmpty(message = "Activity Id can't be empty!")
	private String userId;

}
