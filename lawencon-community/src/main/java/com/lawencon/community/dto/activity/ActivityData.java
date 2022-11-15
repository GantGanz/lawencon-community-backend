package com.lawencon.community.dto.activity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ActivityData {

	private String id;
	private String title;
	private Integer version;
	private String activityLocation;
	private LocalDateTime startAt;
	private LocalDateTime endAt;
	private BigDecimal fee;
	private String activityTypeId;
	private String activityTypeName;
	private String creatorName;
	private String createdBy;
	private Boolean isActive;
	
}
