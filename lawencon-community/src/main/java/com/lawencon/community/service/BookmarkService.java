package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.BookmarkDao;
import com.lawencon.community.dao.PostDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dto.InsertDataRes;
import com.lawencon.community.dto.InsertRes;
import com.lawencon.community.dto.UpdateDataRes;
import com.lawencon.community.dto.UpdateRes;
import com.lawencon.community.dto.bookmark.BookmarkData;
import com.lawencon.community.dto.bookmark.BookmarkInsertReq;
import com.lawencon.community.dto.bookmark.BookmarkRes;
import com.lawencon.community.dto.bookmark.BookmarkUpdateReq;
import com.lawencon.community.dto.bookmark.BookmarksRes;
import com.lawencon.community.model.Bookmark;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.User;
import com.lawencon.security.principal.PrincipalService;

@Service
public class BookmarkService extends BaseCoreService {

	@Autowired
	private BookmarkDao bookmarkDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private PostDao postDao;
	@Autowired
	private PrincipalService principalService;

	public InsertRes insert(final BookmarkInsertReq data) {
		valInsert(data);

		Bookmark bookmarkInsert = new Bookmark();

		final String userId = principalService.getAuthPrincipal();
		final User user = userDao.getById(User.class, userId);
		bookmarkInsert.setUser(user);

		final Post post = postDao.getById(Post.class, data.getPostId());
		bookmarkInsert.setPost(post);

		try {
			begin();
			bookmarkInsert = bookmarkDao.save(bookmarkInsert);
			commit();
		} catch (final Exception e) {
			e.printStackTrace();
			rollback();
			throw new RuntimeException("Failed to create Bookmark");
		}
		final InsertDataRes dataRes = new InsertDataRes();
		dataRes.setId(bookmarkInsert.getId());

		final InsertRes insertRes = new InsertRes();
		insertRes.setData(dataRes);
		insertRes.setMessage("Bookmark created");

		return insertRes;
	}

	public UpdateRes softDelete(final BookmarkUpdateReq data) {
		valUpdate(data);
		Bookmark bookmark = bookmarkDao.getByIdAndDetach(Bookmark.class, data.getId());
		bookmark.setIsActive(false);
		bookmark.setVersion(data.getVersion());

		try {
			begin();
			bookmark = bookmarkDao.saveAndFlush(bookmark);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new RuntimeException("Soft Delete failed");
		}
		final UpdateDataRes dataRes = new UpdateDataRes();
		dataRes.setVersion(bookmark.getVersion());

		final UpdateRes res = new UpdateRes();
		res.setData(dataRes);
		res.setMessage("Soft Delete success");
		return res;
	}

	public BookmarksRes getAllByUserId() {
		final String userId = principalService.getAuthPrincipal();
		final List<Bookmark> bookmarks = bookmarkDao.getAllByUserId(userId);
		final List<BookmarkData> bookmarkDatas = new ArrayList<>();
		for (int i = 0; i < bookmarks.size(); i++) {
			final Bookmark bookmark = bookmarks.get(i);
			final BookmarkData bookmarkData = new BookmarkData();
			bookmarkData.setId(bookmark.getId());
			bookmarkData.setUserId(bookmark.getUser().getId());
			bookmarkData.setPostId(bookmark.getPost().getId());
			bookmarkDatas.add(bookmarkData);
		}
		final BookmarksRes bookmarksRes = new BookmarksRes();
		bookmarksRes.setData(bookmarkDatas);

		return bookmarksRes;
	}

	public BookmarkRes getById(final String id) {
		final Bookmark bookmark = bookmarkDao.getById(Bookmark.class, id);
		final BookmarkData bookmarkData = new BookmarkData();
		bookmarkData.setId(bookmark.getId());
		bookmarkData.setUserId(bookmark.getUser().getId());
		bookmarkData.setPostId(bookmark.getPost().getId());

		final BookmarkRes bookmarkRes = new BookmarkRes();
		bookmarkRes.setData(bookmarkData);

		return bookmarkRes;
	}

	public Boolean isBookmarked(final String postId) {
		final String userId = principalService.getAuthPrincipal();
		Boolean status = false;
		if (bookmarkDao.isBookmarked(postId, userId) > 0) {
			status = true;
		}
		return status;
	}

	private void valInsert(final BookmarkInsertReq data) {
		valIdFkNotNull(data);
		valFkFound(data);
	}

	private void valIdFkNotNull(final BookmarkInsertReq data) {
		if (data.getUserId() == null) {
			throw new RuntimeException("User id cannot be empty");
		}
		if (data.getPostId() == null) {
			throw new RuntimeException("Industry id cannot be empty");
		}
	}

	private void valFkFound(final BookmarkInsertReq data) {
		final User user = userDao.getByIdAndDetach(User.class, data.getUserId());
		if (user == null) {
			throw new RuntimeException("User not found");
		}
		final Post post = postDao.getByIdAndDetach(Post.class, data.getPostId());
		if (post == null) {
			throw new RuntimeException("Post not found");
		}
	}

	private void valUpdate(final BookmarkUpdateReq data) {
		valIdNotNull(data);
		valIdFound(data);
		valContentNotNull(data);
	}

	private void valIdNotNull(final BookmarkUpdateReq data) {
		if (data.getId() == null) {
			throw new RuntimeException("id cannot be empty");
		}
	}

	private void valContentNotNull(final BookmarkUpdateReq data) {
		if (data.getVersion() == null) {
			throw new RuntimeException("Version cannot be empty");
		}
	}

	private void valIdFound(final BookmarkUpdateReq data) {
		final Bookmark bookmark = bookmarkDao.getById(Bookmark.class, data.getId());
		if (bookmark == null) {
			throw new RuntimeException("Bookmark not found");
		}
	}
}
