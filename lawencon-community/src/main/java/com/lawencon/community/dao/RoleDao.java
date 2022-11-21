package com.lawencon.community.dao;
import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;

@Repository
public class RoleDao extends AbstractJpaDao {
	
	public String getByRoleCode(final String roleCode) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT r.id ").append("FROM t_role r ")
				.append("WHERE role_code = :roleCode ");

		String roleId = null;
		try {
			final Object userObj = createNativeQuery(str.toString())
					.setParameter("roleCode", roleCode).getSingleResult();
			if (userObj != null) {
				roleId = userObj.toString();
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return roleId;
	}
	
}