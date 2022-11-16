package com.lawencon.community.dao;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.constant.PostTypeConstant;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.User;

@Repository
public class PostDao extends AbstractJpaDao {
	
	public List<Post> getAllRegular() {	
		final StringBuilder str = new StringBuilder();
		str.append("SELECT p.id, p.ver, p.post_title, p.post_content, u.fullname, p.created_by, p.created_at, p.updated_at, p.is_active ")
				.append("FROM t_post p ")
				.append("INNER JOIN t_post_type pt ON pt.id = p.post_type_id ")
				.append("INNER JOIN t_user u ON u.id = p.created_by ")
				.append("WHERE pt.post_type_code = :postTypeCode ");

		final String sql = str.toString(); 
		final List<?> result = createNativeQuery(sql).setParameter("postTypeCode", PostTypeConstant.REGULAR.getPostTypeCode()).getResultList();

		final List<Post> posts = new ArrayList<>();
		
		if (result != null && result.size() > 0) {
			result.forEach(checkInObj -> {
				final Object[] objArr = (Object[]) checkInObj;
				final Post post = new Post();
				post.setId(objArr[0].toString());
				post.setVersion(Integer.valueOf(objArr[1].toString()));
				post.setPostTitle(objArr[2].toString());
				post.setPostContent(objArr[3].toString());
				post.setPostContent(objArr[3].toString());
				
				final User user = new User();
				user.setFullname(objArr[4].toString());
				post.setUser(user);
				
				post.setCreatedBy(objArr[5].toString());
				post.setCreatedAt(Timestamp.valueOf(objArr[6].toString()).toLocalDateTime());
				if (objArr[7] != null) {
					post.setUpdatedAt(Timestamp.valueOf(objArr[7].toString()).toLocalDateTime());
				}
				post.setIsActive(Boolean.valueOf(objArr[8].toString()));

				posts.add(post);
			});
		}

		return posts;
	}
	
	public List<Post> getAllPolling() {	
		final StringBuilder str = new StringBuilder();
		str.append("SELECT p.id, p.ver, p.post_title, p.post_content, u.fullname, p.created_by, p.created_at, p.updated_at, p.is_active ")
				.append("FROM t_post p ")
				.append("INNER JOIN t_post_type pt ON pt.id = p.post_type_id ")
				.append("INNER JOIN t_user u ON u.id = p.created_by ")
				.append("WHERE pt.post_type_code = :postTypeCode ");

		final String sql = str.toString(); 
		final List<?> result = createNativeQuery(sql).setParameter("postTypeCode", PostTypeConstant.POLLING.getPostTypeCode()).getResultList();

		final List<Post> posts = new ArrayList<>();
		
		if (result != null && result.size() > 0) {
			result.forEach(checkInObj -> {
				final Object[] objArr = (Object[]) checkInObj;
				final Post post = new Post();
				post.setId(objArr[0].toString());
				post.setVersion(Integer.valueOf(objArr[1].toString()));
				post.setPostTitle(objArr[2].toString());
				post.setPostContent(objArr[3].toString());
				post.setPostContent(objArr[3].toString());
				
				final User user = new User();
				user.setFullname(objArr[4].toString());
				post.setUser(user);
				
				post.setCreatedBy(objArr[5].toString());
				post.setCreatedAt(Timestamp.valueOf(objArr[6].toString()).toLocalDateTime());
				if (objArr[7] != null) {
					post.setUpdatedAt(Timestamp.valueOf(objArr[7].toString()).toLocalDateTime());
				}
				post.setIsActive(Boolean.valueOf(objArr[8].toString()));

				posts.add(post);
			});
		}

		return posts;
	}
	
	public List<Post> getAllPremium() {	
		final StringBuilder str = new StringBuilder();
		str.append("SELECT p.id, p.ver, p.post_title, p.post_content, u.fullname, p.created_by, p.created_at, p.updated_at, p.is_active ")
				.append("FROM t_post p ")
				.append("INNER JOIN t_post_type pt ON pt.id = p.post_type_id ")
				.append("INNER JOIN t_user u ON u.id = p.created_by ")
				.append("WHERE pt.post_type_code = :postTypeCode ");

		final String sql = str.toString(); 
		final List<?> result = createNativeQuery(sql).setParameter("postTypeCode", PostTypeConstant.PREMIUM.getPostTypeCode()).getResultList();

		final List<Post> posts = new ArrayList<>();
		
		if (result != null && result.size() > 0) {
			result.forEach(checkInObj -> {
				final Object[] objArr = (Object[]) checkInObj;
				final Post post = new Post();
				post.setId(objArr[0].toString());
				post.setVersion(Integer.valueOf(objArr[1].toString()));
				post.setPostTitle(objArr[2].toString());
				post.setPostContent(objArr[3].toString());
				post.setPostContent(objArr[3].toString());
				
				final User user = new User();
				user.setFullname(objArr[4].toString());
				post.setUser(user);
				
				post.setCreatedBy(objArr[5].toString());
				post.setCreatedAt(Timestamp.valueOf(objArr[6].toString()).toLocalDateTime());
				if (objArr[7] != null) {
					post.setUpdatedAt(Timestamp.valueOf(objArr[7].toString()).toLocalDateTime());
				}
				post.setIsActive(Boolean.valueOf(objArr[8].toString()));

				posts.add(post);
			});
		}

		return posts;
	}
}