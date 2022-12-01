package com.lawencon.community.constant;

import lombok.Getter;

@Getter
public enum ActivityTypeConstant {
	EVENT("Event", "EVN"), COURSE("Course", "CRS");

	private final String activityTypeName;
	private final String activityTypeCode;

	private ActivityTypeConstant(String activityTypeName, String activityTypeCode) {
		this.activityTypeName = activityTypeName;
		this.activityTypeCode = activityTypeCode;
	}

}
