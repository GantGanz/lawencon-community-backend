package com.lawencon.community.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.constant.ActivityTypeConstant;
import com.lawencon.community.model.Activity;
import com.lawencon.community.model.ActivityType;
import com.lawencon.community.model.User;

@Repository
public class ActivityDao extends AbstractJpaDao {

	public List<Activity> getAllEvent() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT a.id, a.ver, a.activity_title, a.activity_location, a.startAt, a.endAt, a.fee,")
				.append("a.activity_type_id, at.activity_type_name, u.fullname, a.created_by, ")
				.append("a.created_at, a.created_at, a.updated_at, a.is_active ").append("FROM t_activity a ")
				.append("INNER JOIN t_activity_type at ON at.id = a.activity_type_id ")
				.append("INNER JOIN t_user u ON u.id = a.created_by ")
				.append("WHERE pt.activity_type_code = :activityTypeCode ").append("ORDER BY a.id DESC");

		final String sql = str.toString();
		final List<?> result = createNativeQuery(sql)
				.setParameter("activityTypeCode", ActivityTypeConstant.EVENT.getActivityTypeCode()).getResultList();

		final List<Activity> activities = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(checkInObj -> {
				final Object[] objArr = (Object[]) checkInObj;
				final Activity activity = new Activity();
				activity.setId(objArr[0].toString());
				activity.setVersion(Integer.valueOf(objArr[1].toString()));
				activity.setActivityTitle(objArr[2].toString());
				activity.setActivityLocation(objArr[3].toString());
				activity.setStartAt(Timestamp.valueOf(objArr[4].toString()).toLocalDateTime());
				activity.setEndAt(Timestamp.valueOf(objArr[5].toString()).toLocalDateTime());
				activity.setFee(BigDecimal.valueOf(Long.valueOf(objArr[6].toString())));

				final ActivityType activityType = new ActivityType();
				activityType.setId(objArr[7].toString());
				activityType.setActivityTypeName(objArr[8].toString());
				activity.setActivityType(activityType);

				final User user = new User();
				user.setFullname(objArr[9].toString());
				activity.setUser(user);

				activity.setCreatedBy(objArr[10].toString());
				activity.setCreatedAt(Timestamp.valueOf(objArr[11].toString()).toLocalDateTime());
				if (objArr[12] != null) {
					activity.setUpdatedAt(Timestamp.valueOf(objArr[12].toString()).toLocalDateTime());
				}
				activity.setIsActive(Boolean.valueOf(objArr[13].toString()));

				activities.add(activity);
			});
		}

		return activities;
	}

	public List<Activity> getAllCourse() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT a.id, a.ver, a.activity_title, a.activity_location, a.startAt, a.endAt, a.fee,")
				.append("a.activity_type_id, at.activity_type_name, u.fullname, a.created_by, ")
				.append("a.created_at, a.created_at, a.updated_at, a.is_active ").append("FROM t_activity a ")
				.append("INNER JOIN t_activity_type at ON at.id = a.activity_type_id ")
				.append("INNER JOIN t_user u ON u.id = a.created_by ")
				.append("WHERE pt.activity_type_code = :activityTypeCode ").append("ORDER BY a.id DESC");

		final String sql = str.toString();
		final List<?> result = createNativeQuery(sql)
				.setParameter("activityTypeCode", ActivityTypeConstant.COURSE.getActivityTypeCode()).getResultList();

		final List<Activity> activities = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(checkInObj -> {
				final Object[] objArr = (Object[]) checkInObj;
				final Activity activity = new Activity();
				activity.setId(objArr[0].toString());
				activity.setVersion(Integer.valueOf(objArr[1].toString()));
				activity.setActivityTitle(objArr[2].toString());
				activity.setActivityLocation(objArr[3].toString());
				activity.setStartAt(Timestamp.valueOf(objArr[4].toString()).toLocalDateTime());
				activity.setEndAt(Timestamp.valueOf(objArr[5].toString()).toLocalDateTime());
				activity.setFee(BigDecimal.valueOf(Long.valueOf(objArr[6].toString())));

				final ActivityType activityType = new ActivityType();
				activityType.setId(objArr[7].toString());
				activityType.setActivityTypeName(objArr[8].toString());
				activity.setActivityType(activityType);

				final User user = new User();
				user.setFullname(objArr[9].toString());
				activity.setUser(user);

				activity.setCreatedBy(objArr[10].toString());
				activity.setCreatedAt(Timestamp.valueOf(objArr[11].toString()).toLocalDateTime());
				if (objArr[12] != null) {
					activity.setUpdatedAt(Timestamp.valueOf(objArr[12].toString()).toLocalDateTime());
				}
				activity.setIsActive(Boolean.valueOf(objArr[13].toString()));

				activities.add(activity);
			});
		}

		return activities;
	}

	public List<Activity> getAllEventById(final String userId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT a.id, a.ver, a.activity_title, a.activity_location, a.startAt, a.endAt, a.fee,")
				.append("a.activity_type_id, at.activity_type_name, u.fullname, a.created_by, ")
				.append("a.created_at, a.created_at, a.updated_at, a.is_active ").append("FROM t_activity a ")
				.append("INNER JOIN t_activity_type at ON at.id = a.activity_type_id ")
				.append("INNER JOIN t_user u ON u.id = a.created_by ")
				.append("WHERE pt.activity_type_code = :activityTypeCode ").append("AND a.createdBy = :userId ")
				.append("ORDER BY a.id DESC");

		final String sql = str.toString();
		final List<?> result = createNativeQuery(sql)
				.setParameter("activityTypeCode", ActivityTypeConstant.EVENT.getActivityTypeCode())
				.setParameter("userId", userId).getResultList();

		final List<Activity> activities = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(checkInObj -> {
				final Object[] objArr = (Object[]) checkInObj;
				final Activity activity = new Activity();
				activity.setId(objArr[0].toString());
				activity.setVersion(Integer.valueOf(objArr[1].toString()));
				activity.setActivityTitle(objArr[2].toString());
				activity.setActivityLocation(objArr[3].toString());
				activity.setStartAt(Timestamp.valueOf(objArr[4].toString()).toLocalDateTime());
				activity.setEndAt(Timestamp.valueOf(objArr[5].toString()).toLocalDateTime());
				activity.setFee(BigDecimal.valueOf(Long.valueOf(objArr[6].toString())));

				final ActivityType activityType = new ActivityType();
				activityType.setId(objArr[7].toString());
				activityType.setActivityTypeName(objArr[8].toString());
				activity.setActivityType(activityType);

				final User user = new User();
				user.setFullname(objArr[9].toString());
				activity.setUser(user);

				activity.setCreatedBy(objArr[10].toString());
				activity.setCreatedAt(Timestamp.valueOf(objArr[11].toString()).toLocalDateTime());
				if (objArr[12] != null) {
					activity.setUpdatedAt(Timestamp.valueOf(objArr[12].toString()).toLocalDateTime());
				}
				activity.setIsActive(Boolean.valueOf(objArr[13].toString()));

				activities.add(activity);
			});
		}

		return activities;
	}

	public List<Activity> getAllCourseById(final String userId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT a.id, a.ver, a.activity_title, a.activity_location, a.startAt, a.endAt, a.fee,")
				.append("a.activity_type_id, at.activity_type_name, u.fullname, a.created_by, ")
				.append("a.created_at, a.created_at, a.updated_at, a.is_active ").append("FROM t_activity a ")
				.append("INNER JOIN t_activity_type at ON at.id = a.activity_type_id ")
				.append("INNER JOIN t_user u ON u.id = a.created_by ")
				.append("WHERE pt.activity_type_code = :activityTypeCode ").append("AND a.createdBy = :userId ")
				.append("ORDER BY a.id DESC");

		final String sql = str.toString();
		final List<?> result = createNativeQuery(sql)
				.setParameter("activityTypeCode", ActivityTypeConstant.COURSE.getActivityTypeCode())
				.setParameter("userId", userId).getResultList();

		final List<Activity> activities = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(checkInObj -> {
				final Object[] objArr = (Object[]) checkInObj;
				final Activity activity = new Activity();
				activity.setId(objArr[0].toString());
				activity.setVersion(Integer.valueOf(objArr[1].toString()));
				activity.setActivityTitle(objArr[2].toString());
				activity.setActivityLocation(objArr[3].toString());
				activity.setStartAt(Timestamp.valueOf(objArr[4].toString()).toLocalDateTime());
				activity.setEndAt(Timestamp.valueOf(objArr[5].toString()).toLocalDateTime());
				activity.setFee(BigDecimal.valueOf(Long.valueOf(objArr[6].toString())));

				final ActivityType activityType = new ActivityType();
				activityType.setId(objArr[7].toString());
				activityType.setActivityTypeName(objArr[8].toString());
				activity.setActivityType(activityType);

				final User user = new User();
				user.setFullname(objArr[9].toString());
				activity.setUser(user);

				activity.setCreatedBy(objArr[10].toString());
				activity.setCreatedAt(Timestamp.valueOf(objArr[11].toString()).toLocalDateTime());
				if (objArr[12] != null) {
					activity.setUpdatedAt(Timestamp.valueOf(objArr[12].toString()).toLocalDateTime());
				}
				activity.setIsActive(Boolean.valueOf(objArr[13].toString()));

				activities.add(activity);
			});
		}

		return activities;
	}

	public List<Activity> getAllJoinedEventById(final String userId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT a.id, a.ver, a.activity_title, a.activity_location, a.startAt, a.endAt, a.fee,")
				.append("a.activity_type_id, at.activity_type_name, u.fullname, a.created_by, ")
				.append("a.created_at, a.created_at, a.updated_at, a.is_active ").append("FROM t_activity a ")
				.append("INNER JOIN t_activity_type at ON at.id = a.activity_type_id ")
				.append("INNER JOIN t_payment_activity pa ON pa.activity_id = a.id ")
				.append("INNER JOIN t_user u ON u.id = pa.user_id ")
				.append("WHERE pt.activity_type_code = :activityTypeCode ").append("AND pa.is_active = TRUE ")
				.append("ORDER BY a.id DESC");

		final String sql = str.toString();
		final List<?> result = createNativeQuery(sql)
				.setParameter("activityTypeCode", ActivityTypeConstant.EVENT.getActivityTypeCode())
				.setParameter("userId", userId).getResultList();

		final List<Activity> activities = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(checkInObj -> {
				final Object[] objArr = (Object[]) checkInObj;
				final Activity activity = new Activity();
				activity.setId(objArr[0].toString());
				activity.setVersion(Integer.valueOf(objArr[1].toString()));
				activity.setActivityTitle(objArr[2].toString());
				activity.setActivityLocation(objArr[3].toString());
				activity.setStartAt(Timestamp.valueOf(objArr[4].toString()).toLocalDateTime());
				activity.setEndAt(Timestamp.valueOf(objArr[5].toString()).toLocalDateTime());
				activity.setFee(BigDecimal.valueOf(Long.valueOf(objArr[6].toString())));

				final ActivityType activityType = new ActivityType();
				activityType.setId(objArr[7].toString());
				activityType.setActivityTypeName(objArr[8].toString());
				activity.setActivityType(activityType);

				final User user = new User();
				user.setFullname(objArr[9].toString());
				activity.setUser(user);

				activity.setCreatedBy(objArr[10].toString());
				activity.setCreatedAt(Timestamp.valueOf(objArr[11].toString()).toLocalDateTime());
				if (objArr[12] != null) {
					activity.setUpdatedAt(Timestamp.valueOf(objArr[12].toString()).toLocalDateTime());
				}
				activity.setIsActive(Boolean.valueOf(objArr[13].toString()));

				activities.add(activity);
			});
		}

		return activities;
	}

	public List<Activity> getAllJoinedCourseById(final String userId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT a.id, a.ver, a.activity_title, a.activity_location, a.startAt, a.endAt, a.fee,")
				.append("a.activity_type_id, at.activity_type_name, u.fullname, a.created_by, ")
				.append("a.created_at, a.created_at, a.updated_at, a.is_active ").append("FROM t_activity a ")
				.append("INNER JOIN t_activity_type at ON at.id = a.activity_type_id ")
				.append("INNER JOIN t_payment_activity pa ON pa.activity_id = a.id ")
				.append("INNER JOIN t_user u ON u.id = pa.user_id ")
				.append("WHERE pt.activity_type_code = :activityTypeCode ").append("AND pa.is_active = TRUE ")
				.append("ORDER BY a.id DESC");

		final String sql = str.toString();
		final List<?> result = createNativeQuery(sql)
				.setParameter("activityTypeCode", ActivityTypeConstant.COURSE.getActivityTypeCode())
				.setParameter("userId", userId).getResultList();

		final List<Activity> activities = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(checkInObj -> {
				final Object[] objArr = (Object[]) checkInObj;
				final Activity activity = new Activity();
				activity.setId(objArr[0].toString());
				activity.setVersion(Integer.valueOf(objArr[1].toString()));
				activity.setActivityTitle(objArr[2].toString());
				activity.setActivityLocation(objArr[3].toString());
				activity.setStartAt(Timestamp.valueOf(objArr[4].toString()).toLocalDateTime());
				activity.setEndAt(Timestamp.valueOf(objArr[5].toString()).toLocalDateTime());
				activity.setFee(BigDecimal.valueOf(Long.valueOf(objArr[6].toString())));

				final ActivityType activityType = new ActivityType();
				activityType.setId(objArr[7].toString());
				activityType.setActivityTypeName(objArr[8].toString());
				activity.setActivityType(activityType);

				final User user = new User();
				user.setFullname(objArr[9].toString());
				activity.setUser(user);

				activity.setCreatedBy(objArr[10].toString());
				activity.setCreatedAt(Timestamp.valueOf(objArr[11].toString()).toLocalDateTime());
				if (objArr[12] != null) {
					activity.setUpdatedAt(Timestamp.valueOf(objArr[12].toString()).toLocalDateTime());
				}
				activity.setIsActive(Boolean.valueOf(objArr[13].toString()));

				activities.add(activity);
			});
		}

		return activities;
	}
}