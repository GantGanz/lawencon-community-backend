package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.PostTypeDao;
import com.lawencon.community.dto.posttype.PostTypeData;
import com.lawencon.community.dto.posttype.PostTypesRes;
import com.lawencon.community.model.PostType;

@Service
public class PostTypeService extends BaseCoreService {
	@Autowired
	private PostTypeDao postTypeDao;

	public PostTypesRes getAll() {
		final List<PostType> postTypes = postTypeDao.getAll(PostType.class);
		final List<PostTypeData> postTypeDatas = new ArrayList<>();
		for (int i = 0; i < postTypes.size(); i++) {
			final PostType postType = postTypes.get(i);
			final PostTypeData postTypeData = new PostTypeData();
			postTypeData.setId(postType.getId());
			postTypeData.setPostTypeCode(postType.getPostTypeCode());
			postTypeData.setPostTypeName(postType.getPostTypeName());
			postTypeData.setVersion(postType.getVersion());

			postTypeDatas.add(postTypeData);
		}
		final PostTypesRes postTypesRes = new PostTypesRes();
		postTypesRes.setData(postTypeDatas);

		return postTypesRes;
	}
}
