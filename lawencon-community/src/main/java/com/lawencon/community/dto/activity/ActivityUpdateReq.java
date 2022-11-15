package com.lawencon.community.dto.activity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ActivityUpdateReq {

	@NotNull(message = "Id can't be empty!")
	private String id;
	
	@NotEmpty(message = "Title can't be empty!")
	private String title;
	
	@NotEmpty(message = "Activity Location can't be empty!")
	private String activityLocation;
	
	@NotNull(message = "Start At can't be empty!")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
	private LocalDateTime startAt;
	
	@NotNull(message = "End At can't be empty!")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
	private LocalDateTime endAt;
	
	@NotNull(message = "Fee can't be empty!")
	private BigDecimal fee;

	@NotEmpty(message = "Activity Type Id can't be empty!")
	private String activityTypeId;

	@NotNull(message = "Is Active can't be empty!")
	private Boolean isActive;
	
	@NotNull(message = "Version can't be empty!")
	private Integer version;
}
