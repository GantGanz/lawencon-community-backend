package com.lawencon.community.dto.activitymember;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ActivityMemberUpdateReq {

	@NotNull(message = "Id can't be empty!")
	@Size(max = 36, message = "Id is too long!")
	private String id;
	
	@NotNull(message = "Is Approved can't be empty!")
	private Boolean isApproved;
	
	@NotNull(message = "Is Active can't be empty!")
	private Boolean isActive;
	
	@NotNull(message = "Version can't be empty!")
	private Integer version;
}
