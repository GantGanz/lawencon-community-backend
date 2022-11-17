package com.lawencon.community.dao;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.Activity;
import com.lawencon.community.model.File;
import com.lawencon.community.model.PaymentActivity;
import com.lawencon.community.model.User;

@Repository
public class PaymentActivityDao extends AbstractJpaDao {

	public List<PaymentActivity> getAllApproved() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT p.id, p.nominal, p.is_approved, p.file_id, p.user_id, u.fullname, p.created_at, p.activity_id ")
				.append("FROM t_payment_Activity p ")
				.append("INNER JOIN t_user u ON u.id = p.created_by ")
				.append("WHERE p.is_approved = TRUE ")
				.append("ORDER BY a.id DESC");

		final String sql = str.toString();
		final List<?> result = createNativeQuery(sql).getResultList();

		final List<PaymentActivity> paymentActivities = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(resultObj -> {
				final Object[] objArr = (Object[]) resultObj;
				final PaymentActivity paymentActivity = new PaymentActivity();
				paymentActivity.setId(objArr[0].toString());
				paymentActivity.setNominal(BigDecimal.valueOf(Long.valueOf(objArr[1].toString())));
				paymentActivity.setIsApproved(Boolean.valueOf(objArr[2].toString()));

				final File file = new File();
				file.setId(objArr[3].toString());
				paymentActivity.setFile(file);

				final User user = new User();
				user.setId(objArr[4].toString());
				user.setFullname(objArr[5].toString());
				paymentActivity.setUser(user);

				paymentActivity.setCreatedAt(Timestamp.valueOf(objArr[6].toString()).toLocalDateTime());
				
				final Activity activity = new Activity();
				activity.setId(objArr[7].toString());
				paymentActivity.setActivity(activity);
				
				paymentActivities.add(paymentActivity);
			});
		}

		return paymentActivities;
	}
	
	public List<PaymentActivity> getAllUnapproved() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT p.id, p.nominal, p.is_approved, p.file_id, p.user_id, u.fullname, p.created_at, p.activity_id ")
				.append("FROM t_payment_Activity p ")
				.append("INNER JOIN t_user u ON u.id = p.created_by ")
				.append("WHERE p.is_approved = FALSE ")
				.append("ORDER BY a.id DESC");

		final String sql = str.toString();
		final List<?> result = createNativeQuery(sql).getResultList();

		final List<PaymentActivity> paymentActivities = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(resultObj -> {
				final Object[] objArr = (Object[]) resultObj;
				final PaymentActivity paymentActivity = new PaymentActivity();
				paymentActivity.setId(objArr[0].toString());
				paymentActivity.setNominal(BigDecimal.valueOf(Long.valueOf(objArr[1].toString())));
				paymentActivity.setIsApproved(Boolean.valueOf(objArr[2].toString()));

				final File file = new File();
				file.setId(objArr[3].toString());
				paymentActivity.setFile(file);

				final User user = new User();
				user.setId(objArr[4].toString());
				user.setFullname(objArr[5].toString());
				paymentActivity.setUser(user);

				paymentActivity.setCreatedAt(Timestamp.valueOf(objArr[6].toString()).toLocalDateTime());
				
				final Activity activity = new Activity();
				activity.setId(objArr[7].toString());
				paymentActivity.setActivity(activity);
				
				paymentActivities.add(paymentActivity);
			});
		}

		return paymentActivities;
	}
}