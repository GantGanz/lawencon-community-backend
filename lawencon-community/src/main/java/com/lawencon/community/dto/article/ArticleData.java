package com.lawencon.community.dto.article;

import java.time.LocalDateTime;
import java.util.List;

import com.lawencon.community.dto.attachmentarticle.AttachmentArticleData;

import lombok.Data;

@Data
public class ArticleData {

	private String id;
	private Integer version;
	private String articleTitle;
	private String articleContent;
	private String createdBy;
	private LocalDateTime createdAt;
	private List<AttachmentArticleData> attachmentArticleDatas;

}
