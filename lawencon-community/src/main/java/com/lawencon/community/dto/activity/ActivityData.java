package com.lawencon.community.dto.activity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.lawencon.community.dto.attachmentactivity.AttachmentActivityData;

import lombok.Data;

@Data
public class ActivityData {

	private String id;
	private String activityTitle;
	private Integer version;
	private String activityLocation;
	private String provider;
	private LocalDateTime startAt;
	private LocalDateTime endAt;
	private BigDecimal fee;
	private String activityTypeId;
	private String activityTypeName;
	private String activityTypeCode;
	private String creatorName;
	private String createdBy;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private Boolean isActive;
	private List<AttachmentActivityData> attachmentActivityDatas;
	private String paymentStatus;
}
