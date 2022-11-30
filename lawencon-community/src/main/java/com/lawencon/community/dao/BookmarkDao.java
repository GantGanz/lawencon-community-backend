package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.Bookmark;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.User;

@Repository
public class BookmarkDao extends AbstractJpaDao {
	public List<Bookmark> getAllByUserId(final String userId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT b.id, b.ver, b.user_id, b.post_id, b.is_active ").append("FROM t_bookmark b ")
				.append("WHERE b.user_id = :userId ").append("AND b.is_active = TRUE ");

		final String sql = str.toString();
		final List<?> result = createNativeQuery(sql).setParameter("userId", userId).getResultList();

		final List<Bookmark> bookmarks = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(resultObj -> {
				final Object[] objArr = (Object[]) resultObj;
				final Bookmark bookmark = new Bookmark();
				bookmark.setId(objArr[0].toString());
				bookmark.setVersion(Integer.valueOf(objArr[1].toString()));

				final User user = new User();
				user.setId(objArr[2].toString());
				bookmark.setUser(user);

				final Post post = new Post();
				post.setId(objArr[3].toString());
				bookmark.setPost(post);

				bookmark.setIsActive(Boolean.valueOf(objArr[4].toString()));

				bookmarks.add(bookmark);
			});
		}

		return bookmarks;
	}

	public Long isBookmarked(final String postId, final String userId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT COUNT(b.id) ").append("FROM t_bookmark b ").append("WHERE b.is_active = TRUE ")
				.append("AND b.post_id = :postId ").append(" AND b.user_id = :userId ");

		Long total = null;
		try {
			final Object userObj = createNativeQuery(str.toString()).setParameter("postId", postId)
					.setParameter("userId", userId).getSingleResult();
			if (userObj != null) {
				total = Long.valueOf(userObj.toString());
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return total;
	}
	
	public String getByUserAndPost(final String postId, final String userId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT b.id ").append("FROM t_bookmark b ").append("WHERE b.is_active = TRUE ")
				.append("AND b.post_id = :postId ").append(" AND b.user_id = :userId ");

		String total = null;
		try {
			final Object userObj = createNativeQuery(str.toString()).setParameter("postId", postId)
					.setParameter("userId", userId).getSingleResult();
			if (userObj != null) {
				total = userObj.toString();
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return total;
	}
}