package com.lawencon.community.dao;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;

@Repository
public class IndustryDao extends AbstractJpaDao {

	public String getByCode(final String industryCode) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT id from industry where industry_code = :industryCode LIMIT 1");

		String industryId = null;
		try {
			final Object userObj = createNativeQuery(str.toString())
					.setParameter("roleCode", industryCode).getSingleResult();
			if (userObj != null) {
				industryId = userObj.toString();
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return industryId;
	}

}