package com.lawencon.community.dao;
import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;

@Repository
public class PostTypeDao extends AbstractJpaDao {

	public String getPostTypeIdbyCode(final String code) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT id ").append("FROM t_post_type ")
				.append("WHERE post_type_code = :code ");

		String userId = null;
		try {
			final Object userObj = createNativeQuery(str.toString())
					.setParameter("code", code).getSingleResult();
			if (userObj != null) {
				userId = userObj.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userId;
	}
}