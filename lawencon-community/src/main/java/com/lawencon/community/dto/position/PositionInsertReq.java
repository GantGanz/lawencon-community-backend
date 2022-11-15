package com.lawencon.community.dto.position;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PositionInsertReq {

	@NotEmpty(message = "Position Code can't be empty!")
	@Size(max = 5, message = "Position Code is too long!")
	private String positionCode;
	
	@NotEmpty(message = "Position Name can't be empty!")
	@Size(max = 50, message = "Position Name is too long!")
	private String positionName;

}
