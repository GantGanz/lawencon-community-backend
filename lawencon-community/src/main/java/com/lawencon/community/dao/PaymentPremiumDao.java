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
		str.append(
				"SELECT p.id, p.nominal, p.is_approved, p.file_id, p.user_id, u.fullname, p.created_at, u.email, p.ver, p.updated_at ")
				.append("FROM t_payment_premium p ").append("INNER JOIN t_user u ON u.id = p.created_by ")
				.append("WHERE p.is_approved = TRUE ").append("ORDER BY p.created_at DESC ")
				.append("LIMIT :limit OFFSET :offset");

		final String sql = str.toString();
		final List<?> result = createNativeQuery(sql).setParameter("offset", offset).setParameter("limit", limit)
				.getResultList();

		final List<PaymentPremium> paymentPremiums = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(resultObj -> {
				final Object[] objArr = (Object[]) resultObj;
				final PaymentPremium paymentPremium = new PaymentPremium();
				paymentPremium.setId(objArr[0].toString());
				final BigDecimal bd = new BigDecimal(objArr[1].toString());
				paymentPremium.setNominal(bd);
				paymentPremium.setIsApproved(Boolean.valueOf(objArr[2].toString()));

				final File file = new File();
				file.setId(objArr[3].toString());
				paymentPremium.setFile(file);

				final User user = new User();
				user.setId(objArr[4].toString());
				user.setFullname(objArr[5].toString());
				user.setEmail(objArr[7].toString());
				paymentPremium.setUser(user);

				paymentPremium.setCreatedAt(Timestamp.valueOf(objArr[6].toString()).toLocalDateTime());
				paymentPremium.setVersion(Integer.valueOf(objArr[8].toString()));
				if (objArr[9] != null) {
					paymentPremium.setUpdatedAt(Timestamp.valueOf(objArr[9].toString()).toLocalDateTime());
				}
				paymentPremiums.add(paymentPremium);
			});
		}

		return paymentPremiums;
	}
	
	public List<PaymentPremium> getAllRejected(final Integer offset, final Integer limit) {
		final StringBuilder str = new StringBuilder();
		str.append(
				"SELECT p.id, p.nominal, p.is_approved, p.file_id, p.user_id, u.fullname, p.created_at, u.email, p.ver, p.updated_at ")
				.append("FROM t_payment_premium p ").append("INNER JOIN t_user u ON u.id = p.created_by ")
				.append("WHERE p.is_active = TRUE ").append("ORDER BY p.created_at DESC ")
				.append("LIMIT :limit OFFSET :offset");

		final String sql = str.toString();
		final List<?> result = createNativeQuery(sql).setParameter("offset", offset).setParameter("limit", limit)
				.getResultList();

		final List<PaymentPremium> paymentPremiums = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(resultObj -> {
				final Object[] objArr = (Object[]) resultObj;
				final PaymentPremium paymentPremium = new PaymentPremium();
				paymentPremium.setId(objArr[0].toString());
				final BigDecimal bd = new BigDecimal(objArr[1].toString());
				paymentPremium.setNominal(bd);
				paymentPremium.setIsApproved(Boolean.valueOf(objArr[2].toString()));

				final File file = new File();
				file.setId(objArr[3].toString());
				paymentPremium.setFile(file);

				final User user = new User();
				user.setId(objArr[4].toString());
				user.setFullname(objArr[5].toString());
				user.setEmail(objArr[7].toString());
				paymentPremium.setUser(user);

				paymentPremium.setCreatedAt(Timestamp.valueOf(objArr[6].toString()).toLocalDateTime());
				paymentPremium.setVersion(Integer.valueOf(objArr[8].toString()));
				if (objArr[9] != null) {
					paymentPremium.setUpdatedAt(Timestamp.valueOf(objArr[9].toString()).toLocalDateTime());
				}
				paymentPremiums.add(paymentPremium);
			});
		}

		return paymentPremiums;
	}

	public List<PaymentPremium> getAllUnapproved(final Integer offset, final Integer limit) {
		final StringBuilder str = new StringBuilder();
		str.append(
				"SELECT p.id, p.nominal, p.is_approved, p.file_id, p.user_id, u.fullname, p.created_at, u.email, p.ver, p.updated_at ")
				.append("FROM t_payment_premium p ").append("INNER JOIN t_user u ON u.id = p.created_by ")
				.append("WHERE p.is_approved = FALSE ").append("ORDER BY p.created_at DESC ")
				.append("LIMIT :limit OFFSET :offset");
		;

		final String sql = str.toString();
		final List<?> result = createNativeQuery(sql).setParameter("offset", offset).setParameter("limit", limit)
				.getResultList();

		final List<PaymentPremium> paymentPremiums = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(resultObj -> {
				final Object[] objArr = (Object[]) resultObj;
				final PaymentPremium paymentPremium = new PaymentPremium();
				paymentPremium.setId(objArr[0].toString());
				final BigDecimal bd = new BigDecimal(objArr[1].toString());
				paymentPremium.setNominal(bd);
				paymentPremium.setIsApproved(Boolean.valueOf(objArr[2].toString()));

				final File file = new File();
				file.setId(objArr[3].toString());
				paymentPremium.setFile(file);

				final User user = new User();
				user.setId(objArr[4].toString());
				user.setFullname(objArr[5].toString());
				user.setEmail(objArr[7].toString());
				paymentPremium.setUser(user);

				paymentPremium.setCreatedAt(Timestamp.valueOf(objArr[6].toString()).toLocalDateTime());
				paymentPremium.setVersion(Integer.valueOf(objArr[8].toString()));
				if (objArr[9] != null) {
					paymentPremium.setUpdatedAt(Timestamp.valueOf(objArr[9].toString()).toLocalDateTime());
				}
				paymentPremiums.add(paymentPremium);
			});
		}

		return paymentPremiums;
	}

	public Long countAllUnapproved() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT count(p.id) ").append("FROM t_payment_premium p ").append("WHERE p.is_approved = FALSE ");

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
		str.append("SELECT count(p.id) ").append("FROM t_payment_premium p ").append("WHERE p.is_approved = TRUE ");

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
	
	public Long countAllRejected() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT count(p.id) ").append("FROM t_payment_premium p ").append("WHERE p.is_active = TRUE ");

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

	public Long checkStatus(final String userId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT count(p.id) ").append("FROM t_payment_premium p ").append("WHERE p.is_approved = TRUE ")
				.append("AND p.user_id = :userId");

		Long total = null;
		try {
			final Object userObj = createNativeQuery(str.toString()).setParameter("userId", userId).getSingleResult();
			if (userObj != null) {
				total = Long.valueOf(userObj.toString());
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	public Long checkPaid(final String userId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT count(p.id) ").append("FROM t_payment_premium p ").append("WHERE p.user_id = :userId");

		Long total = null;
		try {
			final Object userObj = createNativeQuery(str.toString()).setParameter("userId", userId).getSingleResult();
			if (userObj != null) {
				total = Long.valueOf(userObj.toString());
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return total;
	}

}