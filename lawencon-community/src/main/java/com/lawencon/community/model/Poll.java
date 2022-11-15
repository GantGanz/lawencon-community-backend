package com.lawencon.community.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "t_poll")
@Data
@EqualsAndHashCode(callSuper = false)
public class Poll extends BaseEntity {

	private static final long serialVersionUID = -6260718519969710244L;

	@Column(name = "poll_title", nullable = false)
	private String pollTitle;

	@Column(name = "end_at", nullable = false)
	private LocalDateTime endAt;

	@OneToOne
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;
}
