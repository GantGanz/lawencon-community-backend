package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.LikeDao;
import com.lawencon.community.dao.PostDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dto.InsertDataRes;
import com.lawencon.community.dto.InsertRes;
import com.lawencon.community.dto.UpdateDataRes;
import com.lawencon.community.dto.UpdateRes;
import com.lawencon.community.dto.like.LikeData;
import com.lawencon.community.dto.like.LikeInsertReq;
import com.lawencon.community.dto.like.LikeRes;
import com.lawencon.community.dto.like.LikeUpdateReq;
import com.lawencon.community.dto.like.LikesRes;
import com.lawencon.community.model.Like;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.User;
import com.lawencon.security.principal.PrincipalService;

@Service
public class LikeService extends BaseCoreService {

	@Autowired
	private LikeDao likeDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private PostDao postDao;
	@Autowired
	private PrincipalService principalService;

	public Long countLike(final String postId) {
		return likeDao.countLike(postId);
	}

	public InsertRes insert(final LikeInsertReq data) {
		valInsert(data);

		Like likeInsert = new Like();

		final String userId = principalService.getAuthPrincipal();
		final User user = userDao.getById(User.class, userId);
		likeInsert.setUser(user);

		final Post post = postDao.getById(Post.class, data.getPostId());
		likeInsert.setPost(post);

		try {
			begin();
			likeInsert = likeDao.save(likeInsert);
			commit();
		} catch (final Exception e) {
			e.printStackTrace();
			rollback();

		}
		final InsertDataRes dataRes = new InsertDataRes();
		dataRes.setId(likeInsert.getId());

		final InsertRes insertRes = new InsertRes();
		insertRes.setData(dataRes);

		return insertRes;
	}

	public UpdateRes softDelete(final LikeUpdateReq data) {
		valUpdate(data);
		Like like = likeDao.getByIdAndDetach(Like.class, data.getId());
		like.setIsActive(false);
		like.setVersion(data.getVersion());

		try {
			begin();
			like = likeDao.saveAndFlush(like);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new RuntimeException("Soft Delete failed");
		}
		final UpdateDataRes dataRes = new UpdateDataRes();
		dataRes.setVersion(like.getVersion());

		final UpdateRes res = new UpdateRes();
		res.setData(dataRes);
		res.setMessage("Soft Delete success");
		return res;
	}

	public UpdateRes update(final LikeUpdateReq data) {
		valUpdate(data);
		Like like = likeDao.getByIdAndDetach(Like.class, data.getId());
		like.setIsActive(true);
		like.setVersion(data.getVersion());
		try {
			begin();
			like = likeDao.saveAndFlush(like);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new RuntimeException("Like failed");
		}
		final UpdateDataRes dataRes = new UpdateDataRes();
		dataRes.setVersion(like.getVersion());

		final UpdateRes res = new UpdateRes();
		res.setData(dataRes);
		res.setMessage("Liked");
		return res;
	}

	public LikesRes getAllByUserId() {
		final String userId = principalService.getAuthPrincipal();
		final List<Like> likes = likeDao.getAllByUserId(userId);
		final List<LikeData> likeDatas = new ArrayList<>();
		for (int i = 0; i < likes.size(); i++) {
			final Like like = likes.get(i);
			final LikeData likeData = new LikeData();
			likeData.setId(like.getId());
			likeData.setUserId(like.getUser().getId());
			likeData.setPostId(like.getPost().getId());
			likeDatas.add(likeData);
		}
		final LikesRes likesRes = new LikesRes();
		likesRes.setData(likeDatas);

		return likesRes;
	}

	public LikeRes getById(final String id) {
		final Like like = likeDao.getById(Like.class, id);
		final LikeData likeData = new LikeData();
		likeData.setId(like.getId());
		likeData.setUserId(like.getUser().getId());
		likeData.setPostId(like.getPost().getId());

		final LikeRes likeRes = new LikeRes();
		likeRes.setData(likeData);

		return likeRes;
	}

	public Boolean isLiked(final String postId) {
		final String userId = principalService.getAuthPrincipal();
		Boolean status = false;
		if (likeDao.isLiked(postId, userId) > 0) {
			status = true;
		}
		return status;
	}

	public String getByUserAndPost(final String postId) {
		final String userId = principalService.getAuthPrincipal();
		return likeDao.getByUserAndPost(postId, userId);
	}

	public Boolean delete(final String likeId) {
		try {
			begin();
			likeDao.deleteById(Like.class, likeId);
			commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return false;
	}

	private void valInsert(final LikeInsertReq data) {
		valIdFkNotNull(data);
		valFkFound(data);
	}

	private void valIdFkNotNull(final LikeInsertReq data) {
		if (data.getUserId() == null) {
			throw new RuntimeException("User id cannot be empty");
		}
		if (data.getPostId() == null) {
			throw new RuntimeException("Industry id cannot be empty");
		}
	}

	private void valFkFound(final LikeInsertReq data) {
		final User user = userDao.getByIdAndDetach(User.class, data.getUserId());
		if (user == null) {
			throw new RuntimeException("User not found");
		}
		final Post post = postDao.getByIdAndDetach(Post.class, data.getPostId());
		if (post == null) {
			throw new RuntimeException("Post not found");
		}
	}

	private void valUpdate(final LikeUpdateReq data) {
		valIdNotNull(data);
		valIdFound(data);
		valContentNotNull(data);
	}

	private void valIdNotNull(final LikeUpdateReq data) {
		if (data.getId() == null) {
			throw new RuntimeException("id cannot be empty");
		}
	}

	private void valContentNotNull(final LikeUpdateReq data) {
		if (data.getVersion() == null) {
			throw new RuntimeException("Version cannot be empty");
		}
	}

	private void valIdFound(final LikeUpdateReq data) {
		final Like like = likeDao.getById(Like.class, data.getId());
		if (like == null) {
			throw new RuntimeException("Like not found");
		}
	}
}
