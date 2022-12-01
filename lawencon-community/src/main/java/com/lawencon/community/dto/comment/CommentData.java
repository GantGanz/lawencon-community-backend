package com.lawencon.community.dto.comment;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CommentData {

	private String id;
	private String commentContent;
	private String userName;
	private String userId;
	private String fileId;
	private String postId;
	private String commentId;
	private LocalDateTime createdAt;
	private String positionName;
	private String company;
	
}
