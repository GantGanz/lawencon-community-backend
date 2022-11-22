package com.lawencon.community.dto.paymentactivity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PaymentActivityData {

	private String id;
	private Integer version;
	private BigDecimal nominal;
	private Boolean isApproved;
	private String fileId;
	private String userId;
	private String fullname;
	private String email;
	private String activityId;
	private String activityTitle;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
}
