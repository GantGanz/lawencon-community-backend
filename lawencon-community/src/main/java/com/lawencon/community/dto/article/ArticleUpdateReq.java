package com.lawencon.community.dto.article;

import lombok.Data;

@Data
public class ArticleUpdateReq {

	private String id;
	private String articleTitle;
	private String articleContent;
	private Boolean isActive;
	private Integer version;
}
