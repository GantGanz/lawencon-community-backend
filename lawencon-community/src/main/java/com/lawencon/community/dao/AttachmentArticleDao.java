package com.lawencon.community.dao;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.Article;
import com.lawencon.community.model.AttachmentArticle;
import com.lawencon.community.model.File;

@Repository
public class AttachmentArticleDao extends AbstractJpaDao {
	public List<AttachmentArticle> getAllById(final String articleId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT a.id, a.file_id , a.article_id ").append("FROM t_attachment_article a ")
				.append("WHERE a.article_id = :articleId ").append("ORDER BY a.created_at DESC");

		final String sql = str.toString();
		final List<?> result = createNativeQuery(sql).setParameter("articleId", articleId).getResultList();

		final List<AttachmentArticle> attachmentActivities = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(resultObj -> {
				final Object[] objArr = (Object[]) resultObj;
				final AttachmentArticle attachmentArticle = new AttachmentArticle();
				attachmentArticle.setId(objArr[0].toString());

				final File file = new File();
				file.setId(objArr[1].toString());
				attachmentArticle.setFile(file);
				
				final Article article = new Article();
				article.setId(objArr[2].toString());
				attachmentArticle.setArticle(article);

				attachmentActivities.add(attachmentArticle);
			});
		}

		return attachmentActivities;
	}
}