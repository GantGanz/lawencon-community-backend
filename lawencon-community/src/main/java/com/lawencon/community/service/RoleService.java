package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.RoleDao;
import com.lawencon.community.dto.role.RoleData;
import com.lawencon.community.dto.role.RoleRes;
import com.lawencon.community.dto.role.RolesRes;
import com.lawencon.community.model.Role;

@Service
public class RoleService extends BaseCoreService {
	@Autowired
	private RoleDao roleDao;

	public RolesRes getAll() {
		final List<Role> roles = roleDao.getAll(Role.class);
		final List<RoleData> roleDatas = new ArrayList<>();
		for (int i = 0; i < roles.size(); i++) {
			final Role role = roles.get(i);
			final RoleData roleData = new RoleData();
			roleData.setId(role.getId());
			roleData.setRoleName(role.getRoleName());
			roleData.setRoleCode(role.getRoleCode());
			roleData.setVersion(role.getVersion());

			roleDatas.add(roleData);
		}
		final RolesRes rolesRes = new RolesRes();
		rolesRes.setData(roleDatas);
		return rolesRes;
	}

	public RoleRes getById(final String id) {
		final Role role = roleDao.getById(Role.class, id);
		final RoleData roleData = new RoleData();
		roleData.setId(role.getId());
		roleData.setRoleName(role.getRoleName());
		roleData.setRoleCode(role.getRoleCode());
		roleData.setVersion(role.getVersion());
		final RoleRes roleRes = new RoleRes();
		roleRes.setData(roleData);
		return roleRes;
	}
}
