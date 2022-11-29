package com.lawencon.community.dto.poll;

import java.time.LocalDateTime;
import java.util.List;

import com.lawencon.community.dto.polloption.PollOptionInsertReq;

import lombok.Data;

@Data
public class PollInsertReq {

	private String pollTitle;
	private LocalDateTime endAt;
	private List<PollOptionInsertReq> pollOptionInsertReqs;
	
}
