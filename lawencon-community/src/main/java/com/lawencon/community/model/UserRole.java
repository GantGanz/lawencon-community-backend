package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_user_role", uniqueConstraints = {
		@UniqueConstraint(name = "t_user_role_ck", columnNames = { "role_code", "role_name" }),
		@UniqueConstraint(name = "t_user_role_bk", columnNames = { "role_code" }) })
@Data
@EqualsAndHashCode(callSuper = false)
public class UserRole extends BaseEntity {

	private static final long serialVersionUID = -2773239898053697048L;

	@Column(name = "role_code", nullable = false, length = 5)
	@Setter
	@Getter
	private String roleCode;

	@Column(name = "role_name", nullable = false, length = 12)
	@Setter
	@Getter
	private String roleName;
}
