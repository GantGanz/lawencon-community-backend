package com.lawencon.community.dto.position;

import lombok.Data;

@Data
public class PositionUpdateReq {

	private String id;
	private String positionName;
	private Boolean isActive;
	private Integer version;
}
