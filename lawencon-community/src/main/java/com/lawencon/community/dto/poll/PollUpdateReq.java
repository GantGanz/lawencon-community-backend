package com.lawencon.community.dto.poll;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PollUpdateReq {

	private String id;
	private String pollTitle;
	private Integer version;
	private LocalDateTime endAt;
	private String postId;
	private Boolean isActive;

}
