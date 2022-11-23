package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.constant.RoleConstant;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Industry;
import com.lawencon.community.model.Position;
import com.lawencon.community.model.Role;
import com.lawencon.community.model.User;

@Repository
public class UserDao extends AbstractJpaDao {

	public Optional<User> getByEmail(final String email) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT u.id, u.email, u.pass, u.fullname, ur.role_name, ur.role_code, u.file_id, u.company,")
				.append("u.is_premium, i.industry_name, p.position_name ").append("FROM t_user u ")
				.append("INNER JOIN t_role ur ON ur.id = u.role_id ")
				.append("INNER JOIN t_industry i ON u.industry_id = i.id ")
				.append("INNER JOIN t_position p ON u.position_id = p.id ").append("WHERE email = :username ")
				.append("AND u.is_active = TRUE ");

		User user = null;
		try {
			final Object userObj = createNativeQuery(str.toString()).setParameter("username", email).getSingleResult();

			if (userObj != null) {
				final Object[] objArr = (Object[]) userObj;
				user = new User();
				user.setId(objArr[0].toString());
				user.setEmail(objArr[1].toString());
				user.setPass(objArr[2].toString());
				user.setFullname(objArr[3].toString());

				final Role role = new Role();
				role.setRoleName(objArr[4].toString());
				role.setRoleCode(objArr[5].toString());

				final File file = new File();
				file.setId(objArr[6].toString());

				user.setCompany(objArr[7].toString());
				user.setIsPremium(Boolean.valueOf(objArr[8].toString()));

				final Industry industry = new Industry();
				industry.setIndustryName(objArr[9].toString());

				final Position position = new Position();
				position.setPositionName(objArr[10].toString());

				user.setFile(file);
				user.setRole(role);
				user.setIndustry(industry);
				user.setPosition(position);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		Optional<User> userOptional = Optional.ofNullable(user);
		return userOptional;
	}

	public String getSystemId() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT u.id ").append("FROM t_user u ").append("INNER JOIN t_role ur ON ur.id = u.role_id ")
				.append("WHERE role_code = :roleCode ");

		String userId = null;
		try {
			final Object userObj = createNativeQuery(str.toString())
					.setParameter("roleCode", RoleConstant.SYSTEM.getRoleCode()).getSingleResult();
			if (userObj != null) {
				userId = userObj.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userId;
	}

	public Long countAllUser(final String roleCode) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT COUNT(u.id) ").append("FROM t_user u ").append("INNER JOIN t_role ur ON ur.id = u.role_id ")
				.append("WHERE role_code = :roleCode ");

		Long total = null;
		try {
			final Object userObj = createNativeQuery(str.toString()).setParameter("roleCode", roleCode)
					.getSingleResult();
			if (userObj != null) {
				total = Long.valueOf(userObj.toString());
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	public Long countAllPremium() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT COUNT(u.id) ").append("FROM t_user u ").append("WHERE is_premium = TRUE ");

		Long total = null;
		try {
			final Object userObj = createNativeQuery(str.toString()).getSingleResult();
			if (userObj != null) {
				total = Long.valueOf(userObj.toString());
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	public List<User> getAll(final Integer offset, final Integer limit) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT u.id, u.fullname, u.email, u.company, u.role_id, ur.role_name, ")
				.append("u.industry_id, i.industry_name, u.position_id, p.position_name, ")
				.append("u.is_premium, u.file_id, u.ver, u.is_active ").append("FROM t_user u ")
				.append("INNER JOIN t_role ur ON ur.id = u.role_id ")
				.append("INNER JOIN t_industry i ON u.industry_id = i.id ")
				.append("INNER JOIN t_position p ON u.position_id = p.id ").append("WHERE u.is_active = TRUE ").append("ORDER BY u.id DESC");

		final List<?> result = createNativeQuery(str.toString(), offset, limit).getResultList();

		final List<User> users = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(userObj -> {
				final Object[] objArr = (Object[]) userObj;
				User user = new User();
				user.setId(objArr[0].toString());
				user.setFullname(objArr[1].toString());
				user.setEmail(objArr[2].toString());
				user.setCompany(objArr[3].toString());

				final Role role = new Role();
				role.setId(objArr[4].toString());
				role.setRoleName(objArr[5].toString());

				final Industry industry = new Industry();
				industry.setId(objArr[6].toString());
				industry.setIndustryName(objArr[7].toString());

				final Position position = new Position();
				position.setId(objArr[8].toString());
				position.setPositionName(objArr[9].toString());
				
				user.setIsPremium(Boolean.valueOf(objArr[10].toString()));
				
				final File file = new File();
				file.setId(objArr[11].toString());

				user.setVersion(Integer.valueOf(objArr[12].toString()));
				user.setIsActive(Boolean.valueOf(objArr[13].toString()));
				
				user.setFile(file);
				user.setRole(role);
				user.setIndustry(industry);
				user.setPosition(position);
				users.add(user);
			});
		}

		return users;
	}
}