package com.lawencon.community.dto.poll;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PollInsertReq {

	private String pollTitle;
	private LocalDateTime endAt;
	private String postId;
	
}
