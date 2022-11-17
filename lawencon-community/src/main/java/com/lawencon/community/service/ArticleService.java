package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.ArticleDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dto.InsertDataRes;
import com.lawencon.community.dto.InsertRes;
import com.lawencon.community.dto.UpdateDataRes;
import com.lawencon.community.dto.UpdateRes;
import com.lawencon.community.dto.article.ArticleData;
import com.lawencon.community.dto.article.ArticleInsertReq;
import com.lawencon.community.dto.article.ArticleUpdateReq;
import com.lawencon.community.dto.article.ArticlesRes;
import com.lawencon.community.model.Article;
import com.lawencon.community.model.User;
import com.lawencon.security.principal.PrincipalService;

@Service
public class ArticleService extends BaseCoreService {
	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private PrincipalService principalService;

	public InsertRes insert(final ArticleInsertReq data) {
		valInsert(data);

		Article articleInsert = new Article();
		articleInsert.setArticleTitle(data.getArticleTitle());
		articleInsert.setArticleContent(data.getArticleContent());

		final String userId = principalService.getAuthPrincipal();
		final User user = userDao.getById(User.class, userId);
		articleInsert.setUser(user);

		try {
			begin();
			articleInsert = articleDao.save(articleInsert);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new RuntimeException("Article failed to create");
		}
		final InsertDataRes dataRes = new InsertDataRes();
		dataRes.setId(articleInsert.getId());

		final InsertRes insertRes = new InsertRes();
		insertRes.setData(dataRes);
		insertRes.setMessage("Article created");

		return insertRes;
	}

	public UpdateRes update(final ArticleUpdateReq data) {
		valUpdate(data);
		Article article = articleDao.getByIdAndDetach(Article.class, data.getId());
		article.setArticleTitle(data.getArticleTitle());
		article.setArticleContent(data.getArticleContent());
		article.setIsActive(data.getIsActive());
		article.setVersion(data.getVersion());

		try {
			begin();
			article = articleDao.saveAndFlush(article);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new RuntimeException("Update failed");
		}
		final UpdateDataRes dataRes = new UpdateDataRes();
		dataRes.setVersion(article.getVersion());

		final UpdateRes res = new UpdateRes();
		res.setData(dataRes);
		res.setMessage("Update success");
		return res;
	}

	public ArticlesRes getAll() {
		final List<Article> articles = articleDao.getAll(Article.class);
		final List<ArticleData> articleDatas = new ArrayList<>();
		for (int i = 0; i < articles.size(); i++) {
			final Article article = articles.get(i);
			final ArticleData articleData = new ArticleData();
			articleData.setId(article.getId());
			articleData.setArticleTitle(article.getArticleTitle());
			articleData.setArticleContent(article.getArticleContent());
			articleData.setCreatedBy(article.getCreatedBy());
			articleData.setCreatedAt(article.getCreatedAt());
			articleData.setVersion(article.getVersion());

			articleDatas.add(articleData);
		}
		final ArticlesRes articlesRes = new ArticlesRes();
		articlesRes.setData(articleDatas);

		return articlesRes;
	}

	public ArticlesRes getAllById() {
		final List<Article> articles = articleDao.getAllById(principalService.getAuthPrincipal());
		final List<ArticleData> articleDatas = new ArrayList<>();
		for (int i = 0; i < articles.size(); i++) {
			final Article article = articles.get(i);
			final ArticleData articleData = new ArticleData();
			articleData.setId(article.getId());
			articleData.setArticleTitle(article.getId());
			articleData.setArticleContent(article.getArticleContent());
			articleData.setCreatedAt(article.getCreatedAt());
			articleData.setCreatedBy(article.getCreatedBy());
			articleData.setVersion(article.getVersion());

			articleDatas.add(articleData);
		}
		final ArticlesRes articlesRes = new ArticlesRes();
		articlesRes.setData(articleDatas);

		return articlesRes;
	}

	private void valInsert(final ArticleInsertReq data) {
		valContentNotNull(data);
	}

	private void valContentNotNull(final ArticleInsertReq data) {
		if (data.getArticleTitle() == null) {
			throw new RuntimeException("Title cannot be empty");
		}
		if (data.getArticleContent() == null) {
			throw new RuntimeException("Content cannot be empty");
		}
	}

	private void valUpdate(final ArticleUpdateReq data) {
		valIdNotNull(data);
		valIdFound(data);
		valContentNotNull(data);
	}

	private void valIdNotNull(final ArticleUpdateReq data) {
		if (data.getId() == null) {
			throw new RuntimeException("id cannot be empty");
		}
	}

	private void valContentNotNull(final ArticleUpdateReq data) {
		if (data.getArticleTitle() == null) {
			throw new RuntimeException("Title cannot be empty");
		}
		if (data.getArticleContent() == null) {
			throw new RuntimeException("Content cannot be empty");
		}
		if (data.getIsActive() == null) {
			throw new RuntimeException("isActive cannot be empty");
		}
		if (data.getVersion() == null) {
			throw new RuntimeException("Version cannot be empty");
		}
	}

	private void valIdFound(final ArticleUpdateReq data) {
		final Article article = articleDao.getById(Article.class, data.getId());
		if (article == null) {
			throw new RuntimeException("Article not found");
		}
	}
}
