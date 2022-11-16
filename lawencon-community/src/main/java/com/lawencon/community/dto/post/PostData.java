package com.lawencon.community.dto.post;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PostData {

	private String id;
	private Integer version;
	private String postTitle;
	private String postContent;
	private String postTypeId;
	private String userId;
	private String creatorName;
	private String createdBy;
	private LocalDateTime updatedAt;
	
}
