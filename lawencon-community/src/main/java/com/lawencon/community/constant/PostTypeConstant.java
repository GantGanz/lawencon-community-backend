package com.lawencon.community.constant;

public enum PostTypeConstant {
	REGULAR("Regular", "REG"), POLLING("Polling", "POL"), PREMIUM("Premium", "PRE");

	private final String postTypeName;
	private final String postTypeCode;

	private PostTypeConstant(String postTypeName, String postTypeCode) {
		this.postTypeName = postTypeName;
		this.postTypeCode = postTypeCode;
	}

	String getPostTypeName() {
		return postTypeName;
	}

	String getPostTypeCode() {
		return postTypeCode;
	}

}
