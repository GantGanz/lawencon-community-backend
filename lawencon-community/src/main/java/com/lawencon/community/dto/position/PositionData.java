package com.lawencon.community.dto.position;

import lombok.Data;

@Data
public class PositionData {

	private String id;
	private Integer version;
	private String positionCode;
	private String positionName;
	private Boolean isActive;
	
}
