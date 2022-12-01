package com.lawencon.community.constant;

import lombok.Getter;

@Getter
public enum PostTypeConstant {
	REGULAR("Regular", "REG"), POLLING("Polling", "POL"), PREMIUM("Premium", "PRE");

	private final String postTypeName;
	private final String postTypeCode;

	private PostTypeConstant(String postTypeName, String postTypeCode) {
		this.postTypeName = postTypeName;
		this.postTypeCode = postTypeCode;
	}
}
