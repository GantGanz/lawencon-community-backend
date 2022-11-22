package com.lawencon.community.dto.paymentactivity;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PaymentActivityInsertReq {

	private BigDecimal nominal;
	private String activityId;
	private String fileCodes;
	private String extensions;
}
