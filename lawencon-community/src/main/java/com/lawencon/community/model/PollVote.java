package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "t_poll_vote")
@Data
@EqualsAndHashCode(callSuper = false)
public class PollVote extends BaseEntity {

	private static final long serialVersionUID = -2997004678469173802L;

	@OneToOne
	@JoinColumn(name = "poll_option_id", nullable = false)
	private PollOption pollOption;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
