package com.lawencon.community.dao;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;

@Repository
public class LikeDao extends AbstractJpaDao {

	public Long countLike(final String postId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT COUNT(l.id) ").append("FROM t_like l ").append("INNER JOIN t_post p ON l.post_id = p.id ")
				.append("WHERE l.is_active = TRUE ").append("AND p.id = :postId ");

		Long total = null;
		try {
			final Object userObj = createNativeQuery(str.toString()).setParameter("postId", postId).getSingleResult();
			if (userObj != null) {
				total = Long.valueOf(userObj.toString());
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return total;
	}
}