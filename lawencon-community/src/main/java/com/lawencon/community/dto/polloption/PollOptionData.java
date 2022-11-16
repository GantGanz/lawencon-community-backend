package com.lawencon.community.dto.polloption;

import lombok.Data;

@Data
public class PollOptionData {

	private String id;
	private Integer version;
	private String pollContent;
	private String pollId;
	private Boolean isActive;
	
}
