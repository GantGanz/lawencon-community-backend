package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.IndustryDao;
import com.lawencon.community.dto.industry.IndustriesRes;
import com.lawencon.community.dto.industry.IndustryData;
import com.lawencon.community.model.Industry;

@Service
public class IndustryService extends BaseCoreService {
	@Autowired
	private IndustryDao industryDao;

	public IndustriesRes getAll() {
		final List<Industry> industries = industryDao.getAll(Industry.class);
		final List<IndustryData> industryDatas = new ArrayList<>();
		for (int i = 0; i < industries.size(); i++) {
			final Industry industry = industries.get(i);
			final IndustryData industryData = new IndustryData();
			industryData.setId(industry.getId());
			industryData.setIndustryCode(industry.getIndustryCode());
			industryData.setIndustryName(industry.getIndustryName());
			industryData.setVersion(industry.getVersion());

			industryDatas.add(industryData);
		}
		final IndustriesRes industriesRes = new IndustriesRes();
		industriesRes.setData(industryDatas);

		return industriesRes;
	}
}
