package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.ActivityDao;
import com.lawencon.community.dao.ActivityTypeDao;
import com.lawencon.community.dao.AttachmentActivityDao;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dto.InsertDataRes;
import com.lawencon.community.dto.InsertRes;
import com.lawencon.community.dto.UpdateDataRes;
import com.lawencon.community.dto.UpdateRes;
import com.lawencon.community.dto.activity.ActivitiesRes;
import com.lawencon.community.dto.activity.ActivityData;
import com.lawencon.community.dto.activity.ActivityInsertReq;
import com.lawencon.community.dto.activity.ActivityRes;
import com.lawencon.community.dto.activity.ActivityUpdateReq;
import com.lawencon.community.dto.attachmentactivity.AttachmentActivityData;
import com.lawencon.community.dto.attachmentactivity.AttachmentActivityInsertReq;
import com.lawencon.community.model.Activity;
import com.lawencon.community.model.ActivityType;
import com.lawencon.community.model.AttachmentActivity;
import com.lawencon.community.model.File;
import com.lawencon.security.principal.PrincipalService;

@Service
public class ActivityService extends BaseCoreService {

	@Autowired
	private ActivityDao activityDao;
	@Autowired
	private ActivityTypeDao activityTypeDao;
	@Autowired
	private AttachmentActivityDao attachmentActivityDao;
	@Autowired
	private FileDao fileDao;
	@Autowired
	private PrincipalService principalService;
	
	public InsertRes insert(final ActivityInsertReq data) {
		valInsert(data);

		Activity activityInsert = new Activity();
		activityInsert.setActivityTitle(data.getActivityTitle());
		activityInsert.setActivityLocation(data.getActivityLocation());
		activityInsert.setStartAt(data.getStartAt());
		activityInsert.setEndAt(data.getEndAt());
		activityInsert.setFee(data.getFee());

		final ActivityType activityType = activityTypeDao.getById(ActivityType.class, data.getActivityTypeId());
		activityInsert.setActivityType(activityType);

		final int attachmentSize = data.getAttachmentActivityInsertReqs().size();
		try {
			begin();
			activityInsert = activityDao.save(activityInsert);
			for (int i = 0; i < attachmentSize; i++) {
				final AttachmentActivityInsertReq attachmentActivityInsertReq = data.getAttachmentActivityInsertReqs()
						.get(i);
				final AttachmentActivity attachmentActivity = new AttachmentActivity();

				attachmentActivity.setActivity(activityInsert);

				final File file = fileDao.getById(File.class, attachmentActivityInsertReq.getFileId());
				attachmentActivity.setFile(file);

				attachmentActivityDao.save(attachmentActivity);
			}
			commit();
		} catch (final Exception e) {
			e.printStackTrace();
			rollback();
			throw new RuntimeException("failed to create activity");
		}
		final InsertDataRes dataRes = new InsertDataRes();
		dataRes.setId(activityInsert.getId());

		final InsertRes insertRes = new InsertRes();
		insertRes.setData(dataRes);
		insertRes.setMessage("activity created");

		return insertRes;
	}

	public UpdateRes update(final ActivityUpdateReq data) {
		valUpdate(data);
		Activity activity = activityDao.getByIdAndDetach(Activity.class, data.getId());
		activity.setActivityTitle(data.getActivityTitle());
		activity.setActivityLocation(data.getActivityLocation());
		activity.setStartAt(data.getStartAt());
		activity.setEndAt(data.getEndAt());
		activity.setFee(data.getFee());
		activity.setIsActive(data.getIsActive());
		activity.setVersion(data.getVersion());

		final ActivityType activityType = activityTypeDao.getById(ActivityType.class, data.getActivityTypeId());
		activity.setActivityType(activityType);

		try {
			begin();
			activity = activityDao.saveAndFlush(activity);
			commit();
		} catch (final Exception e) {
			e.printStackTrace();
			rollback();
			throw new RuntimeException("Update failed");
		}
		final UpdateDataRes dataRes = new UpdateDataRes();
		dataRes.setVersion(activity.getVersion());

		final UpdateRes res = new UpdateRes();
		res.setData(dataRes);
		res.setMessage("Update success");
		return res;
	}
	
