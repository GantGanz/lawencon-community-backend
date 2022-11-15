package com.lawencon.community.dto.comment;

import lombok.Data;

@Data
public class CommentData {

	private String id;
	private String commentContent;
	private String userName;
	private String userId;
	private String postId;
	private String commentId;
	
}
