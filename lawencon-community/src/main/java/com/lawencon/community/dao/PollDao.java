package com.lawencon.community.dao;
import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.Poll;
import com.lawencon.community.model.Post;

@Repository
public class PollDao extends AbstractJpaDao {

	public Optional<Poll> getByPostId(final String postId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT p.id, p.poll_title, p.end_at, p.post_id, p.is_active ")
				.append("FROM t_poll p ")
				.append("WHERE p.post_id = :postId ")
				.append("AND p.is_active = TRUE ");

		Poll poll = null;
		try {
			final Object pollObj = createNativeQuery(str.toString()).setParameter("postId", postId).getSingleResult();

			if (pollObj != null) {
				final Object[] objArr = (Object[]) pollObj;
				poll = new Poll();
				poll.setId(objArr[0].toString());
				poll.setPollTitle(objArr[1].toString());
				poll.setEndAt(Timestamp.valueOf(objArr[2].toString()).toLocalDateTime());
				
				final Post post = new Post();
				post.setId(objArr[3].toString());
				poll.setPost(post);				
				
				poll.setIsActive(Boolean.valueOf(objArr[4].toString()));
			}

		} catch (final Exception e) {
			e.printStackTrace();
		}
		Optional<Poll> pollOptional = Optional.ofNullable(poll);
		return pollOptional;
	}
	
}