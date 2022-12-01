package com.lawencon.community.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.CommentDao;
import com.lawencon.community.dao.PostDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dto.InsertDataRes;
import com.lawencon.community.dto.InsertRes;
import com.lawencon.community.dto.UpdateDataRes;
import com.lawencon.community.dto.UpdateRes;
import com.lawencon.community.dto.comment.CommentData;
import com.lawencon.community.dto.comment.CommentInsertReq;
import com.lawencon.community.dto.comment.CommentRes;
import com.lawencon.community.dto.comment.CommentUpdateReq;
import com.lawencon.community.dto.comment.CommentsRes;
import com.lawencon.community.model.Comment;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.User;
import com.lawencon.security.principal.PrincipalService;

@Service
public class CommentService extends BaseCoreService {
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private PostDao postDao;
	@Autowired
	private PrincipalService principalService;
	
	public InsertRes insert(final CommentInsertReq data) {
		valInsert(data);

		Comment commentInsert = new Comment();
		commentInsert.setCommentContent(data.getCommentContent());

		final String userId = principalService.getAuthPrincipal();
		final User user = userDao.getById(User.class, userId);
		commentInsert.setUser(user);
		
		if(data.getPostId() != null) {			
			final Post post = postDao.getById(Post.class, data.getPostId());
			commentInsert.setPost(post);
		}else {
			final Comment comment = commentDao.getById(Comment.class, data.getCommentId());
			commentInsert.setComment(comment);
		}

		try {
			begin();
			commentInsert = commentDao.save(commentInsert);
			commit();
		} catch (final Exception e) {
			e.printStackTrace();
			rollback();
			throw new RuntimeException("Failed to create Comment");
		}
		final InsertDataRes dataRes = new InsertDataRes();
		dataRes.setId(commentInsert.getId());

		final InsertRes insertRes = new InsertRes();
		insertRes.setData(dataRes);
		insertRes.setMessage("Comment created");

		return insertRes;
	}

	public UpdateRes update(final CommentUpdateReq data) {
		valUpdate(data);
		Comment comment = commentDao.getByIdAndDetach(Comment.class, data.getId());
		comment.setCommentContent(data.getCommentContent());
		comment.setIsActive(data.getIsActive());
		comment.setVersion(data.getVersion());

		try {
			begin();
			comment = commentDao.saveAndFlush(comment);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new RuntimeException("Update failed");
		}
		final UpdateDataRes dataRes = new UpdateDataRes();
		dataRes.setVersion(comment.getVersion());

		final UpdateRes res = new UpdateRes();
		res.setData(dataRes);
		res.setMessage("Update success");
		return res;
	}
	
	public UpdateRes softDelete(final CommentUpdateReq data) {
		valUpdate(data);
		Comment comment = commentDao.getByIdAndDetach(Comment.class, data.getId());
		comment.setIsActive(false);
		comment.setVersion(data.getVersion());

		try {
			begin();
			comment = commentDao.saveAndFlush(comment);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new RuntimeException("Soft Delete failed");
		}
		final UpdateDataRes dataRes = new UpdateDataRes();
		dataRes.setVersion(comment.getVersion());

		final UpdateRes res = new UpdateRes();
		res.setData(dataRes);
		res.setMessage("Soft Delete success");
		return res;
	}

	public CommentsRes getAllByPostId(final String id) {
		final List<Comment> comments = commentDao.getAllByPostId(id);
		final List<CommentData> commentDatas = new ArrayList<>();
		for (int i = 0; i < comments.size(); i++) {
			final Comment comment = comments.get(i);
			final CommentData commentData = new CommentData();
			commentData.setId(comment.getId());
			commentData.setCommentContent(comment.getCommentContent());
			commentData.setUserName(comment.getUser().getFullname());
			commentData.setUserId(comment.getUser().getId());
			commentData.setPostId(comment.getPost().getId());
			commentDatas.add(commentData);
		}
		final CommentsRes commentsRes = new CommentsRes();
		commentsRes.setData(commentDatas);

		return commentsRes;
	}
	
	public CommentsRes getAllByCommentId(final String id) {
		final List<Comment> comments = commentDao.getAllByCommentId(id);
		final List<CommentData> commentDatas = new ArrayList<>();
		for (int i = 0; i < comments.size(); i++) {
			final Comment comment = comments.get(i);
			final CommentData commentData = new CommentData();
			commentData.setId(comment.getId());
			commentData.setCommentContent(comment.getCommentContent());
			commentData.setUserName(comment.getUser().getFullname());
			commentData.setUserId(comment.getUser().getId());
			commentData.setCommentId(comment.getComment().getId());
			commentDatas.add(commentData);
		}
		final CommentsRes commentsRes = new CommentsRes();
		commentsRes.setData(commentDatas);

		return commentsRes;
	}

	public CommentRes getById(final String id) {
		final Comment comment = commentDao.getById(Comment.class, id);
		final CommentData commentData = new CommentData();
		commentData.setId(comment.getId());
		commentData.setCommentContent(comment.getCommentContent());
		commentData.setUserName(comment.getUser().getFullname());
		commentData.setUserId(comment.getUser().getId());
		commentData.setPostId(comment.getPost().getId());
		commentData.setCommentId(comment.getComment().getId());

		final CommentRes commentRes = new CommentRes();
		commentRes.setData(commentData);

		return commentRes;
	}
	
	public Long countComment(final String postId) {
		return commentDao.countComment(postId);
	}
	
	private void valInsert(final CommentInsertReq data) {
		valContentNotNull(data);
		valIdFkNotNull(data);
		valFkFound(data);
	}

	private void valContentNotNull(final CommentInsertReq data) {
		if (data.getCommentContent() == null) {
			throw new RuntimeException("Content cannot be empty");
		}
	}
	
	private void valIdFkNotNull(final CommentInsertReq data) {
		if (data.getPostId() == null) {
			throw new RuntimeException("Post id cannot be empty");
		}
	}

	private void valFkFound(final CommentInsertReq data) {
		final Post post = postDao.getByIdAndDetach(Post.class, data.getPostId());
		if (post == null) {
			throw new RuntimeException("Post not found");
		}
	}

	private void valUpdate(final CommentUpdateReq data) {
		valIdNotNull(data);
		valIdFound(data);
		valContentNotNull(data);
	}

	private void valIdNotNull(final CommentUpdateReq data) {
		if (data.getId() == null) {
			throw new RuntimeException("id cannot be empty");
		}
	}

	private void valContentNotNull(final CommentUpdateReq data) {
		if (data.getCommentContent() == null) {
			throw new RuntimeException("Content cannot be empty");
		}
		if (data.getIsActive() == null) {
			throw new RuntimeException("isActive cannot be empty");
		}
		if (data.getVersion() == null) {
			throw new RuntimeException("Version cannot be empty");
		}
	}

	private void valIdFound(final CommentUpdateReq data) {
		final Comment comment = commentDao.getById(Comment.class, data.getId());
		if (comment == null) {
			throw new RuntimeException("Comment not found");
		}
	}
}
