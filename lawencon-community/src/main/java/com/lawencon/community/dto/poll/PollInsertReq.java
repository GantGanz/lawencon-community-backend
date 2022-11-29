package com.lawencon.community.dto.poll;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawencon.community.dto.polloption.PollOptionInsertReq;

import lombok.Data;

@Data
public class PollInsertReq {

	private String pollTitle;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'+07:00'")
	private LocalDateTime endAt;
	private List<PollOptionInsertReq> pollOptionInsertReqs;
}
