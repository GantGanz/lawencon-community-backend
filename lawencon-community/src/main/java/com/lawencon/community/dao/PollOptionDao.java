package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.Poll;
import com.lawencon.community.model.PollOption;

@Repository
public class PollOptionDao extends AbstractJpaDao {

	public List<PollOption> getAllByPollId(final String pollId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT p.id, p.poll_content, p.poll_id ").append("FROM t_poll_option p ")
				.append("WHERE p.poll_id = :pollId ").append("ORDER BY p.created_at");

		final String sql = str.toString();
		final List<?> result = createNativeQuery(sql).setParameter("pollId", pollId).getResultList();

		final List<PollOption> pollOptions = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(resultObj -> {
				final Object[] objArr = (Object[]) resultObj;
				final PollOption pollOption = new PollOption();
				pollOption.setId(objArr[0].toString());
				pollOption.setPollContent(objArr[1].toString());

				final Poll poll = new Poll();
				poll.setId(objArr[2].toString());
				pollOption.setPoll(poll);

				pollOptions.add(pollOption);
			});
		}

		return pollOptions;
	}
}