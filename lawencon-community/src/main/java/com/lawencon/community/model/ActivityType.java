package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_activity_type", uniqueConstraints = {
		@UniqueConstraint(name = "t_activity_type_bk", columnNames = { "activity_type_code" }),
		@UniqueConstraint(name = "t_activity_type_ck", columnNames = { "activity_type_code", "activity_type_name" }) })
public class ActivityType extends BaseEntity {

	private static final long serialVersionUID = 173643027340409022L;

	@Column(name = "activity_type_name", nullable = false, length = 20)
	private String activityTypeName;

	@Column(name = "activity_type_code", nullable = false, length = 6)
	private String activityTypeCode;
}
