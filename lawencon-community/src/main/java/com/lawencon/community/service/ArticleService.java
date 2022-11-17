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
import com.lawencon.community.dto.article.ArticleData;
import com.lawencon.community.dto.article.ArticleInsertReq;
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

	private void valInsert(final ArticleInsertReq data) {
		contentNotNull(data);
	}

	private void contentNotNull(final ArticleInsertReq data) {
		if (data.getArticleTitle() == null) {
			throw new RuntimeException("Title cannot be null");
		}
		if (data.getArticleContent() == null) {
			throw new RuntimeException("Content cannot be null");
		}
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
}
