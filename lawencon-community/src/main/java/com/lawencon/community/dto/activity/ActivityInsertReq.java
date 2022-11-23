package com.lawencon.community.dto.activity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.lawencon.community.dto.attachmentactivity.AttachmentActivityInsertReq;

import lombok.Data;

@Data
public class ActivityInsertReq {

	private String activityTitle;
	private String activityLocation;
	private String provider;
	private LocalDateTime startAt;
	private LocalDateTime endAt;
	private BigDecimal fee;
	private String activityTypeId;
	private List<AttachmentActivityInsertReq> attachmentActivityInsertReqs;
}
