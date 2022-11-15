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
@Table(name = "t_activity_member")
@Data
@EqualsAndHashCode(callSuper = false)
public class ActivityMember extends BaseEntity {

	private static final long serialVersionUID = 2808821138299251109L;

	@Column(name = "is_approved", nullable = false)
	private Boolean isAproved;

	@OneToOne
	@JoinColumn(name = "file_id", nullable = false)
	private File file;

	@OneToOne
	@JoinColumn(name = "activity_id", nullable = false)
	private Activity activity;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