	public ActivitiesRes getAllEvent() {
		final List<Activity> activities = activityDao.getAllEvent();
		final List<ActivityData> activityDatas = new ArrayList<>();
		for (int i = 0; i < activities.size(); i++) {
			final Activity activity = activities.get(i);
			final ActivityData activityData = new ActivityData();
			activityData.setId(activity.getId());
			activityData.setVersion(activity.getVersion());
			activityData.setActivityTitle(activity.getId());
			activityData.setActivityLocation(activity.getActivityLocation());
			activityData.setStartAt(activity.getStartAt());
			activityData.setEndAt(activity.getEndAt());
			activityData.setFee(activity.getFee());
			activityData.setActivityTypeId(activity.getActivityType().getId());
			activityData.setActivityTypeCode(activity.getActivityType().getActivityTypeCode());
			activityData.setActivityTypeName(activity.getActivityType().getActivityTypeName());
			activityData.setCreatorName(activity.getUser().getFullname());
			activityData.setCreatedBy(activity.getCreatedBy());
			activityData.setCreatedAt(activity.getCreatedAt());
			activityData.setUpdatedAt(activity.getUpdatedAt());

			final List<AttachmentActivity> attachmentActivities = attachmentActivityDao.getAllById(activity.getId());
			final int attachmentActivitySize = attachmentActivities.size();
			final List<AttachmentActivityData> attachmentActivityDatas = new ArrayList<>();
			for (int x = 0; x < attachmentActivitySize; x++) {
				final AttachmentActivity attachmentActivity = attachmentActivities.get(x);
				final AttachmentActivityData attachmentActivityData = new AttachmentActivityData();
				attachmentActivityData.setId(attachmentActivity.getId());
				attachmentActivityData.setActivityId(attachmentActivity.getActivity().getId());
				attachmentActivityData.setFileId(attachmentActivity.getFile().getId());

				attachmentActivityDatas.add(attachmentActivityData);
			}
			activityData.setAttachmentActivityDatas(attachmentActivityDatas);
		}
		final ActivitiesRes activitiesRes = new ActivitiesRes();
		activitiesRes.setData(activityDatas);

		return activitiesRes;
	}
	
	public ActivitiesRes getAllCourse() {
		final List<Activity> activities = activityDao.getAllCourse();
		final List<ActivityData> activityDatas = new ArrayList<>();
		for (int i = 0; i < activities.size(); i++) {
			final Activity activity = activities.get(i);
			final ActivityData activityData = new ActivityData();
			activityData.setId(activity.getId());
			activityData.setVersion(activity.getVersion());
			activityData.setActivityTitle(activity.getId());
			activityData.setActivityLocation(activity.getActivityLocation());
			activityData.setStartAt(activity.getStartAt());
			activityData.setEndAt(activity.getEndAt());
			activityData.setFee(activity.getFee());
			activityData.setActivityTypeId(activity.getActivityType().getId());
			activityData.setActivityTypeCode(activity.getActivityType().getActivityTypeCode());
			activityData.setActivityTypeName(activity.getActivityType().getActivityTypeName());
			activityData.setCreatorName(activity.getUser().getFullname());
			activityData.setCreatedBy(activity.getCreatedBy());
			activityData.setCreatedAt(activity.getCreatedAt());
			activityData.setUpdatedAt(activity.getUpdatedAt());

			final List<AttachmentActivity> attachmentActivities = attachmentActivityDao.getAllById(activity.getId());
			final int attachmentActivitySize = attachmentActivities.size();
			final List<AttachmentActivityData> attachmentActivityDatas = new ArrayList<>();
			for (int x = 0; x < attachmentActivitySize; x++) {
				final AttachmentActivity attachmentActivity = attachmentActivities.get(x);
				final AttachmentActivityData attachmentActivityData = new AttachmentActivityData();
				attachmentActivityData.setId(attachmentActivity.getId());
				attachmentActivityData.setActivityId(attachmentActivity.getActivity().getId());
				attachmentActivityData.setFileId(attachmentActivity.getFile().getId());

				attachmentActivityDatas.add(attachmentActivityData);
			}
			activityData.setAttachmentActivityDatas(attachmentActivityDatas);
		}
		final ActivitiesRes activitiesRes = new ActivitiesRes();
		activitiesRes.setData(activityDatas);

		return activitiesRes;
	}
	
