package com.lawencon.community.constant;

public enum ActivityTypeConstant {
	EVENT("Event", "EVN"), COURSE("Course", "CRS");

	private final String activityTypeName;
	private final String activityTypeCode;

	private ActivityTypeConstant(String activityTypeName, String activityTypeCode) {
		this.activityTypeName = activityTypeName;
		this.activityTypeCode = activityTypeCode;
	}

	public String getActivityTypeName() {
		return activityTypeName;
	}

	public String getActivityTypeCode() {
		return activityTypeCode;
	}

}
