package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "t_like")
@Data
@EqualsAndHashCode(callSuper = false)
public class Like extends BaseEntity {

	private static final long serialVersionUID = -5057263070934472809L;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToOne
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;
}
