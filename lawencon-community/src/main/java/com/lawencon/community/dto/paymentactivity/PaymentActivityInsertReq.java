package com.lawencon.community.dto.paymentactivity;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PaymentActivityInsertReq {

	private BigDecimal nominal;
	private String userId;
	private String activityId;
}
