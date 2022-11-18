package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.constant.PostTypeConstant;
import com.lawencon.community.dao.AttachmentPostDao;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.PollDao;
import com.lawencon.community.dao.PollOptionDao;
import com.lawencon.community.dao.PostDao;
import com.lawencon.community.dao.PostTypeDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dto.InsertDataRes;
import com.lawencon.community.dto.InsertRes;
import com.lawencon.community.dto.UpdateDataRes;
import com.lawencon.community.dto.UpdateRes;
import com.lawencon.community.dto.attachmentpost.AttachmentPostInsertReq;
import com.lawencon.community.dto.poll.PollInsertReq;
import com.lawencon.community.dto.polloption.PollOptionInsertReq;
import com.lawencon.community.dto.post.PostData;
import com.lawencon.community.dto.post.PostInsertReq;
import com.lawencon.community.dto.post.PostUpdateReq;
import com.lawencon.community.dto.post.PostsRes;
import com.lawencon.community.model.AttachmentPost;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Poll;
import com.lawencon.community.model.PollOption;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.PostType;
import com.lawencon.community.model.User;
import com.lawencon.security.principal.PrincipalService;

@Service
public class PostService extends BaseCoreService {
	@Autowired
	private PostDao postDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private PostTypeDao postTypeDao;
	@Autowired
	private AttachmentPostDao attachmentPostDao;
	@Autowired
	private FileDao fileDao;
	@Autowired
	private PollDao pollDao;
	@Autowired
	private PollOptionDao pollOptionDao;
	@Autowired
	private PrincipalService principalService;

	public InsertRes insert(final PostInsertReq data) {
		valInsert(data);

		Post postInsert = new Post();
		postInsert.setPostTitle(data.getPostTitle());
		postInsert.setPostContent(data.getPostContent());

		final String userId = principalService.getAuthPrincipal();
		final User user = userDao.getById(User.class, userId);
		postInsert.setUser(user);

		final PostType postType = postTypeDao.getById(PostType.class, data.getPostTypeId());
		postInsert.setPostType(postType);

		if (postType.getPostTypeCode() == PostTypeConstant.POLLING.getPostTypeCode()) {
			try {
				begin();
				postInsert = postDao.save(postInsert);

				final PollInsertReq pollInsertReq = data.getPollInsertReq();
				final Poll poll = new Poll();
				poll.setPost(postInsert);
				poll.setPollTitle(pollInsertReq.getPollTitle());
				poll.setEndAt(pollInsertReq.getEndAt());
				final Poll pollInsert = pollDao.save(poll);
				
				final int pollOptionSize = pollInsertReq.getPollOptionInsertReqs().size();
				
				for (int i = 0; i < pollOptionSize; i++) {
					final PollOptionInsertReq pollOptionInsertReq = pollInsertReq.getPollOptionInsertReqs().get(i);
					final PollOption pollOption = new PollOption();

					pollOption.setPoll(pollInsert);
					pollOption.setPollContent(pollOptionInsertReq.getPollContent());

					pollOptionDao.save(pollOption);
				}
				commit();
			} catch (final Exception e) {
				e.printStackTrace();
				rollback();
				throw new RuntimeException("failed to create poll");
			}
		} else {
			final int attachmentSize = data.getAttachmentPostInsertReqs().size();
			try {
				begin();
				postInsert = postDao.save(postInsert);
				for (int i = 0; i < attachmentSize; i++) {
					final AttachmentPostInsertReq attachmentPostInsertReq = data.getAttachmentPostInsertReqs().get(i);
					final AttachmentPost attachmentPost = new AttachmentPost();

					attachmentPost.setPost(postInsert);

					final File file = fileDao.getById(File.class, attachmentPostInsertReq.getFileId());
					attachmentPost.setFile(file);

					attachmentPostDao.save(attachmentPost);
				}
				commit();
			} catch (final Exception e) {
				e.printStackTrace();
				rollback();
				throw new RuntimeException("failed to create post");
			}
		}
		final InsertDataRes dataRes = new InsertDataRes();
		dataRes.setId(postInsert.getId());

		final InsertRes insertRes = new InsertRes();
		insertRes.setData(dataRes);
		insertRes.setMessage("post created");

		return insertRes;
	}

