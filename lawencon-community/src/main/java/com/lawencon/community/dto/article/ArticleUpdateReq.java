package com.lawencon.community.dto.article;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ArticleUpdateReq {

	@NotNull(message = "Id can't be empty!")
	@Size(max = 36, message = "Id is too long!")
	private String id;
	
	@NotEmpty(message = "Article Title can't be empty!")
	@Size(max = 100, message = "Article Title is too long!")
	private String articleTitle;
	
	@NotEmpty(message = "Article Content can't be empty!")
	private String articleContent;
	
	@NotNull(message = "Is Active can't be empty!")
	private Boolean isActive;
	
	@NotNull(message = "Version can't be empty!")
	private Integer version;
}
