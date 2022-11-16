package com.lawencon.community.dto.pollvote;

import lombok.Data;

@Data
public class PollVoteUpdateReq {

	private String id;
	private Integer version;
	private String pollOptionId;
	private String userId;
	private Boolean isActive;
	
}
