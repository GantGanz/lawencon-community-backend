package com.lawencon.community.dto.industry;

import lombok.Data;

@Data
public class IndustryUpdateReq {

	private String id;
	private String industryName;
	private Boolean isActive;
	private Integer version;
}
