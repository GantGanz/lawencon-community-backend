package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.ActivityTypeDao;
import com.lawencon.community.dto.activitytype.ActivityTypeData;
import com.lawencon.community.dto.activitytype.ActivityTypeRes;
import com.lawencon.community.dto.activitytype.ActivityTypesRes;
import com.lawencon.community.model.ActivityType;

@Service
public class ActivityTypeService extends BaseCoreService {
	@Autowired
	private ActivityTypeDao activityTypeDao;

	public ActivityTypesRes getAll() {
		final List<ActivityType> activityTypes = activityTypeDao.getAll(ActivityType.class);
		final List<ActivityTypeData> activityTypeDatas = new ArrayList<>();
		for (int i = 0; i < activityTypes.size(); i++) {
			final ActivityType activityType = activityTypes.get(i);
			final ActivityTypeData activityTypeData = new ActivityTypeData();
			activityTypeData.setId(activityType.getId());
			activityTypeData.setActivityTypeCode(activityType.getActivityTypeCode());
			activityTypeData.setActivityTypeName(activityType.getActivityTypeName());
			activityTypeData.setVersion(activityType.getVersion());

			activityTypeDatas.add(activityTypeData);
		}
		final ActivityTypesRes activityTypesRes = new ActivityTypesRes();
		activityTypesRes.setData(activityTypeDatas);

		return activityTypesRes;
	}

	public ActivityTypeRes getById(final String id) {
		final ActivityType activityType = activityTypeDao.getById(ActivityType.class, id);
		final ActivityTypeData activityTypeData = new ActivityTypeData();
		activityTypeData.setId(activityType.getId());
		activityTypeData.setActivityTypeCode(activityType.getActivityTypeCode());
		activityTypeData.setActivityTypeName(activityType.getActivityTypeName());
		activityTypeData.setVersion(activityType.getVersion());

		final ActivityTypeRes activityTypeRes = new ActivityTypeRes();
		activityTypeRes.setData(activityTypeData);

		return activityTypeRes;
	}
}
