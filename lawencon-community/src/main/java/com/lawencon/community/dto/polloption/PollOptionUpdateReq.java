package com.lawencon.community.dto.polloption;

import lombok.Data;

@Data
public class PollOptionUpdateReq {

	private String id;
	private Integer version;
	private String pollContent;
	private String pollId;
	private Boolean isActive;
	
}
