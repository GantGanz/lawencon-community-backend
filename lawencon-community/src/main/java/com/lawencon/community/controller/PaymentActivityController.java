package com.lawencon.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.service.PaymentActivityService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("payment-activity")
public class PaymentActivityController {
	@Autowired
	private PaymentActivityService paymentActivityService;
	
	@GetMapping("count")
	public ResponseEntity<Long> countAllArticle() {
		final Long res = paymentActivityService.countAllUnapproved();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
