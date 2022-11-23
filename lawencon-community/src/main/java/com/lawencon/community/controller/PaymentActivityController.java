package com.lawencon.community.controller;

import java.math.BigDecimal;

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
import com.lawencon.community.dto.paymentactivity.PaymentActivitiesRes;
import com.lawencon.community.dto.paymentactivity.PaymentActivityInsertReq;
import com.lawencon.community.dto.paymentactivity.PaymentActivityRes;
import com.lawencon.community.dto.paymentactivity.PaymentActivityUpdateReq;
import com.lawencon.community.service.PaymentActivityService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("payment-activities")
public class PaymentActivityController {
	@Autowired
	private PaymentActivityService paymentActivityService;
	
	@GetMapping("count")
	public ResponseEntity<Long> countAllUnapproved() {
		final Long res = paymentActivityService.countAllUnapproved();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("creator-income")
	public ResponseEntity<BigDecimal> getCreatorIncome() {
		final BigDecimal res = paymentActivityService.getCreatorIncome();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("system-income")
	public ResponseEntity<BigDecimal> getSystemIncome() {
		final BigDecimal res = paymentActivityService.getSystemIncome();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("approved")
	public ResponseEntity<PaymentActivitiesRes> getAllApproved() {
		final PaymentActivitiesRes res = paymentActivityService.getAllApproved();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("unapproved")
	public ResponseEntity<PaymentActivitiesRes> getAllUnapproved() {
		final PaymentActivitiesRes res = paymentActivityService.getAllUnapproved();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("user")
	public ResponseEntity<PaymentActivitiesRes> getAllByCreatorId() {
		final PaymentActivitiesRes res = paymentActivityService.getAllByCreatorId();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<InsertRes> insert(@RequestBody final PaymentActivityInsertReq data) {
		final InsertRes res = paymentActivityService.insert(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<UpdateRes> update(@RequestBody final PaymentActivityUpdateReq data) {
		final UpdateRes res = paymentActivityService.approve(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<PaymentActivityRes> getById(@PathVariable("id") final String id) {
		final PaymentActivityRes result = paymentActivityService.getById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
