package com.lawencon.community.dao;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.Position;

@Repository
public class PositionDao extends AbstractJpaDao {
	public String getByCode(final String positionCode) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT id from t_position where position_code = :positionCode LIMIT 1");

		String positionId = null;
		try {
			final Object userObj = createNativeQuery(str.toString())
					.setParameter("positionCode", positionCode).getSingleResult();
			if (userObj != null) {
				positionId = userObj.toString();
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return positionId;
	}
	
	public List<Position> getAllActive() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT p.id, p.position_name, p.position_code, p.ver, p.is_active ").append("FROM t_position p ")
				.append("WHERE p.is_active = TRUE ").append("ORDER BY p.id DESC");

		final List<?> result = createNativeQuery(str.toString()).getResultList();

		final List<Position> positions = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(positionObj -> {
				final Object[] objArr = (Object[]) positionObj;
				Position position = new Position();
				position.setId(objArr[0].toString());
				position.setPositionName(objArr[1].toString());
				position.setPositionCode(objArr[2].toString());
				position.setVersion(Integer.valueOf(objArr[3].toString()));
				position.setIsActive(Boolean.valueOf(objArr[4].toString()));
				positions.add(position);
			});
		}

		return positions;
	}
}