	public UpdateRes update(final PostUpdateReq data) {
		valUpdate(data);
		Post post = postDao.getByIdAndDetach(Post.class, data.getId());
		post.setPostTitle(data.getPostTitle());
		post.setPostContent(data.getPostContent());
		post.setIsActive(data.getIsActive());
		post.setVersion(data.getVersion());

		final PostType postType = postTypeDao.getById(PostType.class, data.getPostTypeId());
		post.setPostType(postType);

		try {
			begin();
			post = postDao.saveAndFlush(post);
			commit();
		} catch (final Exception e) {
			e.printStackTrace();
			rollback();
			throw new RuntimeException("Update failed");
		}
		final UpdateDataRes dataRes = new UpdateDataRes();
		dataRes.setVersion(post.getVersion());

		final UpdateRes res = new UpdateRes();
		res.setData(dataRes);
		res.setMessage("Update success");
		return res;
	}

	public PostsRes getAll() {
		final List<Post> posts = postDao.getAll(Post.class);
		final List<PostData> postDatas = new ArrayList<>();
		for (int i = 0; i < posts.size(); i++) {
			final Post post = posts.get(i);
			final PostData postData = new PostData();
			postData.setId(post.getId());
			postData.setPostTitle(post.getPostTitle());
			postData.setPostContent(post.getPostContent());
			postData.setPostTypeId(post.getPostType().getId());
			postData.setCreatedBy(post.getCreatedBy());
			postData.setCreatedAt(post.getCreatedAt());
			postData.setVersion(post.getVersion());

			postDatas.add(postData);
		}
		final PostsRes postsRes = new PostsRes();
		postsRes.setData(postDatas);

		return postsRes;
	}

	public PostsRes getAllRegular() {
		final List<Post> posts = postDao.getAllRegular();
		final List<PostData> postDatas = new ArrayList<>();
		for (int i = 0; i < posts.size(); i++) {
			final Post post = posts.get(i);
			final PostData postData = new PostData();
			postData.setId(post.getId());
			postData.setPostTitle(post.getId());
			postData.setPostContent(post.getPostContent());
			postData.setPostTypeId(post.getPostType().getId());
			postData.setCreatedAt(post.getCreatedAt());
			postData.setCreatedBy(post.getCreatedBy());
			postData.setVersion(post.getVersion());

			postDatas.add(postData);
		}
		final PostsRes postsRes = new PostsRes();
		postsRes.setData(postDatas);

		return postsRes;
	}

	public PostsRes getAllRegularById() {
		final List<Post> posts = postDao.getAllRegularById(principalService.getAuthPrincipal());
		final List<PostData> postDatas = new ArrayList<>();
		for (int i = 0; i < posts.size(); i++) {
			final Post post = posts.get(i);
			final PostData postData = new PostData();
			postData.setId(post.getId());
			postData.setPostTitle(post.getId());
			postData.setPostContent(post.getPostContent());
			postData.setPostTypeId(post.getPostType().getId());
			postData.setCreatedAt(post.getCreatedAt());
			postData.setCreatedBy(post.getCreatedBy());
			postData.setVersion(post.getVersion());

			postDatas.add(postData);
		}
		final PostsRes postsRes = new PostsRes();
		postsRes.setData(postDatas);

		return postsRes;
	}

	private void valInsert(final PostInsertReq data) {
		valContentNotNull(data);
	}

	private void valContentNotNull(final PostInsertReq data) {
		if (data.getPostTitle() == null) {
			throw new RuntimeException("Title cannot be empty");
		}
		if (data.getPostContent() == null) {
			throw new RuntimeException("Content cannot be empty");
		}
	}

	private void valUpdate(final PostUpdateReq data) {
		valIdNotNull(data);
		valIdFound(data);
		valContentNotNull(data);
	}

	private void valIdNotNull(final PostUpdateReq data) {
		if (data.getId() == null) {
			throw new RuntimeException("id cannot be empty");
		}
	}

	private void valContentNotNull(final PostUpdateReq data) {
		if (data.getPostTitle() == null) {
			throw new RuntimeException("Title cannot be empty");
		}
		if (data.getPostContent() == null) {
			throw new RuntimeException("Content cannot be empty");
		}
		if (data.getPostTypeId() == null) {
			throw new RuntimeException("Post type cannot be empty");
		}
		if (data.getIsActive() == null) {
			throw new RuntimeException("isActive cannot be empty");
		}
		if (data.getVersion() == null) {
			throw new RuntimeException("Version cannot be empty");
		}
	}

	private void valIdFound(final PostUpdateReq data) {
		final Post post = postDao.getById(Post.class, data.getId());
		if (post == null) {
			throw new RuntimeException("post not found");
		}
	}
}
