package com.lawencon.community.dto.activity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ActivityUpdateReq {

	private String id;
	private String activityTitle;
	private String activityLocation;
	private LocalDateTime startAt;
	private LocalDateTime endAt;
	private BigDecimal fee;
	private String activityTypeId;
	private Boolean isActive;
	private Integer version;
}
