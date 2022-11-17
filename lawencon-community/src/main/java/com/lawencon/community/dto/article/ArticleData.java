package com.lawencon.community.dto.article;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ArticleData {

	private String id;
	private Integer version;
	private String articleTitle;
	private String articleContent;
	private String createdBy;
	private LocalDateTime createdAt;

}
