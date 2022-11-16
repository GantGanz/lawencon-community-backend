package com.lawencon.community.dto.post;

import lombok.Data;

@Data
public class PostInsertReq {

	private String postTitle;
	private String postContent;
	private String postTypeId;
	private String userId;
}
