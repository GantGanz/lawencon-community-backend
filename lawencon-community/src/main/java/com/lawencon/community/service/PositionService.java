package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.PositionDao;
import com.lawencon.community.dto.InsertDataRes;
import com.lawencon.community.dto.InsertRes;
import com.lawencon.community.dto.UpdateDataRes;
import com.lawencon.community.dto.UpdateRes;
import com.lawencon.community.dto.position.PositionData;
import com.lawencon.community.dto.position.PositionInsertReq;
import com.lawencon.community.dto.position.PositionUpdateReq;
import com.lawencon.community.dto.position.PositionsRes;
import com.lawencon.community.model.Position;

@Service
public class PositionService extends BaseCoreService {
	@Autowired
	private PositionDao positionDao;

	public InsertRes insert(final PositionInsertReq data) {
		valInsert(data);

		Position positionInsert = new Position();
		positionInsert.setPositionCode(data.getPositionCode());
		positionInsert.setPositionName(data.getPositionName());

		try {
			begin();
			positionInsert = positionDao.save(positionInsert);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new RuntimeException("Failed to insert position");
		}

		final InsertDataRes dataRes = new InsertDataRes();
		dataRes.setId(positionInsert.getId());

		final InsertRes insertRes = new InsertRes();
		insertRes.setData(dataRes);
		insertRes.setMessage("Position successfully inserted");

		return insertRes;
	}

	public UpdateRes update(final PositionUpdateReq data) {
		valUpdate(data);
		Position positionUpdate = positionDao.getByIdAndDetach(Position.class, data.getId());
		positionUpdate.setPositionName(data.getPositionName());
		positionUpdate.setIsActive(data.getIsActive());
		positionUpdate.setVersion(data.getVersion());

		try {
			begin();
			positionUpdate = positionDao.saveAndFlush(positionUpdate);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new RuntimeException("Update failed");
		}
		final UpdateDataRes dataRes = new UpdateDataRes();
		dataRes.setVersion(positionUpdate.getVersion());

		final UpdateRes res = new UpdateRes();
		res.setData(dataRes);
		res.setMessage("Update success");

		return res;
	}

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

	private void valInsert(final PositionInsertReq data) {
		valContentNotNull(data);
		valBkNotDuplicate(data);
	}

	private void valContentNotNull(final PositionInsertReq data) {
		if (data.getPositionCode() == null) {
			throw new RuntimeException("Position code cannot be empty");
		}
		if (data.getPositionName() == null) {
			throw new RuntimeException("Position name cannot be empty");
		}
	}

	private void valBkNotDuplicate(final PositionInsertReq data) {
		final String positionId = positionDao.getByCode(data.getPositionCode());
		if (positionId != null) {
			throw new RuntimeException("Code already used");
		}
	}

	private void valUpdate(final PositionUpdateReq data) {
		valIdNotNull(data);
		valIdFound(data);
		valContentNotNull(data);
	}

	private void valContentNotNull(final PositionUpdateReq data) {
		if (data.getPositionName() == null) {
			throw new RuntimeException("Position name cannot be empty");
		}
		if (data.getIsActive() == null) {
			throw new RuntimeException("Is Active cannot be empty");
		}
		if (data.getVersion() == null) {
			throw new RuntimeException("Version cannot be empty");
		}
	}

	private void valIdNotNull(final PositionUpdateReq data) {
		if (data.getId() == null) {
			throw new RuntimeException("Id cannot be empty");
		}
	}

	private void valIdFound(final PositionUpdateReq data) {
		final Position position = positionDao.getById(Position.class, data.getId());
		if (position == null) {
			throw new RuntimeException("Position not found");
		}
	}
}
