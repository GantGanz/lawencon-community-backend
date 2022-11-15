package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "t_poll_option")
@Data
@EqualsAndHashCode(callSuper = false)
public class PollOption extends BaseEntity {

	private static final long serialVersionUID = -7394738515070027822L;

	@Column(name = "poll_content", nullable = false)
	private String pollContent;

	@ManyToOne
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;
}
