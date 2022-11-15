package com.lawencon.community.constant;

public enum RoleConstant {
	SUPERADMIN("Super Admin", "sa"), ADMIN("Admin", "ADM"), MEMBER("Member", "MMB"), SYSTEM("System", "SYS");

	private final String roleName;
	private final String roleCode;

	private RoleConstant(String roleName, String roleCode) {
		this.roleName = roleName;
		this.roleCode = roleCode;
	}

	String getRoleName() {
		return roleName;
	}

	String getRoleCode() {
		return roleCode;
	}
}
