package com.lawencon.community.dao;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;

@Repository
public class PollVoteDao extends AbstractJpaDao {
	public Long countPollVote(final String pollId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT COUNT(v.id) ").append("FROM t_poll p ")
				.append("INNER JOIN t_poll_option o ON o.poll_id = p.id ")
				.append("INNER JOIN t_poll_vote v ON v.poll_option_id = o.id ").append("WHERE o.poll_id = :pollId ");

		Long total = null;
		try {
			final Object userObj = createNativeQuery(str.toString()).setParameter("pollId", pollId).getSingleResult();
			if (userObj != null) {
				total = Long.valueOf(userObj.toString());
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	public Long countPollVoteByPollOption(final String pollOptionId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT COUNT(v.id) ").append("FROM t_poll p ")
				.append("INNER JOIN t_poll_option o ON o.poll_id = p.id ")
				.append("INNER JOIN t_poll_vote v ON v.poll_option_id = o.id ").append("WHERE o.id = :pollOptionId ");

		Long total = null;
		try {
			final Object userObj = createNativeQuery(str.toString()).setParameter("pollOptionId", pollOptionId)
					.getSingleResult();
			if (userObj != null) {
				total = Long.valueOf(userObj.toString());
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	public Long isVoted(final String pollId, final String userId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT COUNT(v.id) ").append("FROM t_poll p ")
				.append("INNER JOIN t_poll_option o ON o.poll_id = p.id ")
				.append("INNER JOIN t_poll_vote v ON v.poll_option_id = o.id ").append("WHERE p.id = :pollId ")
				.append(" AND v.created_by = :userId ");

		Long total = null;
		try {
			final Object userObj = createNativeQuery(str.toString()).setParameter("pollId", pollId)
					.setParameter("userId", userId).getSingleResult();
			if (userObj != null) {
				total = Long.valueOf(userObj.toString());
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	public Long isVotedOption(final String pollOptionId, final String userId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT COUNT(v.id) ").append("FROM t_poll p ")
				.append("INNER JOIN t_poll_option o ON o.poll_id = p.id ")
				.append("INNER JOIN t_poll_vote v ON v.poll_option_id = o.id ").append("WHERE o.id = :pollOptionId ")
				.append(" AND v.created_by = :userId ");

		Long total = null;
		try {
			final Object userObj = createNativeQuery(str.toString()).setParameter("pollOptionId", pollOptionId)
					.setParameter("userId", userId).getSingleResult();
			if (userObj != null) {
				total = Long.valueOf(userObj.toString());
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return total;
	}
}