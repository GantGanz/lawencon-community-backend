package com.lawencon.community.dao;
import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;

@Repository
public class PositionDao extends AbstractJpaDao {
	public String getByCode(final String positionCode) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT id from position where position_code = :positionCode LIMIT 1");

		String positionId = null;
		try {
			final Object userObj = createNativeQuery(str.toString())
					.setParameter("roleCode", positionCode).getSingleResult();
			if (userObj != null) {
				positionId = userObj.toString();
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return positionId;
	}
}