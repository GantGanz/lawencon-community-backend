package com.lawencon.community.model;

import java.math.BigDecimal;
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
@Table(name = "t_activity")
@Data
@EqualsAndHashCode(callSuper = false)
public class Activity extends BaseEntity {

	private static final long serialVersionUID = 289179169564948704L;

	@Column(name = "title", nullable = false, length = 50)
	private String activityTitle;

	@Column(name = "provider", nullable = false, length = 50)
	private String provider;

	@Column(name = "activity_location", nullable = false)
	private String activityLocation;

	@Column(name = "start_at", nullable = false)
	private LocalDateTime startAt;

	@Column(name = "end_at", nullable = false)
	private LocalDateTime endAt;

	@Column(name = "fee", nullable = false)
	private BigDecimal fee;

	@OneToOne
	@JoinColumn(name = "activity_type_id", nullable = false)
	private ActivityType activityType;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
