package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.PositionDao;
import com.lawencon.community.dto.position.PositionData;
import com.lawencon.community.dto.position.PositionsRes;
import com.lawencon.community.model.Position;

@Service
public class PositionService extends BaseCoreService {
	@Autowired
	private PositionDao positionDao;

	public PositionsRes getAll() {
		final List<Position> positions = positionDao.getAll(Position.class);
		final List<PositionData> positionDatas = new ArrayList<>();
		for (int i = 0; i < positions.size(); i++) {
			final Position position = positions.get(i);
			final PositionData positionData = new PositionData();
			positionData.setId(position.getId());
			positionData.setIndustryCode(position.getPositionCode());
			positionData.setIndustryName(position.getPositionName());
			positionData.setVersion(position.getVersion());

			positionDatas.add(positionData);
		}
		final PositionsRes positionsRes = new PositionsRes();
		positionsRes.setData(positionDatas);

		return positionsRes;
	}
}
