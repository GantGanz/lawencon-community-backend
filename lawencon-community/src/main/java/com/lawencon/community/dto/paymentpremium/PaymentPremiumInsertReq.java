package com.lawencon.community.dto.paymentpremium;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PaymentPremiumInsertReq {

	private BigDecimal nominal;
	private String fileId;
	private String userId;
}
