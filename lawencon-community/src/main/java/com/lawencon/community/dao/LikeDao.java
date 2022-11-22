package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.Like;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.User;

@Repository
public class LikeDao extends AbstractJpaDao {

	public Long countLike(final String postId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT COUNT(l.id) ").append("FROM t_like l ")
				.append("WHERE l.is_active = TRUE ").append("AND l.post_id = :postId ");

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
	
	public List<Like> getAllByUserId(final String userId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT l.id, l.ver, l.user_id, l.post_id, l.is_active ").append("FROM t_like l ")
				.append("WHERE l.user_id = :userId ").append("AND l.is_active = TRUE ");

		final String sql = str.toString();
		final List<?> result = createNativeQuery(sql).setParameter("userId", userId).getResultList();

		final List<Like> likes = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(resultObj -> {
				final Object[] objArr = (Object[]) resultObj;
				final Like like = new Like();
				like.setId(objArr[0].toString());
				like.setVersion(Integer.valueOf(objArr[1].toString()));

				final User user = new User();
				user.setId(objArr[2].toString());
				like.setUser(user);

				final Post post = new Post();
				post.setId(objArr[3].toString());
				like.setPost(post);

				like.setIsActive(Boolean.valueOf(objArr[4].toString()));

				likes.add(like);
			});
		}

		return likes;
	}

}