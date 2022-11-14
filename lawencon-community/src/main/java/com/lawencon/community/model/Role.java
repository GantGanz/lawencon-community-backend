package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_user_role", uniqueConstraints = {
		@UniqueConstraint(name = "t_user_role_ck", columnNames = { "role_code", "role_name" }),
		@UniqueConstraint(name = "t_user_role_bk", columnNames = { "role_code" }) })
public class Role extends BaseEntity {
	
	private static final long serialVersionUID = 8361745271697416724L;

	@Column(name = "role_code", nullable = false, length = 5)
	private String roleCode;

	@Column(name = "role_name", nullable = false, length = 12)
	private String roleName;

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
