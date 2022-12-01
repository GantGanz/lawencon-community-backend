package com.lawencon.community.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.constant.ActivityTypeConstant;
import com.lawencon.community.dto.report.ReportData;
import com.lawencon.community.model.Activity;
import com.lawencon.community.model.ActivityType;
import com.lawencon.community.model.User;

@Repository
public class ActivityDao extends AbstractJpaDao {

	public List<Activity> getAllEvent(final Integer offset, final Integer limit) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT a.id, a.ver, a.title, a.activity_location, a.start_at, a.end_at, a.fee,")
				.append("a.activity_type_id, at.activity_type_name, u.fullname, a.created_by, ")
				.append("a.created_at, a.updated_at, a.is_active, at.activity_type_code, a.provider ")
				.append("FROM t_activity a ").append("INNER JOIN t_activity_type at ON at.id = a.activity_type_id ")
				.append("INNER JOIN t_user u ON u.id = a.created_by ")
				.append("WHERE at.activity_type_code = :activityTypeCode ").append("AND a.is_active = TRUE ")
				.append("ORDER BY a.created_at DESC");

		final String sql = str.toString();
		final List<?> result = createNativeQuery(sql, offset, limit)
				.setParameter("activityTypeCode", ActivityTypeConstant.EVENT.getActivityTypeCode()).getResultList();

