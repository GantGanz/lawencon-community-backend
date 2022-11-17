package com.lawencon.community.dto.article;

import lombok.Data;

@Data
public class ArticleData {

	private String id;
	private Integer version;
	private String articleTitle;
	private String articleCode;
	private String creatorName;
	private String createdBy;
	
}