	public ActivitiesRes getAllEventById() {
		final List<Activity> activities = activityDao.getAllEventById(principalService.getAuthPrincipal());
		final List<ActivityData> activityDatas = new ArrayList<>();
		for (int i = 0; i < activities.size(); i++) {
			final Activity activity = activities.get(i);
			final ActivityData activityData = new ActivityData();
			activityData.setId(activity.getId());
			activityData.setVersion(activity.getVersion());
			activityData.setActivityTitle(activity.getId());
			activityData.setActivityLocation(activity.getActivityLocation());
			activityData.setStartAt(activity.getStartAt());
			activityData.setEndAt(activity.getEndAt());
			activityData.setFee(activity.getFee());
			activityData.setActivityTypeId(activity.getActivityType().getId());
			activityData.setActivityTypeCode(activity.getActivityType().getActivityTypeCode());
			activityData.setActivityTypeName(activity.getActivityType().getActivityTypeName());
			activityData.setCreatorName(activity.getUser().getFullname());
			activityData.setCreatedBy(activity.getCreatedBy());
			activityData.setCreatedAt(activity.getCreatedAt());
			activityData.setUpdatedAt(activity.getUpdatedAt());

			final List<AttachmentActivity> attachmentActivities = attachmentActivityDao.getAllById(activity.getId());
			final int attachmentActivitySize = attachmentActivities.size();
			final List<AttachmentActivityData> attachmentActivityDatas = new ArrayList<>();
			for (int x = 0; x < attachmentActivitySize; x++) {
				final AttachmentActivity attachmentActivity = attachmentActivities.get(x);
				final AttachmentActivityData attachmentActivityData = new AttachmentActivityData();
				attachmentActivityData.setId(attachmentActivity.getId());
				attachmentActivityData.setActivityId(attachmentActivity.getActivity().getId());
				attachmentActivityData.setFileId(attachmentActivity.getFile().getId());

				attachmentActivityDatas.add(attachmentActivityData);
			}
			activityData.setAttachmentActivityDatas(attachmentActivityDatas);
		}
		final ActivitiesRes activitiesRes = new ActivitiesRes();
		activitiesRes.setData(activityDatas);

		return activitiesRes;
	}
	
	public ActivitiesRes getAllCourseById() {
		final List<Activity> activities = activityDao.getAllCourseById(principalService.getAuthPrincipal());
		final List<ActivityData> activityDatas = new ArrayList<>();
		for (int i = 0; i < activities.size(); i++) {
			final Activity activity = activities.get(i);
			final ActivityData activityData = new ActivityData();
			activityData.setId(activity.getId());
			activityData.setVersion(activity.getVersion());
			activityData.setActivityTitle(activity.getId());
			activityData.setActivityLocation(activity.getActivityLocation());
			activityData.setStartAt(activity.getStartAt());
			activityData.setEndAt(activity.getEndAt());
			activityData.setFee(activity.getFee());
			activityData.setActivityTypeId(activity.getActivityType().getId());
			activityData.setActivityTypeCode(activity.getActivityType().getActivityTypeCode());
			activityData.setActivityTypeName(activity.getActivityType().getActivityTypeName());
			activityData.setCreatorName(activity.getUser().getFullname());
			activityData.setCreatedBy(activity.getCreatedBy());
			activityData.setCreatedAt(activity.getCreatedAt());
			activityData.setUpdatedAt(activity.getUpdatedAt());

			final List<AttachmentActivity> attachmentActivities = attachmentActivityDao.getAllById(activity.getId());
			final int attachmentActivitySize = attachmentActivities.size();
			final List<AttachmentActivityData> attachmentActivityDatas = new ArrayList<>();
			for (int x = 0; x < attachmentActivitySize; x++) {
				final AttachmentActivity attachmentActivity = attachmentActivities.get(x);
				final AttachmentActivityData attachmentActivityData = new AttachmentActivityData();
				attachmentActivityData.setId(attachmentActivity.getId());
				attachmentActivityData.setActivityId(attachmentActivity.getActivity().getId());
				attachmentActivityData.setFileId(attachmentActivity.getFile().getId());

				attachmentActivityDatas.add(attachmentActivityData);
			}
			activityData.setAttachmentActivityDatas(attachmentActivityDatas);
		}
		final ActivitiesRes activitiesRes = new ActivitiesRes();
		activitiesRes.setData(activityDatas);

		return activitiesRes;
	}
	
