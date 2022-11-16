package com.lawencon.community.dto.paymentactivity;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PaymentActivityData {

	private String id;
	private BigDecimal nominal;
	private String userId;
	private String activityId;
	
}
