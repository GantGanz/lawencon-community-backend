package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "t_article")
@Data
@EqualsAndHashCode(callSuper = false)
public class Article extends BaseEntity {

	private static final long serialVersionUID = 6289671817306427452L;

	@Column(name = "article_title", nullable = false, length = 100)
	private String articleTitle;

	@Column(name = "article_content", nullable = false)
	private String articleContent;
}
