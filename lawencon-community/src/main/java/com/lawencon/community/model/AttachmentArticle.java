package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "t_attachment_article")
@Data
@EqualsAndHashCode(callSuper = false)
public class AttachmentArticle extends BaseEntity {

	private static final long serialVersionUID = 5122756966310247627L;

	@OneToOne
	@JoinColumn(name = "file_id", nullable = false)
	private File file;

	@OneToOne
	@JoinColumn(name = "article_id", nullable = false)
	private Article article;
}
