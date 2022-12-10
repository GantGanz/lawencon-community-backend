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
import com.lawencon.community.dto.attachmentpost.AttachmentPostData;
import com.lawencon.community.dto.attachmentpost.AttachmentPostInsertReq;
import com.lawencon.community.dto.poll.PollData;
import com.lawencon.community.dto.poll.PollInsertReq;
import com.lawencon.community.dto.polloption.PollOptionData;
import com.lawencon.community.dto.polloption.PollOptionInsertReq;
import com.lawencon.community.dto.post.PostData;
import com.lawencon.community.dto.post.PostInsertReq;
import com.lawencon.community.dto.post.PostRes;
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
	@Autowired
	private LikeService likeService;
	@Autowired
	private PollVoteService pollVoteService;
	@Autowired
	private BookmarkService bookmarkService;
	@Autowired
	private CommentService commentService;

	public Long countAll() {
		return postDao.countAll();
	}

	public Long countMyPost() {
		return postDao.countMyPost(principalService.getAuthPrincipal());
	}

	public Long countLiked() {
		return postDao.countLiked(principalService.getAuthPrincipal());
	}

	public Long countBookmarked() {
		return postDao.countBookmarked(principalService.getAuthPrincipal());
	}

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

		if (PostTypeConstant.POLLING.getPostTypeCode().equals(postType.getPostTypeCode())) {
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

					File fileInsert = new File();
					fileInsert.setFileCodes(attachmentPostInsertReq.getFileCodes());
					fileInsert.setExtensions(attachmentPostInsertReq.getExtensions());

					fileInsert = fileDao.save(fileInsert);
					attachmentPost.setFile(fileInsert);

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

		return insertRes;
	}

	public UpdateRes update(final PostUpdateReq data) {
		valUpdate(data);
		Post post = postDao.getByIdAndDetach(Post.class, data.getId());
		post.setPostTitle(data.getPostTitle());
		post.setPostContent(data.getPostContent());
		post.setIsActive(data.getIsActive());
		post.setVersion(data.getVersion());

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

	public PostsRes getAll(final Integer offset, final Integer limit) {
		final List<Post> posts = postDao.getAll(offset, limit);
		final List<PostData> postDatas = new ArrayList<>();
		for (int i = 0; i < posts.size(); i++) {
			final Post post = posts.get(i);
			final PostData postData = new PostData();
			postData.setId(post.getId());
			postData.setVersion(post.getVersion());
			postData.setPostTitle(post.getPostTitle());
			postData.setPostContent(post.getPostContent());
			postData.setPostTypeId(post.getPostType().getId());
			postData.setUserId(post.getUser().getId());
			postData.setCreatorName(post.getUser().getFullname());
			postData.setCreatedBy(post.getCreatedBy());
			postData.setCreatedAt(post.getCreatedAt());
			postData.setUpdatedAt(post.getUpdatedAt());
			postData.setCompany(post.getUser().getCompany());
			postData.setPositionName(post.getUser().getPosition().getPositionName());
			postData.setFileId(post.getUser().getFile().getId());
			postData.setCountComment(commentService.countComment(post.getId()));

			final Long countLike = likeService.countLike(post.getId());
			postData.setCountLike(countLike);

			final Boolean isLiked = likeService.isLiked(post.getId());
			postData.setIsLiked(isLiked);

			final Boolean isBookmarked = bookmarkService.isBookmarked(post.getId());
			postData.setIsBookmarked(isBookmarked);

			final String postTypeCode = post.getPostType().getPostTypeCode();
			postData.setPostTypeCode(postTypeCode);

			if (PostTypeConstant.POLLING.getPostTypeCode().equals(postTypeCode)) {
				final Poll poll = pollDao.getByPostId(post.getId()).get();
				final PollData pollData = new PollData();

				pollData.setId(poll.getId());
				pollData.setPollTitle(poll.getPollTitle());
				pollData.setEndAt(poll.getEndAt());
				pollData.setPostId(poll.getPost().getId());
				pollData.setIsActive(poll.getIsActive());

				final Long countVote = pollVoteService.countVote(poll.getId());
				pollData.setCountVote(countVote);

				final Boolean isVoted = pollVoteService.isVoted(poll.getId());
				pollData.setIsVoted(isVoted);

				final List<PollOption> pollOptions = pollOptionDao.getAllByPollId(pollData.getId());
				final int pollOptionSize = pollOptions.size();
				final List<PollOptionData> pollOptionDatas = new ArrayList<>();
				for (int x = 0; x < pollOptionSize; x++) {
					final PollOption pollOption = pollOptions.get(x);
					final PollOptionData pollOptionData = new PollOptionData();
					pollOptionData.setId(pollOption.getId());
					pollOptionData.setPollId(pollOption.getPoll().getId());
					pollOptionData.setPollContent(pollOption.getPollContent());
					pollOptionData.setVersion(pollOption.getVersion());
					pollOptionData.setIsActive(pollOption.getIsActive());

					final Boolean optionIsVoted = pollVoteService.optionIsVoted(pollOption.getId());
					pollOptionData.setIsVoted(optionIsVoted);

					final Long countOptionVote = pollVoteService.countVoteByPollOption(pollOption.getId());
					pollOptionData.setCountVote(countOptionVote);

					pollOptionDatas.add(pollOptionData);
				}
				pollData.setPollOptionDatas(pollOptionDatas);

				postData.setPollData(pollData);
			} else {
				final List<AttachmentPost> attachmentPosts = attachmentPostDao.getAllById(post.getId());
				final int attachmentPostSize = attachmentPosts.size();
				final List<AttachmentPostData> attachmentPostDatas = new ArrayList<>();
				for (int x = 0; x < attachmentPostSize; x++) {
					final AttachmentPost attachmentPost = attachmentPosts.get(x);
					final AttachmentPostData attachmentPostData = new AttachmentPostData();
					attachmentPostData.setId(attachmentPost.getId());
					attachmentPostData.setPostId(attachmentPost.getPost().getId());
					attachmentPostData.setFileId(attachmentPost.getFile().getId());

					attachmentPostDatas.add(attachmentPostData);
				}
				postData.setAttachmentPostDatas(attachmentPostDatas);
			}

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
			postData.setVersion(post.getVersion());
			postData.setPostTitle(post.getPostTitle());
			postData.setPostContent(post.getPostContent());
			postData.setPostTypeCode(post.getPostType().getPostTypeCode());
			postData.setUserId(post.getUser().getId());
			postData.setCreatorName(post.getUser().getFullname());
			postData.setCreatedBy(post.getCreatedBy());
			postData.setCreatedAt(post.getCreatedAt());
			postData.setUpdatedAt(post.getUpdatedAt());

			final List<AttachmentPost> attachmentPosts = attachmentPostDao.getAllById(post.getId());
			final int attachmentPostSize = attachmentPosts.size();
			final List<AttachmentPostData> attachmentPostDatas = new ArrayList<>();
			for (int x = 0; x < attachmentPostSize; x++) {
				final AttachmentPost attachmentPost = attachmentPosts.get(x);
				final AttachmentPostData attachmentPostData = new AttachmentPostData();
				attachmentPostData.setId(attachmentPost.getId());
				attachmentPostData.setPostId(attachmentPost.getPost().getId());
				attachmentPostData.setFileId(attachmentPost.getFile().getId());

				attachmentPostDatas.add(attachmentPostData);
			}
			postData.setAttachmentPostDatas(attachmentPostDatas);

			postDatas.add(postData);
		}
		final PostsRes postsRes = new PostsRes();
		postsRes.setData(postDatas);

		return postsRes;
	}

	public PostsRes getAllPolling() {
		final List<Post> posts = postDao.getAllPolling();
		final List<PostData> postDatas = new ArrayList<>();
		for (int i = 0; i < posts.size(); i++) {
			final Post post = posts.get(i);
			final PostData postData = new PostData();
			postData.setId(post.getId());
			postData.setVersion(post.getVersion());
			postData.setPostTitle(post.getPostTitle());
			postData.setPostContent(post.getPostContent());
			postData.setPostTypeId(post.getPostType().getId());
			postData.setUserId(post.getUser().getId());
			postData.setCreatorName(post.getUser().getFullname());
			postData.setCreatedBy(post.getCreatedBy());
			postData.setCreatedAt(post.getCreatedAt());
			postData.setUpdatedAt(post.getUpdatedAt());

			final Poll poll = pollDao.getByPostId(post.getId()).get();
			final PollData pollData = new PollData();
			pollData.setId(poll.getId());
			pollData.setPollTitle(poll.getPollTitle());
			pollData.setEndAt(poll.getEndAt());
			pollData.setPostId(poll.getPost().getId());
			pollData.setIsActive(poll.getIsActive());

			final List<PollOption> pollOptions = pollOptionDao.getAllByPollId(pollData.getId());
			final int pollOptionSize = pollOptions.size();
			final List<PollOptionData> pollOptionDatas = new ArrayList<>();
			for (int x = 0; x < pollOptionSize; x++) {
				final PollOption pollOption = pollOptions.get(x);
				final PollOptionData pollOptionData = new PollOptionData();
				pollOptionData.setId(pollOption.getId());
				pollOptionData.setPollId(pollOption.getPoll().getId());
				pollOptionData.setPollContent(pollOption.getPollContent());
				pollOptionData.setVersion(pollOption.getVersion());
				pollOptionData.setIsActive(pollOption.getIsActive());

				pollOptionDatas.add(pollOptionData);
			}
			pollData.setPollOptionDatas(pollOptionDatas);

			postData.setPollData(pollData);

			postDatas.add(postData);
		}
		final PostsRes postsRes = new PostsRes();
		postsRes.setData(postDatas);

		return postsRes;
	}

	public PostsRes getAllPremium() {
		final List<Post> posts = postDao.getAllPremium();
		final List<PostData> postDatas = new ArrayList<>();
		for (int i = 0; i < posts.size(); i++) {
			final Post post = posts.get(i);
			final PostData postData = new PostData();
			postData.setId(post.getId());
			postData.setVersion(post.getVersion());
			postData.setPostTitle(post.getPostTitle());
			postData.setPostContent(post.getPostContent());
			postData.setPostTypeId(post.getPostType().getId());
			postData.setUserId(post.getUser().getId());
			postData.setCreatorName(post.getUser().getFullname());
			postData.setCreatedBy(post.getCreatedBy());
			postData.setCreatedAt(post.getCreatedAt());
			postData.setUpdatedAt(post.getUpdatedAt());

			final List<AttachmentPost> attachmentPosts = attachmentPostDao.getAllById(post.getId());
			final int attachmentPostSize = attachmentPosts.size();
			final List<AttachmentPostData> attachmentPostDatas = new ArrayList<>();
			for (int x = 0; x < attachmentPostSize; x++) {
				final AttachmentPost attachmentPost = attachmentPosts.get(x);
				final AttachmentPostData attachmentPostData = new AttachmentPostData();
				attachmentPostData.setId(attachmentPost.getId());
				attachmentPostData.setPostId(attachmentPost.getPost().getId());
				attachmentPostData.setFileId(attachmentPost.getFile().getId());

				attachmentPostDatas.add(attachmentPostData);
			}
			postData.setAttachmentPostDatas(attachmentPostDatas);

			postDatas.add(postData);
		}
		final PostsRes postsRes = new PostsRes();
		postsRes.setData(postDatas);

		return postsRes;
	}

	public PostsRes getAllById(final Integer offset, final Integer limit) {
		final List<Post> posts = postDao.getAllById(principalService.getAuthPrincipal(), offset, limit);
		final List<PostData> postDatas = new ArrayList<>();
		for (int i = 0; i < posts.size(); i++) {
			final Post post = posts.get(i);
			final PostData postData = new PostData();
			postData.setId(post.getId());
			postData.setVersion(post.getVersion());
			postData.setPostTitle(post.getPostTitle());
			postData.setPostContent(post.getPostContent());
			postData.setPostTypeId(post.getPostType().getId());
			postData.setUserId(post.getUser().getId());
			postData.setCreatorName(post.getUser().getFullname());
			postData.setCreatedBy(post.getCreatedBy());
			postData.setCreatedAt(post.getCreatedAt());
			postData.setUpdatedAt(post.getUpdatedAt());
			postData.setCompany(post.getUser().getCompany());
			postData.setPositionName(post.getUser().getPosition().getPositionName());
			postData.setFileId(post.getUser().getFile().getId());
			postData.setCountComment(commentService.countComment(post.getId()));

			final Long countLike = likeService.countLike(post.getId());
			postData.setCountLike(countLike);

			final Boolean isLiked = likeService.isLiked(post.getId());
			postData.setIsLiked(isLiked);

			final Boolean isBookmarked = bookmarkService.isBookmarked(post.getId());
			postData.setIsBookmarked(isBookmarked);

			final String postTypeCode = post.getPostType().getPostTypeCode();
			postData.setPostTypeCode(postTypeCode);

			if (PostTypeConstant.POLLING.getPostTypeCode().equals(postTypeCode)) {
				final Poll poll = pollDao.getByPostId(post.getId()).get();
				final PollData pollData = new PollData();

				pollData.setId(poll.getId());
				pollData.setPollTitle(poll.getPollTitle());
				pollData.setEndAt(poll.getEndAt());
				pollData.setPostId(poll.getPost().getId());
				pollData.setIsActive(poll.getIsActive());

				final Long countVote = pollVoteService.countVote(poll.getId());
				pollData.setCountVote(countVote);

				final Boolean isVoted = pollVoteService.isVoted(poll.getId());
				pollData.setIsVoted(isVoted);

				final List<PollOption> pollOptions = pollOptionDao.getAllByPollId(pollData.getId());
				final int pollOptionSize = pollOptions.size();
				final List<PollOptionData> pollOptionDatas = new ArrayList<>();
				for (int x = 0; x < pollOptionSize; x++) {
					final PollOption pollOption = pollOptions.get(x);
					final PollOptionData pollOptionData = new PollOptionData();
					pollOptionData.setId(pollOption.getId());
					pollOptionData.setPollId(pollOption.getPoll().getId());
					pollOptionData.setPollContent(pollOption.getPollContent());
					pollOptionData.setVersion(pollOption.getVersion());
					pollOptionData.setIsActive(pollOption.getIsActive());

					final Boolean optionIsVoted = pollVoteService.optionIsVoted(pollOption.getId());
					pollOptionData.setIsVoted(optionIsVoted);

					final Long countOptionVote = pollVoteService.countVoteByPollOption(pollOption.getId());
					pollOptionData.setCountVote(countOptionVote);

					pollOptionDatas.add(pollOptionData);
				}
				pollData.setPollOptionDatas(pollOptionDatas);

				postData.setPollData(pollData);
			} else {
				final List<AttachmentPost> attachmentPosts = attachmentPostDao.getAllById(post.getId());
				final int attachmentPostSize = attachmentPosts.size();
				final List<AttachmentPostData> attachmentPostDatas = new ArrayList<>();
				for (int x = 0; x < attachmentPostSize; x++) {
					final AttachmentPost attachmentPost = attachmentPosts.get(x);
					final AttachmentPostData attachmentPostData = new AttachmentPostData();
					attachmentPostData.setId(attachmentPost.getId());
					attachmentPostData.setPostId(attachmentPost.getPost().getId());
					attachmentPostData.setFileId(attachmentPost.getFile().getId());

					attachmentPostDatas.add(attachmentPostData);
				}
				postData.setAttachmentPostDatas(attachmentPostDatas);
			}

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
			postData.setVersion(post.getVersion());
			postData.setPostTitle(post.getPostTitle());
			postData.setPostContent(post.getPostContent());
			postData.setPostTypeId(post.getPostType().getId());
			postData.setUserId(post.getUser().getId());
			postData.setCreatorName(post.getUser().getFullname());
			postData.setCreatedBy(post.getCreatedBy());
			postData.setCreatedAt(post.getCreatedAt());
			postData.setUpdatedAt(post.getUpdatedAt());

			final List<AttachmentPost> attachmentPosts = attachmentPostDao.getAllById(post.getId());
			final int attachmentPostSize = attachmentPosts.size();
			final List<AttachmentPostData> attachmentPostDatas = new ArrayList<>();
			for (int x = 0; x < attachmentPostSize; x++) {
				final AttachmentPost attachmentPost = attachmentPosts.get(x);
				final AttachmentPostData attachmentPostData = new AttachmentPostData();
				attachmentPostData.setId(attachmentPost.getId());
				attachmentPostData.setPostId(attachmentPost.getPost().getId());
				attachmentPostData.setFileId(attachmentPost.getFile().getId());

				attachmentPostDatas.add(attachmentPostData);
			}
			postData.setAttachmentPostDatas(attachmentPostDatas);

			postDatas.add(postData);
		}
		final PostsRes postsRes = new PostsRes();
		postsRes.setData(postDatas);

		return postsRes;
	}

	public PostsRes getAllPollingById() {
		final List<Post> posts = postDao.getAllPollingById(principalService.getAuthPrincipal());
		final List<PostData> postDatas = new ArrayList<>();
		for (int i = 0; i < posts.size(); i++) {
			final Post post = posts.get(i);
			final PostData postData = new PostData();
			postData.setId(post.getId());
			postData.setVersion(post.getVersion());
			postData.setPostTitle(post.getPostTitle());
			postData.setPostContent(post.getPostContent());
			postData.setPostTypeId(post.getPostType().getId());
			postData.setUserId(post.getUser().getId());
			postData.setCreatorName(post.getUser().getFullname());
			postData.setCreatedBy(post.getCreatedBy());
			postData.setCreatedAt(post.getCreatedAt());
			postData.setUpdatedAt(post.getUpdatedAt());

			final Poll poll = pollDao.getByPostId(post.getId()).get();
			final PollData pollData = new PollData();
			pollData.setId(poll.getId());
			pollData.setPollTitle(poll.getPollTitle());
			pollData.setEndAt(poll.getEndAt());
			pollData.setPostId(poll.getPost().getId());
			pollData.setIsActive(poll.getIsActive());

			final List<PollOption> pollOptions = pollOptionDao.getAllByPollId(pollData.getId());
			final int pollOptionSize = pollOptions.size();
			final List<PollOptionData> pollOptionDatas = new ArrayList<>();
			for (int x = 0; x < pollOptionSize; x++) {
				final PollOption pollOption = pollOptions.get(x);
				final PollOptionData pollOptionData = new PollOptionData();
				pollOptionData.setId(pollOption.getId());
				pollOptionData.setPollId(pollOption.getPoll().getId());
				pollOptionData.setPollContent(pollOption.getPollContent());
				pollOptionData.setVersion(pollOption.getVersion());
				pollOptionData.setIsActive(pollOption.getIsActive());

				pollOptionDatas.add(pollOptionData);
			}
			pollData.setPollOptionDatas(pollOptionDatas);

			postData.setPollData(pollData);

			postDatas.add(postData);
		}
		final PostsRes postsRes = new PostsRes();
		postsRes.setData(postDatas);

		return postsRes;
	}

	public PostsRes getAllPremiumById() {
		final List<Post> posts = postDao.getAllPremiumById(principalService.getAuthPrincipal());
		final List<PostData> postDatas = new ArrayList<>();
		for (int i = 0; i < posts.size(); i++) {
			final Post post = posts.get(i);
			final PostData postData = new PostData();
			postData.setId(post.getId());
			postData.setVersion(post.getVersion());
			postData.setPostTitle(post.getPostTitle());
			postData.setPostContent(post.getPostContent());
			postData.setPostTypeId(post.getPostType().getId());
			postData.setUserId(post.getUser().getId());
			postData.setCreatorName(post.getUser().getFullname());
			postData.setCreatedBy(post.getCreatedBy());
			postData.setCreatedAt(post.getCreatedAt());
			postData.setUpdatedAt(post.getUpdatedAt());

			final List<AttachmentPost> attachmentPosts = attachmentPostDao.getAllById(post.getId());
			final int attachmentPostSize = attachmentPosts.size();
			final List<AttachmentPostData> attachmentPostDatas = new ArrayList<>();
			for (int x = 0; x < attachmentPostSize; x++) {
				final AttachmentPost attachmentPost = attachmentPosts.get(x);
				final AttachmentPostData attachmentPostData = new AttachmentPostData();
				attachmentPostData.setId(attachmentPost.getId());
				attachmentPostData.setPostId(attachmentPost.getPost().getId());
				attachmentPostData.setFileId(attachmentPost.getFile().getId());

				attachmentPostDatas.add(attachmentPostData);
			}
			postData.setAttachmentPostDatas(attachmentPostDatas);

			postDatas.add(postData);
		}
		final PostsRes postsRes = new PostsRes();
		postsRes.setData(postDatas);

		return postsRes;
	}

	public PostsRes getAllBookmarked(final Integer offset, final Integer limit) {
		final List<Post> posts = postDao.getAllBookmarked(principalService.getAuthPrincipal(), offset, limit);
		final List<PostData> postDatas = new ArrayList<>();
		for (int i = 0; i < posts.size(); i++) {
			final Post post = posts.get(i);
			final PostData postData = new PostData();
			postData.setId(post.getId());
			postData.setVersion(post.getVersion());
			postData.setPostTitle(post.getPostTitle());
			postData.setPostContent(post.getPostContent());
			postData.setPostTypeId(post.getPostType().getId());
			postData.setUserId(post.getUser().getId());
			postData.setCreatorName(post.getUser().getFullname());
			postData.setCreatedBy(post.getCreatedBy());
			postData.setCreatedAt(post.getCreatedAt());
			postData.setUpdatedAt(post.getUpdatedAt());
			postData.setCompany(post.getUser().getCompany());
			postData.setPositionName(post.getUser().getPosition().getPositionName());
			postData.setFileId(post.getUser().getFile().getId());
			postData.setCountComment(commentService.countComment(post.getId()));

			final Long countLike = likeService.countLike(post.getId());
			postData.setCountLike(countLike);

			final Boolean isLiked = likeService.isLiked(post.getId());
			postData.setIsLiked(isLiked);

			final Boolean isBookmarked = bookmarkService.isBookmarked(post.getId());
			postData.setIsBookmarked(isBookmarked);

			final String postTypeCode = post.getPostType().getPostTypeCode();
			postData.setPostTypeCode(postTypeCode);

			if (PostTypeConstant.POLLING.getPostTypeCode().equals(postTypeCode)) {
				final Poll poll = pollDao.getByPostId(post.getId()).get();
				final PollData pollData = new PollData();

				pollData.setId(poll.getId());
				pollData.setPollTitle(poll.getPollTitle());
				pollData.setEndAt(poll.getEndAt());
				pollData.setPostId(poll.getPost().getId());
				pollData.setIsActive(poll.getIsActive());

				final Long countVote = pollVoteService.countVote(poll.getId());
				pollData.setCountVote(countVote);

				final Boolean isVoted = pollVoteService.isVoted(poll.getId());
				pollData.setIsVoted(isVoted);

				final List<PollOption> pollOptions = pollOptionDao.getAllByPollId(pollData.getId());
				final int pollOptionSize = pollOptions.size();
				final List<PollOptionData> pollOptionDatas = new ArrayList<>();
				for (int x = 0; x < pollOptionSize; x++) {
					final PollOption pollOption = pollOptions.get(x);
					final PollOptionData pollOptionData = new PollOptionData();
					pollOptionData.setId(pollOption.getId());
					pollOptionData.setPollId(pollOption.getPoll().getId());
					pollOptionData.setPollContent(pollOption.getPollContent());
					pollOptionData.setVersion(pollOption.getVersion());
					pollOptionData.setIsActive(pollOption.getIsActive());

					final Boolean optionIsVoted = pollVoteService.optionIsVoted(pollOption.getId());
					pollOptionData.setIsVoted(optionIsVoted);

					final Long countOptionVote = pollVoteService.countVoteByPollOption(pollOption.getId());
					pollOptionData.setCountVote(countOptionVote);

					pollOptionDatas.add(pollOptionData);
				}
				pollData.setPollOptionDatas(pollOptionDatas);

				postData.setPollData(pollData);
			} else {
				final List<AttachmentPost> attachmentPosts = attachmentPostDao.getAllById(post.getId());
				final int attachmentPostSize = attachmentPosts.size();
				final List<AttachmentPostData> attachmentPostDatas = new ArrayList<>();
				for (int x = 0; x < attachmentPostSize; x++) {
					final AttachmentPost attachmentPost = attachmentPosts.get(x);
					final AttachmentPostData attachmentPostData = new AttachmentPostData();
					attachmentPostData.setId(attachmentPost.getId());
					attachmentPostData.setPostId(attachmentPost.getPost().getId());
					attachmentPostData.setFileId(attachmentPost.getFile().getId());

					attachmentPostDatas.add(attachmentPostData);
				}
				postData.setAttachmentPostDatas(attachmentPostDatas);
			}

			postDatas.add(postData);
		}
		final PostsRes postsRes = new PostsRes();
		postsRes.setData(postDatas);

		return postsRes;
	}

	public PostsRes getAllLiked(final Integer offset, final Integer limit) {
		final List<Post> posts = postDao.getAllLiked(principalService.getAuthPrincipal(), offset, limit);
		final List<PostData> postDatas = new ArrayList<>();
		for (int i = 0; i < posts.size(); i++) {
			final Post post = posts.get(i);
			final PostData postData = new PostData();
			postData.setId(post.getId());
			postData.setVersion(post.getVersion());
			postData.setPostTitle(post.getPostTitle());
			postData.setPostContent(post.getPostContent());
			postData.setPostTypeId(post.getPostType().getId());
			postData.setUserId(post.getUser().getId());
			postData.setCreatorName(post.getUser().getFullname());
			postData.setCreatedBy(post.getCreatedBy());
			postData.setCreatedAt(post.getCreatedAt());
			postData.setUpdatedAt(post.getUpdatedAt());
			postData.setCompany(post.getUser().getCompany());
			postData.setPositionName(post.getUser().getPosition().getPositionName());
			postData.setFileId(post.getUser().getFile().getId());
			postData.setCountComment(commentService.countComment(post.getId()));

			final Long countLike = likeService.countLike(post.getId());
			postData.setCountLike(countLike);

			final Boolean isLiked = likeService.isLiked(post.getId());
			postData.setIsLiked(isLiked);

			final Boolean isBookmarked = bookmarkService.isBookmarked(post.getId());
			postData.setIsBookmarked(isBookmarked);

			final String postTypeCode = post.getPostType().getPostTypeCode();
			postData.setPostTypeCode(postTypeCode);

			if (PostTypeConstant.POLLING.getPostTypeCode().equals(postTypeCode)) {
				final Poll poll = pollDao.getByPostId(post.getId()).get();
				final PollData pollData = new PollData();

				pollData.setId(poll.getId());
				pollData.setPollTitle(poll.getPollTitle());
				pollData.setEndAt(poll.getEndAt());
				pollData.setPostId(poll.getPost().getId());
				pollData.setIsActive(poll.getIsActive());

				final Long countVote = pollVoteService.countVote(poll.getId());
				pollData.setCountVote(countVote);

				final Boolean isVoted = pollVoteService.isVoted(poll.getId());
				pollData.setIsVoted(isVoted);

				final List<PollOption> pollOptions = pollOptionDao.getAllByPollId(pollData.getId());
				final int pollOptionSize = pollOptions.size();
				final List<PollOptionData> pollOptionDatas = new ArrayList<>();
				for (int x = 0; x < pollOptionSize; x++) {
					final PollOption pollOption = pollOptions.get(x);
					final PollOptionData pollOptionData = new PollOptionData();
					pollOptionData.setId(pollOption.getId());
					pollOptionData.setPollId(pollOption.getPoll().getId());
					pollOptionData.setPollContent(pollOption.getPollContent());
					pollOptionData.setVersion(pollOption.getVersion());
					pollOptionData.setIsActive(pollOption.getIsActive());

					final Boolean optionIsVoted = pollVoteService.optionIsVoted(pollOption.getId());
					pollOptionData.setIsVoted(optionIsVoted);

					final Long countOptionVote = pollVoteService.countVoteByPollOption(pollOption.getId());
					pollOptionData.setCountVote(countOptionVote);

					pollOptionDatas.add(pollOptionData);
				}
				pollData.setPollOptionDatas(pollOptionDatas);

				postData.setPollData(pollData);
			} else {
				final List<AttachmentPost> attachmentPosts = attachmentPostDao.getAllById(post.getId());
				final int attachmentPostSize = attachmentPosts.size();
				final List<AttachmentPostData> attachmentPostDatas = new ArrayList<>();
				for (int x = 0; x < attachmentPostSize; x++) {
					final AttachmentPost attachmentPost = attachmentPosts.get(x);
					final AttachmentPostData attachmentPostData = new AttachmentPostData();
					attachmentPostData.setId(attachmentPost.getId());
					attachmentPostData.setPostId(attachmentPost.getPost().getId());
					attachmentPostData.setFileId(attachmentPost.getFile().getId());

					attachmentPostDatas.add(attachmentPostData);
				}
				postData.setAttachmentPostDatas(attachmentPostDatas);
			}

			postDatas.add(postData);
		}
		final PostsRes postsRes = new PostsRes();
		postsRes.setData(postDatas);

		return postsRes;
	}

	public PostRes getById(final String id) {
		final Post post = postDao.getById(Post.class, id);
		final PostData postData = new PostData();
		postData.setId(post.getId());
		postData.setVersion(post.getVersion());
		postData.setPostTitle(post.getPostTitle());
		postData.setPostContent(post.getPostContent());
		postData.setPostTypeId(post.getPostType().getId());
		postData.setUserId(post.getUser().getId());
		postData.setCreatorName(post.getUser().getFullname());
		postData.setCreatedBy(post.getCreatedBy());
		postData.setCreatedAt(post.getCreatedAt());
		postData.setUpdatedAt(post.getUpdatedAt());
		postData.setCompany(post.getUser().getCompany());
		postData.setPositionName(post.getUser().getPosition().getPositionName());
		postData.setFileId(post.getUser().getFile().getId());
		final String postTypeCode = post.getPostType().getPostTypeCode();
		postData.setPostTypeCode(postTypeCode);

		if (PostTypeConstant.POLLING.getPostTypeCode().equals(postTypeCode)) {
			final Poll poll = pollDao.getByPostId(post.getId()).get();
			final PollData pollData = new PollData();

			pollData.setId(poll.getId());
			pollData.setPollTitle(poll.getPollTitle());
			pollData.setEndAt(poll.getEndAt());
			pollData.setPostId(poll.getPost().getId());
			pollData.setIsActive(poll.getIsActive());

			final List<PollOption> pollOptions = pollOptionDao.getAllByPollId(pollData.getId());
			final int pollOptionSize = pollOptions.size();
			final List<PollOptionData> pollOptionDatas = new ArrayList<>();
			for (int x = 0; x < pollOptionSize; x++) {
				final PollOption pollOption = pollOptions.get(x);
				final PollOptionData pollOptionData = new PollOptionData();
				pollOptionData.setId(pollOption.getId());
				pollOptionData.setPollId(pollOption.getPoll().getId());
				pollOptionData.setPollContent(pollOption.getPollContent());
				pollOptionData.setVersion(pollOption.getVersion());
				pollOptionData.setIsActive(pollOption.getIsActive());

				pollOptionDatas.add(pollOptionData);
			}
			pollData.setPollOptionDatas(pollOptionDatas);

			postData.setPollData(pollData);
		} else {
			final List<AttachmentPost> attachmentPosts = attachmentPostDao.getAllById(post.getId());
			final int attachmentPostSize = attachmentPosts.size();
			final List<AttachmentPostData> attachmentPostDatas = new ArrayList<>();
			for (int x = 0; x < attachmentPostSize; x++) {
				final AttachmentPost attachmentPost = attachmentPosts.get(x);
				final AttachmentPostData attachmentPostData = new AttachmentPostData();
				attachmentPostData.setId(attachmentPost.getId());
				attachmentPostData.setPostId(attachmentPost.getPost().getId());
				attachmentPostData.setFileId(attachmentPost.getFile().getId());

				attachmentPostDatas.add(attachmentPostData);
			}
			postData.setAttachmentPostDatas(attachmentPostDatas);
		}
		final PostRes postRes = new PostRes();
		postRes.setData(postData);

		return postRes;
	}

	private void valInsert(final PostInsertReq data) {
		valContentNotNull(data);
		valIdFkNotNull(data);
		valFkFound(data);
	}

	private void valContentNotNull(final PostInsertReq data) {
		if (data.getPostTitle() == null) {
			throw new RuntimeException("Title cannot be empty");
		}
		if (data.getPostContent() == null) {
			throw new RuntimeException("Content cannot be empty");
		}
	}

	private void valIdFkNotNull(final PostInsertReq data) {
		if (data.getPostTypeId() == null) {
			throw new RuntimeException("Post Type id cannot be empty");
		}
	}

	private void valFkFound(final PostInsertReq data) {
		final PostType postType = postTypeDao.getByIdAndDetach(PostType.class, data.getPostTypeId());
		if (postType == null) {
			throw new RuntimeException("Post Type not found");
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
