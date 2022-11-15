package com.lawencon.community.dto.comment;

import lombok.Data;

@Data
public class CommentInsertReq {

	private String id;
	private String commentContent;
	private String userId;
	private String postId;
	private String commentId;

}
