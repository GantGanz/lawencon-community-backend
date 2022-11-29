package com.lawencon.community.dto.activity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawencon.community.dto.attachmentactivity.AttachmentActivityInsertReq;

import lombok.Data;

@Data
public class ActivityInsertReq {

	private String activityTitle;
	private String activityLocation;
	private String provider;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'+07:00'")
	private LocalDateTime startAt;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'+07:00'")
	private LocalDateTime endAt;
	private BigDecimal fee;
	private String activityTypeId;
	private List<AttachmentActivityInsertReq> attachmentActivityInsertReqs;
}
