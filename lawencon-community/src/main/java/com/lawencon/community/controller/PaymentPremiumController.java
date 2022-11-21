package com.lawencon.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.service.PaymentPremiumService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("payment-premium")
public class PaymentPremiumController {
	@Autowired
	private PaymentPremiumService paymentPremiumService;
	
	@GetMapping("count")
	public ResponseEntity<Long> countAllArticle() {
		final Long res = paymentPremiumService.countAllUnapproved();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
