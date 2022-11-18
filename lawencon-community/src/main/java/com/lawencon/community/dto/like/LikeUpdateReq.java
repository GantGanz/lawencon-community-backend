package com.lawencon.community.dto.like;

import lombok.Data;

@Data
public class LikeUpdateReq {

	private String id;
	private Boolean isActive;
	private Integer version;
}
