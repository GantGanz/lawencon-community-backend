package com.lawencon.community.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.File;
import com.lawencon.community.model.PaymentPremium;
import com.lawencon.community.model.User;

@Repository
public class PaymentPremiumDao extends AbstractJpaDao {

	public List<PaymentPremium> getAllApproved(final Integer offset, final Integer limit) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT p.id, p.nominal, p.is_approved, p.file_id, p.user_id, u.fullname, p.created_at ")
				.append("FROM t_payment_premium p ").append("INNER JOIN t_user u ON u.id = p.created_by ")
				.append("WHERE p.is_approved = TRUE ").append("ORDER BY a.id DESC ").append("LIMIT :limit OFFSET :offset");

		final String sql = str.toString();
		final List<?> result = createNativeQuery(sql).setParameter("offset", offset)
				.setParameter("limit", limit).getResultList();

		final List<PaymentPremium> paymentPremiums = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(resultObj -> {
				final Object[] objArr = (Object[]) resultObj;
				final PaymentPremium paymentPremium = new PaymentPremium();
				paymentPremium.setId(objArr[0].toString());
				paymentPremium.setNominal(BigDecimal.valueOf(Long.valueOf(objArr[1].toString())));
				paymentPremium.setIsApproved(Boolean.valueOf(objArr[2].toString()));

				final File file = new File();
				file.setId(objArr[3].toString());
				paymentPremium.setFile(file);

				final User user = new User();
				user.setId(objArr[4].toString());
				user.setFullname(objArr[5].toString());
				paymentPremium.setUser(user);

				paymentPremium.setCreatedAt(Timestamp.valueOf(objArr[6].toString()).toLocalDateTime());
				paymentPremiums.add(paymentPremium);
			});
		}

		return paymentPremiums;
	}

	public List<PaymentPremium> getAllUnapproved(final Integer offset, final Integer limit) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT p.id, p.nominal, p.is_approved, p.file_id, p.user_id, u.fullname, p.created_at ")
				.append("FROM t_payment_premium p ").append("INNER JOIN t_user u ON u.id = p.created_by ")
				.append("WHERE p.is_approved = FALSE ").append("ORDER BY a.id DESC ").append("LIMIT :limit OFFSET :offset");;

		final String sql = str.toString();
		final List<?> result = createNativeQuery(sql).setParameter("offset", offset)
				.setParameter("limit", limit).getResultList();

		final List<PaymentPremium> paymentPremiums = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(resultObj -> {
				final Object[] objArr = (Object[]) resultObj;
				final PaymentPremium paymentPremium = new PaymentPremium();
				paymentPremium.setId(objArr[0].toString());
				paymentPremium.setNominal(BigDecimal.valueOf(Long.valueOf(objArr[1].toString())));
				paymentPremium.setIsApproved(Boolean.valueOf(objArr[2].toString()));

				final File file = new File();
				file.setId(objArr[3].toString());
				paymentPremium.setFile(file);

				final User user = new User();
				user.setId(objArr[4].toString());
				user.setFullname(objArr[5].toString());
				paymentPremium.setUser(user);

				paymentPremium.setCreatedAt(Timestamp.valueOf(objArr[6].toString()).toLocalDateTime());
				paymentPremiums.add(paymentPremium);
			});
		}

		return paymentPremiums;
	}

	public Long countAllUnapproved() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT count(p.id) ").append("FROM t_payment_premium p ")
				.append("WHERE p.is_approved = FALSE ");

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
		str.append("SELECT count(p.id) ").append("FROM t_payment_premium p ")
				.append("WHERE p.is_approved = TRUE ");

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