		final List<Activity> activities = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(resultObj -> {
				final Object[] objArr = (Object[]) resultObj;
				final Activity activity = new Activity();
				activity.setId(objArr[0].toString());
				activity.setVersion(Integer.valueOf(objArr[1].toString()));
				activity.setActivityTitle(objArr[2].toString());
				activity.setActivityLocation(objArr[3].toString());
				activity.setStartAt(Timestamp.valueOf(objArr[4].toString()).toLocalDateTime());
				activity.setEndAt(Timestamp.valueOf(objArr[5].toString()).toLocalDateTime());
				final BigDecimal bd = new BigDecimal(objArr[6].toString());
				activity.setFee(bd);

				final ActivityType activityType = new ActivityType();
				activityType.setId(objArr[7].toString());
				activityType.setActivityTypeName(objArr[8].toString());
				activityType.setActivityTypeCode(objArr[14].toString());
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
				activity.setProvider(String.valueOf(objArr[15].toString()));

				activities.add(activity);
			});
		}

		return activities;
	}

	public List<Activity> getAllCourse(final Integer offset, final Integer limit) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT a.id, a.ver, a.title, a.activity_location, a.start_at, a.end_at, a.fee,")
				.append("a.activity_type_id, at.activity_type_name, u.fullname, a.created_by, ")
				.append("a.created_at, a.updated_at, a.is_active, at.activity_type_code, a.provider  ")
				.append("FROM t_activity a ").append("INNER JOIN t_activity_type at ON at.id = a.activity_type_id ")
				.append("INNER JOIN t_user u ON u.id = a.created_by ")
				.append("WHERE at.activity_type_code = :activityTypeCode ").append("AND a.is_active = TRUE ")
				.append("ORDER BY a.created_at DESC");

		final String sql = str.toString();
		final List<?> result = createNativeQuery(sql, offset, limit)
				.setParameter("activityTypeCode", ActivityTypeConstant.COURSE.getActivityTypeCode()).getResultList();

		final List<Activity> activities = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(resultObj -> {
				final Object[] objArr = (Object[]) resultObj;
				final Activity activity = new Activity();
				activity.setId(objArr[0].toString());
				activity.setVersion(Integer.valueOf(objArr[1].toString()));
				activity.setActivityTitle(objArr[2].toString());
				activity.setActivityLocation(objArr[3].toString());
				activity.setStartAt(Timestamp.valueOf(objArr[4].toString()).toLocalDateTime());
				activity.setEndAt(Timestamp.valueOf(objArr[5].toString()).toLocalDateTime());
				final BigDecimal bd = new BigDecimal(objArr[6].toString());
				activity.setFee(bd);

				final ActivityType activityType = new ActivityType();
				activityType.setId(objArr[7].toString());
				activityType.setActivityTypeName(objArr[8].toString());
				activityType.setActivityTypeCode(objArr[14].toString());
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
				activity.setProvider(String.valueOf(objArr[15].toString()));
				activities.add(activity);
			});
		}

		return activities;
	}

	public List<Activity> getAllEventById(final String userId, final Integer offset, final Integer limit) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT a.id, a.ver, a.title, a.activity_location, a.start_at, a.end_at, a.fee,")
				.append("a.activity_type_id, at.activity_type_name, u.fullname, a.created_by, ")
				.append("a.created_at, a.updated_at, a.is_active, at.activity_type_code, a.provider  ")
				.append("FROM t_activity a ").append("INNER JOIN t_activity_type at ON at.id = a.activity_type_id ")
				.append("INNER JOIN t_user u ON u.id = a.created_by ")
				.append("WHERE at.activity_type_code = :activityTypeCode ").append("AND a.is_active = TRUE ")
				.append("AND a.created_by = :userId ").append("ORDER BY a.created_at DESC");

		final String sql = str.toString();
		final List<?> result = createNativeQuery(sql, offset, limit)
				.setParameter("activityTypeCode", ActivityTypeConstant.EVENT.getActivityTypeCode())
				.setParameter("userId", userId).getResultList();

		final List<Activity> activities = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(resultObj -> {
				final Object[] objArr = (Object[]) resultObj;
				final Activity activity = new Activity();
				activity.setId(objArr[0].toString());
				activity.setVersion(Integer.valueOf(objArr[1].toString()));
				activity.setActivityTitle(objArr[2].toString());
				activity.setActivityLocation(objArr[3].toString());
				activity.setStartAt(Timestamp.valueOf(objArr[4].toString()).toLocalDateTime());
				activity.setEndAt(Timestamp.valueOf(objArr[5].toString()).toLocalDateTime());
				final BigDecimal bd = new BigDecimal(objArr[6].toString());
				activity.setFee(bd);

				final ActivityType activityType = new ActivityType();
				activityType.setId(objArr[7].toString());
				activityType.setActivityTypeName(objArr[8].toString());
				activityType.setActivityTypeCode(objArr[14].toString());
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
				activity.setProvider(String.valueOf(objArr[15].toString()));
				activities.add(activity);
			});
		}

		return activities;
	}

	public List<Activity> getAllCourseById(final String userId, final Integer offset, final Integer limit) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT a.id, a.ver, a.title, a.activity_location, a.start_at, a.end_at, a.fee,")
				.append("a.activity_type_id, at.activity_type_name, u.fullname, a.created_by, ")
				.append("a.created_at, a.updated_at, a.is_active, at.activity_type_code, a.provider  ")
				.append("FROM t_activity a ").append("INNER JOIN t_activity_type at ON at.id = a.activity_type_id ")
				.append("INNER JOIN t_user u ON u.id = a.created_by ")
				.append("WHERE at.activity_type_code = :activityTypeCode ").append("AND a.is_active = TRUE ")
				.append("AND a.created_by = :userId ").append("ORDER BY a.created_at DESC");

		final String sql = str.toString();
		final List<?> result = createNativeQuery(sql, offset, limit)
				.setParameter("activityTypeCode", ActivityTypeConstant.COURSE.getActivityTypeCode())
				.setParameter("userId", userId).getResultList();

		final List<Activity> activities = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(resultObj -> {
				final Object[] objArr = (Object[]) resultObj;
				final Activity activity = new Activity();
				activity.setId(objArr[0].toString());
				activity.setVersion(Integer.valueOf(objArr[1].toString()));
				activity.setActivityTitle(objArr[2].toString());
				activity.setActivityLocation(objArr[3].toString());
				activity.setStartAt(Timestamp.valueOf(objArr[4].toString()).toLocalDateTime());
				activity.setEndAt(Timestamp.valueOf(objArr[5].toString()).toLocalDateTime());
				final BigDecimal bd = new BigDecimal(objArr[6].toString());
				activity.setFee(bd);

				final ActivityType activityType = new ActivityType();
				activityType.setId(objArr[7].toString());
				activityType.setActivityTypeName(objArr[8].toString());
				activityType.setActivityTypeCode(objArr[14].toString());
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
				activity.setProvider(String.valueOf(objArr[15].toString()));

				activities.add(activity);
			});
		}

		return activities;
	}

	public List<Activity> getAllJoinedEventById(final String userId, final Integer offset, final Integer limit) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT a.id, a.ver, a.title, a.activity_location, a.start_at, a.end_at, a.fee,")
				.append("a.activity_type_id, at.activity_type_name, u.fullname, a.created_by, ")
				.append("a.created_at, a.updated_at, a.is_active, at.activity_type_code, a.provider  ")
				.append("FROM t_activity a ").append("INNER JOIN t_activity_type at ON at.id = a.activity_type_id ")
				.append("INNER JOIN t_payment_activity pa ON pa.activity_id = a.id ")
				.append("INNER JOIN t_user u ON u.id = pa.user_id ")
				.append("WHERE at.activity_type_code = :activityTypeCode ").append("AND pa.is_approved = TRUE ")
				.append("AND u.id = :userId ").append("AND a.is_active = TRUE ").append("ORDER BY a.created_at DESC");

		final String sql = str.toString();
		final List<?> result = createNativeQuery(sql, offset, limit)
				.setParameter("activityTypeCode", ActivityTypeConstant.EVENT.getActivityTypeCode())
				.setParameter("userId", userId).getResultList();

		final List<Activity> activities = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(resultObj -> {
				final Object[] objArr = (Object[]) resultObj;
				final Activity activity = new Activity();
				activity.setId(objArr[0].toString());
				activity.setVersion(Integer.valueOf(objArr[1].toString()));
				activity.setActivityTitle(objArr[2].toString());
				activity.setActivityLocation(objArr[3].toString());
				activity.setStartAt(Timestamp.valueOf(objArr[4].toString()).toLocalDateTime());
				activity.setEndAt(Timestamp.valueOf(objArr[5].toString()).toLocalDateTime());
				final BigDecimal bd = new BigDecimal(objArr[6].toString());
				activity.setFee(bd);

				final ActivityType activityType = new ActivityType();
				activityType.setId(objArr[7].toString());
				activityType.setActivityTypeName(objArr[8].toString());
				activityType.setActivityTypeCode(objArr[14].toString());
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
				activity.setProvider(String.valueOf(objArr[15].toString()));

				activities.add(activity);
			});
		}

		return activities;
	}

	public List<Activity> getAllJoinedCourseById(final String userId, final Integer offset, final Integer limit) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT a.id, a.ver, a.title, a.activity_location, a.start_at, a.end_at, a.fee,")
				.append("a.activity_type_id, at.activity_type_name, u.fullname, a.created_by, ")
				.append("a.created_at, a.updated_at, a.is_active, at.activity_type_code, a.provider  ")
				.append("FROM t_activity a ").append("INNER JOIN t_activity_type at ON at.id = a.activity_type_id ")
				.append("INNER JOIN t_payment_activity pa ON pa.activity_id = a.id ")
				.append("INNER JOIN t_user u ON u.id = pa.user_id ")
				.append("WHERE at.activity_type_code = :activityTypeCode ").append("AND pa.is_approved = TRUE ")
				.append("AND u.id = :userId ").append("ORDER BY a.created_at DESC");

		final String sql = str.toString();
		final List<?> result = createNativeQuery(sql, offset, limit)
				.setParameter("activityTypeCode", ActivityTypeConstant.COURSE.getActivityTypeCode())
				.setParameter("userId", userId).getResultList();

		final List<Activity> activities = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(resultObj -> {
				final Object[] objArr = (Object[]) resultObj;
				final Activity activity = new Activity();
				activity.setId(objArr[0].toString());
				activity.setVersion(Integer.valueOf(objArr[1].toString()));
				activity.setActivityTitle(objArr[2].toString());
				activity.setActivityLocation(objArr[3].toString());
				activity.setStartAt(Timestamp.valueOf(objArr[4].toString()).toLocalDateTime());
				activity.setEndAt(Timestamp.valueOf(objArr[5].toString()).toLocalDateTime());
				final BigDecimal bd = new BigDecimal(objArr[6].toString());
				activity.setFee(bd);

				final ActivityType activityType = new ActivityType();
				activityType.setId(objArr[7].toString());
				activityType.setActivityTypeName(objArr[8].toString());
				activityType.setActivityTypeCode(objArr[14].toString());
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
				activity.setProvider(String.valueOf(objArr[15].toString()));

				activities.add(activity);
			});
		}

		return activities;
	}
	
	public Long countMyActivity(final String userId, final String activityTypeCode) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT COUNT(a.id) ").append("FROM t_activity a ")
				.append("INNER JOIN t_activity_type at ON at.id = a.activity_type_id ")
				.append("INNER JOIN t_user u ON u.id = a.created_by ")
				.append("WHERE at.activity_type_code = :activityTypeCode ").append("AND a.created_by = :userId ");

		Long total = null;
		try {
			final Object userObj = createNativeQuery(str.toString())
					.setParameter("activityTypeCode", activityTypeCode)
					.setParameter("userId", userId).getSingleResult();
			if (userObj != null) {
				total = Long.valueOf(userObj.toString());
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return total;
	}
	
	public List<ReportData> getMemberActivity(final String userId, final String startDate, final String endDate) {
		final StringBuilder query = new StringBuilder()
				.append("SELECT ROW_NUMBER() OVER(), at.activity_type_name, a.title, a.start_at, COUNT(pa.user_id) ")
				.append("FROM t_payment_activity pa ")
				.append("INNER JOIN t_activity a ON pa.activity_id = a.id ")
				.append("INNER JOIN t_activity_type at ON a.activity_type_id = at.id ")
				.append("INNER JOIN t_user uc ON a.created_by = uc.id ")
				.append("WHERE a.created_at >= DATE(:startDate) AND a.created_at <= DATE(:endDate) ")
				.append("AND pa.is_approved = TRUE AND a.created_by = :userId ")
				.append("GROUP BY at.activity_type_name, a.title, a.start_at ")
				.append("ORDER BY a.start_at DESC, at.activity_type_name, a.title ");
		final List<?> result = createNativeQuery(query.toString())
				.setParameter("startDate", startDate).setParameter("endDate", endDate).setParameter("userId", userId).getResultList();
		final List<ReportData> data =  new ArrayList<>();
		if(result != null && result.size() > 0) {
			result.forEach(objCol -> {
				final Object[] objArr = (Object[]) objCol;
				final ReportData row = new ReportData();
				row.setNo(Long.valueOf(objArr[0].toString()));
				row.setActivityType(objArr[1].toString());
				row.setTitle(objArr[2].toString());
				row.setStartDate(Timestamp.valueOf(objArr[3].toString()).toLocalDateTime().toLocalDate());
				row.setTotalParticipants(Integer.valueOf(objArr[4].toString()));
				data.add(row);
			});
		}
		return data;
	}
	
	public List<ReportData> getActivitySuperAdmin(final String startDate, final String endDate) {
		final StringBuilder query = new StringBuilder()
				.append("SELECT ROW_NUMBER() OVER(), uc.fullname, a.provider, at.activity_type_name, a.title, a.start_at, COUNT(pa.user_id) ")
				.append("FROM t_payment_activity pa ")
				.append("INNER JOIN t_activity a ON pa.activity_id = a.id ")
				.append("INNER JOIN t_activity_type at ON a.activity_type_id = at.id ")
				.append("INNER JOIN t_user uc ON a.created_by = uc.id ")
				.append("WHERE a.created_at >= DATE(:startDate) AND a.created_at <= DATE(:endDate) AND pa.is_approved = TRUE ")
				.append("GROUP BY uc.fullname, a.provider, at.activity_type_name, a.title, a.start_at ")
				.append("ORDER BY a.start_at DESC, at.activity_type_name , a.title ");
		final List<?> result = createNativeQuery(query.toString())
				.setParameter("startDate", startDate).setParameter("endDate", endDate).getResultList();
		final List<ReportData> data =  new ArrayList<>();
		if(result != null && result.size() > 0) {
			result.forEach(objCol -> {
				final Object[] objArr = (Object[]) objCol;
				final ReportData row = new ReportData();
				row.setNo(Long.valueOf(objArr[0].toString()));
				row.setMemberName(objArr[1].toString());
				row.setProvider(objArr[2].toString());
				row.setActivityType(objArr[3].toString());
				row.setTitle(objArr[4].toString());
				row.setStartDate(Timestamp.valueOf(objArr[5].toString()).toLocalDateTime().toLocalDate());
				row.setTotalParticipants(Integer.valueOf(objArr[6].toString()));
				data.add(row);
			});
		}
		return data;
	}

}