package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "t_attachment_post")
@Data
@EqualsAndHashCode(callSuper = false)
public class AttachmentPost extends BaseEntity {

	private static final long serialVersionUID = -4188145173987753837L;

	@OneToOne
	@JoinColumn(name = "file_id", nullable = false)
	private File file;

	@OneToOne
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;
}
