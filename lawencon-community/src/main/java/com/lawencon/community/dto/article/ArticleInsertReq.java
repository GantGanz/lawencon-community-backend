package com.lawencon.community.dto.article;

import java.util.List;

import com.lawencon.community.dto.attachmentarticle.AttachmentArticleInsertReq;

import lombok.Data;

@Data
public class ArticleInsertReq {

	private String articleTitle;
	private String articleContent;
	private List<AttachmentArticleInsertReq> attachmentArticleInsertReqs;
}
