package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.IndustryDao;
import com.lawencon.community.dto.InsertDataRes;
import com.lawencon.community.dto.InsertRes;
import com.lawencon.community.dto.industry.IndustriesRes;
import com.lawencon.community.dto.industry.IndustryData;
import com.lawencon.community.dto.industry.IndustryInsertReq;
import com.lawencon.community.model.Industry;

@Service
public class IndustryService extends BaseCoreService {
	@Autowired
	private IndustryDao industryDao;

	public InsertRes insert(final IndustryInsertReq data) {
		valInsert(data);

		Industry industryInsert = new Industry();
		industryInsert.setIndustryCode(data.getIndustryCode());
		industryInsert.setIndustryName(data.getIndustryName());

		try {
			begin();
			industryInsert = industryDao.save(industryInsert);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new RuntimeException("Failed to insert industry");
		}
		final InsertDataRes dataRes = new InsertDataRes();
		dataRes.setId(industryInsert.getId());

		final InsertRes insertRes = new InsertRes();
		insertRes.setData(dataRes);
		insertRes.setMessage("Industry successfully inserted");

		return insertRes;
	}

	private void valInsert(final IndustryInsertReq data) {
		bkNotDuplicate(data);
	}

	private void bkNotDuplicate(final IndustryInsertReq data) {
		final String industryId = industryDao.getByCode(data.getIndustryCode());
		if (industryId != null) {
			throw new RuntimeException("Code already used");
		}
	}

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
