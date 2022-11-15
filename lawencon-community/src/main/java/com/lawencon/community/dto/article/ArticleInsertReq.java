package com.lawencon.community.dto.article;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ArticleInsertReq {

	@NotEmpty(message = "Article Title can't be empty!")
	@Size(max = 100, message = "Article Title is too long!")
	private String articleTitle;
	
	@NotEmpty(message = "Article Content can't be empty!")
	private String articleContent;
}