	public ActivitiesRes getAllJoinedEventById() {
		final List<Activity> activities = activityDao.getAllJoinedEventById(principalService.getAuthPrincipal());
		final List<ActivityData> activityDatas = new ArrayList<>();
		for (int i = 0; i < activities.size(); i++) {
			final Activity activity = activities.get(i);
			final ActivityData activityData = new ActivityData();
			activityData.setId(activity.getId());
			activityData.setVersion(activity.getVersion());
			activityData.setActivityTitle(activity.getId());
			activityData.setActivityLocation(activity.getActivityLocation());
			activityData.setStartAt(activity.getStartAt());
			activityData.setEndAt(activity.getEndAt());
			activityData.setFee(activity.getFee());
			activityData.setActivityTypeId(activity.getActivityType().getId());
			activityData.setActivityTypeCode(activity.getActivityType().getActivityTypeCode());
			activityData.setActivityTypeName(activity.getActivityType().getActivityTypeName());
			activityData.setCreatorName(activity.getUser().getFullname());
			activityData.setCreatedBy(activity.getCreatedBy());
			activityData.setCreatedAt(activity.getCreatedAt());
			activityData.setUpdatedAt(activity.getUpdatedAt());

			final List<AttachmentActivity> attachmentActivities = attachmentActivityDao.getAllById(activity.getId());
			final int attachmentActivitySize = attachmentActivities.size();
			final List<AttachmentActivityData> attachmentActivityDatas = new ArrayList<>();
			for (int x = 0; x < attachmentActivitySize; x++) {
				final AttachmentActivity attachmentActivity = attachmentActivities.get(x);
				final AttachmentActivityData attachmentActivityData = new AttachmentActivityData();
				attachmentActivityData.setId(attachmentActivity.getId());
				attachmentActivityData.setActivityId(attachmentActivity.getActivity().getId());
				attachmentActivityData.setFileId(attachmentActivity.getFile().getId());

				attachmentActivityDatas.add(attachmentActivityData);
			}
			activityData.setAttachmentActivityDatas(attachmentActivityDatas);
		}
		final ActivitiesRes activitiesRes = new ActivitiesRes();
		activitiesRes.setData(activityDatas);

		return activitiesRes;
	}
	
	public ActivitiesRes getAllJoinedCourseById() {
		final List<Activity> activities = activityDao.getAllJoinedCourseById(principalService.getAuthPrincipal());
		final List<ActivityData> activityDatas = new ArrayList<>();
		for (int i = 0; i < activities.size(); i++) {
			final Activity activity = activities.get(i);
			final ActivityData activityData = new ActivityData();
			activityData.setId(activity.getId());
			activityData.setVersion(activity.getVersion());
			activityData.setActivityTitle(activity.getId());
			activityData.setActivityLocation(activity.getActivityLocation());
			activityData.setStartAt(activity.getStartAt());
			activityData.setEndAt(activity.getEndAt());
			activityData.setFee(activity.getFee());
			activityData.setActivityTypeId(activity.getActivityType().getId());
			activityData.setActivityTypeCode(activity.getActivityType().getActivityTypeCode());
			activityData.setActivityTypeName(activity.getActivityType().getActivityTypeName());
			activityData.setCreatorName(activity.getUser().getFullname());
			activityData.setCreatedBy(activity.getCreatedBy());
			activityData.setCreatedAt(activity.getCreatedAt());
			activityData.setUpdatedAt(activity.getUpdatedAt());

			final List<AttachmentActivity> attachmentActivities = attachmentActivityDao.getAllById(activity.getId());
			final int attachmentActivitySize = attachmentActivities.size();
			final List<AttachmentActivityData> attachmentActivityDatas = new ArrayList<>();
			for (int x = 0; x < attachmentActivitySize; x++) {
				final AttachmentActivity attachmentActivity = attachmentActivities.get(x);
				final AttachmentActivityData attachmentActivityData = new AttachmentActivityData();
				attachmentActivityData.setId(attachmentActivity.getId());
				attachmentActivityData.setActivityId(attachmentActivity.getActivity().getId());
				attachmentActivityData.setFileId(attachmentActivity.getFile().getId());

				attachmentActivityDatas.add(attachmentActivityData);
			}
			activityData.setAttachmentActivityDatas(attachmentActivityDatas);
		}
		final ActivitiesRes activitiesRes = new ActivitiesRes();
		activitiesRes.setData(activityDatas);

		return activitiesRes;
	}

