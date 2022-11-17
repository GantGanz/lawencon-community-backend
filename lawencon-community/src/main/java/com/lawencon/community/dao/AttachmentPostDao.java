package com.lawencon.community.dao;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.AttachmentPost;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Post;

@Repository
public class AttachmentPostDao extends AbstractJpaDao {
	public List<AttachmentPost> getAllById(final String postId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT a.id, a.file_id , a.post_id ").append("FROM t_attachment_post a ")
				.append("WHERE a.post_id = :postId").append("ORDER BY a.id DESC");

		final String sql = str.toString();
		final List<?> result = createNativeQuery(sql).setParameter(":postId", postId).getResultList();

		final List<AttachmentPost> attachmentActivities = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(checkInObj -> {
				final Object[] objArr = (Object[]) checkInObj;
				final AttachmentPost attachmentPost = new AttachmentPost();
				attachmentPost.setId(objArr[0].toString());

				final File file = new File();
				file.setId(objArr[1].toString());
				attachmentPost.setFile(file);
				
				final Post post = new Post();
				post.setId(objArr[2].toString());
				attachmentPost.setPost(post);

				attachmentActivities.add(attachmentPost);
			});
		}

		return attachmentActivities;
	}
}