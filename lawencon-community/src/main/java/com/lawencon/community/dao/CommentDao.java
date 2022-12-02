package com.lawencon.community.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.Comment;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Position;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.User;

@Repository
public class CommentDao extends AbstractJpaDao {

	public List<Comment> getAllByPostId(final String postId, final Integer offset, final Integer limit) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT c.id, c.ver, c.comment_content, u.fullname, c.post_id,")
				.append("c.created_by, c.created_at, c.updated_at, c.is_active, u.id as user_id, f.id as file_id, u.company, p.position_name ")
				.append("FROM t_comment c ").append("INNER JOIN t_user u ON u.id = c.created_by ").append("INNER JOIN t_position p ON p.id = u.position_id ")
				.append("INNER JOIN t_file f ON f.id = u.file_id ").append("WHERE c.post_id = :postId ")
				.append("AND c.is_active = TRUE ").append("ORDER BY c.created_at DESC");

		final String sql = str.toString();
		final List<?> result = createNativeQuery(sql, offset, limit).setParameter("postId", postId).getResultList();

		final List<Comment> comments = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(resultObj -> {
				final Object[] objArr = (Object[]) resultObj;
				final Comment comment = new Comment();
				comment.setId(objArr[0].toString());
				comment.setVersion(Integer.valueOf(objArr[1].toString()));
				comment.setCommentContent(objArr[2].toString());

				final User user = new User();
				user.setId(objArr[9].toString());
				user.setFullname(objArr[3].toString());

				final File file = new File();
				file.setId(objArr[10].toString());
				user.setFile(file);

				user.setCompany(objArr[11].toString());
				final Position position = new Position();
				position.setPositionName(objArr[12].toString());
				user.setPosition(position);
				comment.setUser(user);

				final Post post = new Post();
				post.setId(objArr[4].toString());
				comment.setPost(post);

				comment.setCreatedBy(objArr[5].toString());
				comment.setCreatedAt(Timestamp.valueOf(objArr[6].toString()).toLocalDateTime());
				if (objArr[7] != null) {
					comment.setUpdatedAt(Timestamp.valueOf(objArr[7].toString()).toLocalDateTime());
				}
				comment.setIsActive(Boolean.valueOf(objArr[8].toString()));

				comments.add(comment);
			});
		}

		return comments;
	}

	public List<Comment> getAllByCommentId(final String commentId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT c.id, c.ver, c.comment_content, u.fullname, c.comment_id,")
				.append("c.created_by, c.created_at, c.updated_at, c.is_active ").append("FROM t_comment c ")
				.append("INNER JOIN t_user u ON u.id = c.created_by ").append("WHERE c.comment_id = :commentId ")
				.append("AND c.is_active = TRUE ").append("ORDER BY c.created_at DESC");

		final String sql = str.toString();
		final List<?> result = createNativeQuery(sql).setParameter("commentId", commentId).getResultList();

		final List<Comment> comments = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(resultObj -> {
				final Object[] objArr = (Object[]) resultObj;
				final Comment comment = new Comment();
				comment.setId(objArr[0].toString());
				comment.setVersion(Integer.valueOf(objArr[1].toString()));
				comment.setCommentContent(objArr[2].toString());

				final User user = new User();
				user.setFullname(objArr[3].toString());
				comment.setUser(user);

				final Post post = new Post();
				post.setId(objArr[4].toString());
				comment.setPost(post);

				comment.setCreatedBy(objArr[5].toString());
				comment.setCreatedAt(Timestamp.valueOf(objArr[6].toString()).toLocalDateTime());
				if (objArr[7] != null) {
					comment.setUpdatedAt(Timestamp.valueOf(objArr[7].toString()).toLocalDateTime());
				}
				comment.setIsActive(Boolean.valueOf(objArr[8].toString()));

				comments.add(comment);
			});
		}

		return comments;
	}

	public Long countComment(final String postId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT COUNT(c.id) ").append("FROM t_comment c ").append("INNER JOIN t_post p ON c.post_id = p.id ")
				.append("WHERE c.is_active = TRUE ").append("AND p.id = :postId ");

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