package com.lawencon.community.dto.industry;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class IndustryInsertReq {

	@NotEmpty(message = "Industry Code can't be empty!")
	@Size(max = 5, message = "Industry Code is too long!")
	private String industryCode;
	
	@NotEmpty(message = "Industry Name can't be empty!")
	@Size(max = 50, message = "Industry Name is too long!")
	private String industryName;

}