	public ActivityRes getById(final String id) {
		final Activity activity = activityDao.getById(Activity.class, id);
		final ActivityData activityData = new ActivityData();
		activityData.setId(activity.getId());
		activityData.setVersion(activity.getVersion());
		activityData.setActivityTitle(activity.getId());
		activityData.setActivityLocation(activity.getActivityLocation());
		activityData.setStartAt(activity.getStartAt());
		activityData.setEndAt(activity.getEndAt());
		activityData.setFee(activity.getFee());
		activityData.setActivityTypeId(activity.getActivityType().getId());
		activityData.setActivityTypeCode(activity.getActivityType().getActivityTypeCode());
		activityData.setActivityTypeName(activity.getActivityType().getActivityTypeName());
		activityData.setCreatorName(activity.getUser().getFullname());
		activityData.setCreatedBy(activity.getCreatedBy());
		activityData.setCreatedAt(activity.getCreatedAt());
		activityData.setUpdatedAt(activity.getUpdatedAt());

		final List<AttachmentActivity> attachmentActivities = attachmentActivityDao.getAllById(activity.getId());
		final int attachmentActivitySize = attachmentActivities.size();
		final List<AttachmentActivityData> attachmentActivityDatas = new ArrayList<>();
		for (int x = 0; x < attachmentActivitySize; x++) {
			final AttachmentActivity attachmentActivity = attachmentActivities.get(x);
			final AttachmentActivityData attachmentActivityData = new AttachmentActivityData();
			attachmentActivityData.setId(attachmentActivity.getId());
			attachmentActivityData.setActivityId(attachmentActivity.getActivity().getId());
			attachmentActivityData.setFileId(attachmentActivity.getFile().getId());

			attachmentActivityDatas.add(attachmentActivityData);
		}
		activityData.setAttachmentActivityDatas(attachmentActivityDatas);
		final ActivityRes activityRes = new ActivityRes();
		activityRes.setData(activityData);

		return activityRes;
	}

	private void valInsert(final ActivityInsertReq data) {
		valContentNotNull(data);
	}

	private void valContentNotNull(final ActivityInsertReq data) {
		if (data.getActivityTitle() == null) {
			throw new RuntimeException("Title cannot be empty");
		}
		if (data.getActivityLocation() == null) {
			throw new RuntimeException("Location cannot be empty");
		}
	}

	private void valUpdate(final ActivityUpdateReq data) {
		valIdNotNull(data);
		valIdFound(data);
		valContentNotNull(data);
	}

	private void valIdNotNull(final ActivityUpdateReq data) {
		if (data.getId() == null) {
			throw new RuntimeException("id cannot be empty");
		}
	}

	private void valContentNotNull(final ActivityUpdateReq data) {
		if (data.getActivityTitle() == null) {
			throw new RuntimeException("Title cannot be empty");
		}
		if (data.getActivityLocation() == null) {
			throw new RuntimeException("Location cannot be empty");
		}
		if (data.getActivityTypeId() == null) {
			throw new RuntimeException("Activity type cannot be empty");
		}
		if (data.getIsActive() == null) {
			throw new RuntimeException("isActive cannot be empty");
		}
		if (data.getVersion() == null) {
			throw new RuntimeException("Version cannot be empty");
		}
	}

	private void valIdFound(final ActivityUpdateReq data) {
		final Activity activity = activityDao.getById(Activity.class, data.getId());
		if (activity == null) {
			throw new RuntimeException("activity not found");
		}
	}
}
