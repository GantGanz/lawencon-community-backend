package com.lawencon.community.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.LikeDao;

@Service
public class LikeService extends BaseCoreService {

	@Autowired
	private LikeDao likeDao;
	
	public Long countLike(final String postId) {
		return likeDao.countLike(postId);
	}
}
