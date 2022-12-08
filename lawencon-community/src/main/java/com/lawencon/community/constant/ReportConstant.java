package com.lawencon.community.constant;

import lombok.Getter;

@Getter
public enum ReportConstant {
	ACTIVITY_MEMBER("Activity", "Member Report"), ACTIVITY_SUPERADMIN("Activity", "Super Admin Report"),
	MEMBER_INCOME("Income", "Member Report"), SUPERADMIN_INCOME("Income", "Super Admin Report");

	private final String reportTitleEnum;
	private final String reportTypeEnum;

	private ReportConstant(final String reportTitleEnum, final String reportTypeEnum) {
		this.reportTitleEnum = reportTitleEnum;
		this.reportTypeEnum = reportTypeEnum;
	}
}
