package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "t_comment")
@Data
@EqualsAndHashCode(callSuper = false)
public class Comment extends BaseEntity {

	private static final long serialVersionUID = -1936490942093127780L;

	@Column(name = "comment_content", nullable = false)
	private String commentContent;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

//	@OneToOne
//	@JoinColumn(name = "comment_id", nullable = false)
//	private Comment comment;
}
