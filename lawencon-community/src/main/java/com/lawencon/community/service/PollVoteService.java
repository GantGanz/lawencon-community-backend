package com.lawencon.community.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.PollOptionDao;
import com.lawencon.community.dao.PollVoteDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dto.InsertDataRes;
import com.lawencon.community.dto.InsertRes;
import com.lawencon.community.dto.pollvote.PollVoteInsertReq;
import com.lawencon.community.model.PollOption;
import com.lawencon.community.model.PollVote;
import com.lawencon.community.model.User;
import com.lawencon.security.principal.PrincipalService;

@Service
public class PollVoteService extends BaseCoreService {
	@Autowired
	private PollVoteDao pollVoteDao;
	@Autowired
	private PrincipalService principalService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private PollOptionDao pollOptionDao;
	
	public Long countVote(final String pollId) {
		return pollVoteDao.countPollVote(pollId);
	}
	
	public Long countVoteByPollOption(final String pollOptionId) {
		return pollVoteDao.countPollVoteByPollOption(pollOptionId);
	}
	
	public Boolean isVoted(final String pollId) {
		final String userId = principalService.getAuthPrincipal();
		Boolean status = false;
		if (pollVoteDao.isVoted(pollId, userId) > 0) {
			status = true;
		}
		return status;
	}
	
	public Boolean optionIsVoted(final String )
	
	public InsertRes insert(final PollVoteInsertReq data) {
		valInsert(data);

		PollVote pollVoteInsert = new PollVote();

		final String userId = principalService.getAuthPrincipal();
		final User user = userDao.getById(User.class, userId);
		pollVoteInsert.setUser(user);

		final PollOption pollOption = pollOptionDao.getById(PollOption.class, data.getPollOptionId());
		pollVoteInsert.setPollOption(pollOption);

		try {
			begin();
			pollVoteInsert = pollVoteDao.save(pollVoteInsert);
			commit();
		} catch (final Exception e) {
			e.printStackTrace();
			rollback();

		}
		final InsertDataRes dataRes = new InsertDataRes();
		dataRes.setId(pollVoteInsert.getId());

		final InsertRes insertRes = new InsertRes();
		insertRes.setData(dataRes);

		return insertRes;
	}
	
	private void valInsert(final PollVoteInsertReq data) {
		valIdFkNotNull(data);
		valFkFound(data);
	}

	private void valIdFkNotNull(final PollVoteInsertReq data) {
		if (data.getPollOptionId() == null) {
			throw new RuntimeException("Poll Option id cannot be empty");
		}
	}

	private void valFkFound(final PollVoteInsertReq data) {
		final String userId = principalService.getAuthPrincipal();
		final User user = userDao.getByIdAndDetach(User.class, userId);
		if (user == null) {
			throw new RuntimeException("User not found");
		}
		final PollOption pollOption = pollOptionDao.getByIdAndDetach(PollOption.class, data.getPollOptionId());
		if (pollOption == null) {
			throw new RuntimeException("Poll Option not found");
		}
	}
}
