package com.lawencon.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.dto.InsertRes;
import com.lawencon.community.dto.UpdateRes;
import com.lawencon.community.dto.paymentpremium.PaymentPremiumInsertReq;
import com.lawencon.community.dto.paymentpremium.PaymentPremiumRes;
import com.lawencon.community.dto.paymentpremium.PaymentPremiumUpdateReq;
import com.lawencon.community.dto.paymentpremium.PaymentPremiumsRes;
import com.lawencon.community.service.PaymentPremiumService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("payment-premiums")
public class PaymentPremiumController {
	@Autowired
	private PaymentPremiumService paymentPremiumService;
	
	@GetMapping("count")
	public ResponseEntity<Long> countAllUnapproved() {
		final Long res = paymentPremiumService.countAllUnapproved();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("approved")
	public ResponseEntity<PaymentPremiumsRes> getAllApproved() {
		final PaymentPremiumsRes res = paymentPremiumService.getAllApproved();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("unapproved")
	public ResponseEntity<PaymentPremiumsRes> getAllUnapproved() {
		final PaymentPremiumsRes res = paymentPremiumService.getAllUnapproved();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<InsertRes> insert(@RequestBody final PaymentPremiumInsertReq data) {
		final InsertRes res = paymentPremiumService.insert(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<UpdateRes> update(@RequestBody final PaymentPremiumUpdateReq data) {
		final UpdateRes res = paymentPremiumService.approve(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<PaymentPremiumRes> getById(@PathVariable("id") final String id) {
		final PaymentPremiumRes result = paymentPremiumService.getById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
