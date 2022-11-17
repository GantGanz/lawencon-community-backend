package com.lawencon.community.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.Article;
import com.lawencon.community.model.User;

@Repository
public class ArticleDao extends AbstractJpaDao {

	public List<Article> getAllById(final String userId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT a.id, a.ver, a.article_title, a.article_content, u.fullname, a.created_by, ")
				.append("a.created_at, a.created_at, a.updated_at, a.is_active ").append("FROM t_article a ")
				.append("INNER JOIN t_user u ON u.id = a.created_by ").append("WHERE a.created_by = :userId")
				.append("AND a.is_active = TRUE ").append("ORDER BY a.id DESC");

		final String sql = str.toString();
		final List<?> result = createNativeQuery(sql).setParameter(":userId", userId).getResultList();

		final List<Article> activities = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(checkInObj -> {
				final Object[] objArr = (Object[]) checkInObj;
				final Article article = new Article();
				article.setId(objArr[0].toString());
				article.setVersion(Integer.valueOf(objArr[1].toString()));
				article.setArticleTitle(objArr[2].toString());
				article.setArticleContent(objArr[3].toString());

				final User user = new User();
				user.setFullname(objArr[4].toString());
				article.setUser(user);

				article.setCreatedBy(objArr[5].toString());
				article.setCreatedAt(Timestamp.valueOf(objArr[6].toString()).toLocalDateTime());
				if (objArr[7] != null) {
					article.setUpdatedAt(Timestamp.valueOf(objArr[8].toString()).toLocalDateTime());
				}
				article.setIsActive(Boolean.valueOf(objArr[9].toString()));

				activities.add(article);
			});
		}

		return activities;
	}
}