package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "t_post")
@Data
@EqualsAndHashCode(callSuper = false)
public class Post extends BaseEntity {

	private static final long serialVersionUID = -6437753490560897005L;

	@Column(name = "post_title", nullable = false, length = 100)
	private String postTitle;

	@Column(name = "post_content", nullable = false)
	private String postContent;
	
	@OneToOne
	@JoinColumn(name = "post_type_id", nullable = false)
	private PostType postType;
	
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
