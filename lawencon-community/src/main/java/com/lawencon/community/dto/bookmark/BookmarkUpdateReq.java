package com.lawencon.community.dto.bookmark;

import lombok.Data;

@Data
public class BookmarkUpdateReq {

	private String id;
	private Boolean isActive;
	private Integer version;
}
