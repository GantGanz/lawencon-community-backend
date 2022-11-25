package com.lawencon.community.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.Activity;
import com.lawencon.community.model.ActivityType;
import com.lawencon.community.model.File;
import com.lawencon.community.model.PaymentActivity;
import com.lawencon.community.model.User;

@Repository
public class PaymentActivityDao extends AbstractJpaDao {

	public List<PaymentActivity> getAllApproved(final Integer offset, final Integer limit) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT p.id, p.nominal, p.is_approved, p.file_id, p.user_id, u.fullname, ")
				.append("p.created_at, p.activity_id, a.title, p.ver, p.updated_at, at.activity_type_name, u.email ")
				.append("FROM t_payment_activity p ").append("INNER JOIN t_user u ON u.id = p.created_by ")
				.append("INNER JOIN t_activity a ON a.id = p.activity_id  ")
				.append("INNER JOIN t_activity_type at ON at.id = a.activity_type_id ")
				.append("WHERE p.is_approved = TRUE ")
				.append("ORDER BY p.created_at DESC ").append("LIMIT :limit OFFSET :offset");

		final String sql = str.toString();
		final List<?> result = createNativeQuery(sql).setParameter("offset", offset).setParameter("limit", limit)
				.getResultList();

		final List<PaymentActivity> paymentActivities = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(resultObj -> {
				final Object[] objArr = (Object[]) resultObj;
				final PaymentActivity paymentActivity = new PaymentActivity();
				paymentActivity.setId(objArr[0].toString());
				final BigDecimal bd = new BigDecimal(objArr[1].toString());
				paymentActivity.setNominal(bd);
				paymentActivity.setIsApproved(Boolean.valueOf(objArr[2].toString()));

				final File file = new File();
				file.setId(objArr[3].toString());
				paymentActivity.setFile(file);

				final User user = new User();
				user.setId(objArr[4].toString());
				user.setFullname(objArr[5].toString());
				user.setEmail(objArr[12].toString());
				paymentActivity.setUser(user);

				paymentActivity.setCreatedAt(Timestamp.valueOf(objArr[6].toString()).toLocalDateTime());

				final Activity activity = new Activity();
				activity.setId(objArr[7].toString());
				activity.setActivityTitle(objArr[8].toString());
				
				final ActivityType activityType = new ActivityType();
				activityType.setActivityTypeName(objArr[11].toString());
				activity.setActivityType(activityType);
				paymentActivity.setActivity(activity);

				paymentActivity.setVersion(Integer.valueOf(objArr[9].toString()));
				if (objArr[10] != null) {
					paymentActivity.setUpdatedAt(Timestamp.valueOf(objArr[10].toString()).toLocalDateTime());
				}
				paymentActivities.add(paymentActivity);
			});
		}

		return paymentActivities;
	}

	public List<PaymentActivity> getAllUnapproved(final Integer offset, final Integer limit) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT p.id, p.nominal, p.is_approved, p.file_id, p.user_id, u.fullname, ")
				.append("p.created_at, p.activity_id, a.title, p.ver, p.updated_at, at.activity_type_name, u.email ")
				.append("FROM t_payment_activity p ").append("INNER JOIN t_user u ON u.id = p.created_by ")
				.append("INNER JOIN t_activity a ON a.id = p.activity_id  ")
				.append("INNER JOIN t_activity_type at ON at.id = a.activity_type_id ")
				.append("WHERE p.is_approved = FALSE ").append("ORDER BY p.created_at DESC ")
				.append("LIMIT :limit OFFSET :offset");

		final String sql = str.toString();
		final List<?> result = createNativeQuery(sql).setParameter("offset", offset).setParameter("limit", limit)
				.getResultList();

		final List<PaymentActivity> paymentActivities = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(resultObj -> {
				final Object[] objArr = (Object[]) resultObj;
				final PaymentActivity paymentActivity = new PaymentActivity();
				paymentActivity.setId(objArr[0].toString());
				final BigDecimal bd = new BigDecimal(objArr[1].toString());
				paymentActivity.setNominal(bd);
				paymentActivity.setIsApproved(Boolean.valueOf(objArr[2].toString()));

				final File file = new File();
				file.setId(objArr[3].toString());
				paymentActivity.setFile(file);

				final User user = new User();
				user.setId(objArr[4].toString());
				user.setFullname(objArr[5].toString());
				user.setEmail(objArr[12].toString());
				paymentActivity.setUser(user);

				paymentActivity.setCreatedAt(Timestamp.valueOf(objArr[6].toString()).toLocalDateTime());

				final Activity activity = new Activity();
				activity.setId(objArr[7].toString());
				activity.setActivityTitle(objArr[8].toString());
				
				final ActivityType activityType = new ActivityType();
				activityType.setActivityTypeName(objArr[11].toString());
				activity.setActivityType(activityType);
				paymentActivity.setActivity(activity);

				paymentActivity.setVersion(Integer.valueOf(objArr[9].toString()));
				if (objArr[10] != null) {
					paymentActivity.setUpdatedAt(Timestamp.valueOf(objArr[10].toString()).toLocalDateTime());
				}
				paymentActivities.add(paymentActivity);
			});
		}

		return paymentActivities;
	}

	public List<PaymentActivity> getAllByCreatorId(final String userId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT p.id, p.nominal, p.is_approved, p.file_id, p.user_id, u.fullname,")
				.append("p.created_at, p.activity_id, a.title, p.ver, p.updated_at, at.activity_type_name, u.email ")
				.append("FROM t_payment_activity p ").append("INNER JOIN t_user u ON u.id = p.created_by ")
				.append("INNER JOIN t_activity a ON a.id = p.activity_id  ")
				.append("INNER JOIN t_activity_type at ON at.id = a.activity_type_id ").append("WHERE a.created_by = :userId ")
				.append("ORDER BY p.created_at DESC");

		final String sql = str.toString();
		final List<?> result = createNativeQuery(sql).setParameter("userId", userId).getResultList();

		final List<PaymentActivity> paymentActivities = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(resultObj -> {
				final Object[] objArr = (Object[]) resultObj;
				final PaymentActivity paymentActivity = new PaymentActivity();
				paymentActivity.setId(objArr[0].toString());
				final BigDecimal bd = new BigDecimal(objArr[1].toString());
				paymentActivity.setNominal(bd);
				paymentActivity.setIsApproved(Boolean.valueOf(objArr[2].toString()));

				final File file = new File();
				file.setId(objArr[3].toString());
				paymentActivity.setFile(file);

				final User user = new User();
				user.setId(objArr[4].toString());
				user.setFullname(objArr[5].toString());
				user.setEmail(objArr[12].toString());
				paymentActivity.setUser(user);

				paymentActivity.setCreatedAt(Timestamp.valueOf(objArr[6].toString()).toLocalDateTime());

				final Activity activity = new Activity();
				activity.setId(objArr[7].toString());
				activity.setActivityTitle(objArr[8].toString());
				
				final ActivityType activityType = new ActivityType();
				activityType.setActivityTypeName(objArr[11].toString());
				activity.setActivityType(activityType);
				paymentActivity.setActivity(activity);

				paymentActivity.setVersion(Integer.valueOf(objArr[9].toString()));
				if (objArr[10] != null) {
					paymentActivity.setUpdatedAt(Timestamp.valueOf(objArr[10].toString()).toLocalDateTime());
				}
				paymentActivities.add(paymentActivity);
			});
		}

		return paymentActivities;
	}

	public List<PaymentActivity> getAllByMemberId(final String userId, final Integer offset, final Integer limit) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT p.id, p.nominal, p.is_approved, p.file_id, p.user_id, u.fullname,")
				.append("p.created_at, p.activity_id, a.title, p.ver, p.updated_at, at.activity_type_name, u.email ")
				.append("FROM t_payment_activity p ").append("INNER JOIN t_user u ON u.id = p.created_by ")
				.append("INNER JOIN t_activity a ON a.id = p.activity_id  ")
				.append("INNER JOIN t_activity_type at ON at.id = a.activity_type_id ").append("WHERE p.created_by = :userId ")
				.append("ORDER BY p.created_at DESC ").append("LIMIT :limit OFFSET :offset");

		final String sql = str.toString();
		final List<?> result = createNativeQuery(sql).setParameter("userId", userId).setParameter("offset", offset)
				.setParameter("limit", limit).getResultList();

		final List<PaymentActivity> paymentActivities = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(resultObj -> {
				final Object[] objArr = (Object[]) resultObj;
				final PaymentActivity paymentActivity = new PaymentActivity();
				paymentActivity.setId(objArr[0].toString());
				final BigDecimal bd = new BigDecimal(objArr[1].toString());
				paymentActivity.setNominal(bd);
				paymentActivity.setIsApproved(Boolean.valueOf(objArr[2].toString()));

				final File file = new File();
				file.setId(objArr[3].toString());
				paymentActivity.setFile(file);

				final User user = new User();
				user.setId(objArr[4].toString());
				user.setFullname(objArr[5].toString());
				user.setEmail(objArr[12].toString());
				paymentActivity.setUser(user);

				paymentActivity.setCreatedAt(Timestamp.valueOf(objArr[6].toString()).toLocalDateTime());

				final Activity activity = new Activity();
				activity.setId(objArr[7].toString());
				activity.setActivityTitle(objArr[8].toString());
				
				final ActivityType activityType = new ActivityType();
				activityType.setActivityTypeName(objArr[11].toString());
				activity.setActivityType(activityType);
				paymentActivity.setActivity(activity);

				paymentActivity.setVersion(Integer.valueOf(objArr[9].toString()));
				if (objArr[10] != null) {
					paymentActivity.setUpdatedAt(Timestamp.valueOf(objArr[10].toString()).toLocalDateTime());
				}
				paymentActivities.add(paymentActivity);
			});
		}

		return paymentActivities;
	}

	public BigDecimal getCreatorIncome(final String userId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT SUM(nominal) ").append("FROM t_payment_activity p ").append("WHERE p.created_by = :userId ");

		BigDecimal totalIncome = null;
		try {
			final Object userObj = createNativeQuery(str.toString()).setParameter("userId", userId).getSingleResult();
			if (userObj != null) {
				totalIncome = BigDecimal.valueOf(Long.valueOf(userObj.toString()));
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return totalIncome;
	}

	public Long countAllUnapproved() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT count(p.id) ").append("FROM t_payment_activity p ").append("WHERE p.is_approved = FALSE ");

		Long total = null;
		try {
			final Object userObj = createNativeQuery(str.toString()).getSingleResult();
			if (userObj != null) {
				total = Long.valueOf(userObj.toString());
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	public Long countAllApproved() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT count(p.id) ").append("FROM t_payment_activity p ").append("WHERE p.is_approved = TRUE ");

		Long total = null;
		try {
			final Object userObj = createNativeQuery(str.toString()).getSingleResult();
			if (userObj != null) {
				total = Long.valueOf(userObj.toString());
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return total;
	}
}