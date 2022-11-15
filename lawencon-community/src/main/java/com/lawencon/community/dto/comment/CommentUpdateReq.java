package com.lawencon.community.dto.comment;

import lombok.Data;

@Data
public class CommentUpdateReq {

	private String id;
	private String commentContent;
	private Boolean isActive;
	private Integer version;
}
