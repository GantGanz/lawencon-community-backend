package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.Industry;

@Repository
public class IndustryDao extends AbstractJpaDao {

	public String getByCode(final String industryCode) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT id from t_industry where industry_code = :industryCode LIMIT 1");

		String industryId = null;
		try {
			final Object industryObj = createNativeQuery(str.toString()).setParameter("industryCode", industryCode)
					.getSingleResult();
			if (industryObj != null) {
				industryId = industryObj.toString();
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return industryId;
	}

	public List<Industry> getAllActive() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT i.id, i.industry_name, i.industry_code, i.ver, i.is_active ").append("FROM t_industry i ")
				.append("WHERE i.is_active = TRUE ").append("ORDER BY p.id DESC");

		final List<?> result = createNativeQuery(str.toString()).getResultList();

		final List<Industry> industries = new ArrayList<>();

		if (result != null && result.size() > 0) {
			result.forEach(industryObj -> {
				final Object[] objArr = (Object[]) industryObj;
				Industry industry = new Industry();
				industry.setId(objArr[0].toString());
				industry.setIndustryName(objArr[1].toString());
				industry.setIndustryCode(objArr[2].toString());
				industry.setVersion(Integer.valueOf(objArr[3].toString()));
				industry.setIsActive(Boolean.valueOf(objArr[4].toString()));
				industries.add(industry);
			});
		}

		return industries;
	}

}