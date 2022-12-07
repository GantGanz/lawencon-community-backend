package com.lawencon.community.constant;

import lombok.Getter;

@Getter
public enum RoleConstant {
	SUPERADMIN("Super Admin", "SA"), ADMIN("Admin", "ADM"), MEMBER("Member", "MMB"), SYSTEM("System", "SYS");

	private final String roleName;
	private final String roleCode;

	private RoleConstant(String roleName, String roleCode) {
		this.roleName = roleName;
		this.roleCode = roleCode;
	}
}
