package com.lawencon.community.dto.paymentpremium;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PaymentPremiumData {

	private String id;
	private BigDecimal nominal;
	private String userId;
	
}
