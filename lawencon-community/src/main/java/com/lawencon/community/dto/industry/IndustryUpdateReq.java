package com.lawencon.community.dto.industry;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class IndustryUpdateReq {

	@NotNull(message = "Id can't be empty!")
	@Size(max = 36, message = "Id is too long!")
	private String id;
	
	@NotEmpty(message = "Industry Code can't be empty!")
	@Size(max = 5, message = "Industry Code is too long!")
	private String industryCode;
	
	@NotEmpty(message = "Industry Name can't be empty!")
	@Size(max = 50, message = "Industry Name is too long!")
	private String industryName;
	
	@NotNull(message = "Is Active can't be empty!")
	private Boolean isActive;
	
	@NotNull(message = "Version can't be empty!")
	private Integer version;
}
