package com.lawencon.community.dto.activitymember;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ActivityMemberInsertReq {

	@NotEmpty(message = "Activity Id can't be empty!")
	@Size(max = 36, message = "Activity Id is too long!")
	private String activityId;
	
	@NotEmpty(message = "File Codes can't be empty!")
	private String fileCodes;
	
	@NotEmpty(message = "Extension can't be empty!")
	@Size(max = 5, message = "Extension is too long!")
	private String extension;
	
	@NotEmpty(message = "User Id can't be empty!")
	@Size(max = 36, message = "User Id is too long!")
	private String userId;

}
