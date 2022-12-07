package com.lawencon.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.constant.RoleStaticConstant;
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
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.ADM +"')")
	public ResponseEntity<Long> countAllUnapproved() {
		final Long res = paymentPremiumService.countAllUnapproved();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("count-approved")
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.ADM +"')")
	public ResponseEntity<Long> countAllApproved() {
		final Long res = paymentPremiumService.countAllApproved();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("count-rejected")
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.ADM +"')")
	public ResponseEntity<Long> countAllRejected() {
		final Long res = paymentPremiumService.countAllRejected();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("approved")
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.ADM +"')")
	public ResponseEntity<PaymentPremiumsRes> getAllApproved(@RequestParam("offset") final Integer offset, @RequestParam("limit") final Integer limit) {
		final PaymentPremiumsRes res = paymentPremiumService.getAllApproved(offset, limit);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("unapproved")
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.ADM +"')")
	public ResponseEntity<PaymentPremiumsRes> getAllUnapproved(@RequestParam("offset") final Integer offset, @RequestParam("limit") final Integer limit) {
		final PaymentPremiumsRes res = paymentPremiumService.getAllUnapproved(offset, limit);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("rejected")
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.ADM +"')")
	public ResponseEntity<PaymentPremiumsRes> getAllRejected(@RequestParam("offset") final Integer offset, @RequestParam("limit") final Integer limit) {
		final PaymentPremiumsRes res = paymentPremiumService.getAllRejected(offset, limit);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.MMB +"')")
	public ResponseEntity<InsertRes> insert(@RequestBody final PaymentPremiumInsertReq data) {
		final InsertRes res = paymentPremiumService.insert(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PutMapping
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.ADM +"')")
	public ResponseEntity<UpdateRes> approve(@RequestBody final PaymentPremiumUpdateReq data) {
		final UpdateRes res = paymentPremiumService.approve(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PutMapping("reject")
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.ADM +"')")
	public ResponseEntity<UpdateRes> reject(@RequestBody final PaymentPremiumUpdateReq data) {
		final UpdateRes res = paymentPremiumService.reject(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("{id}")
	@PreAuthorize("hasAnyAuthority('"+ RoleStaticConstant.ADM +"', '"+ RoleStaticConstant.MMB +"')")
	public ResponseEntity<PaymentPremiumRes> getById(@PathVariable("id") final String id) {
		final PaymentPremiumRes result = paymentPremiumService.getById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("status")
	@PreAuthorize("hasAnyAuthority('"+ RoleStaticConstant.ADM +"', '"+ RoleStaticConstant.MMB +"')")
	public ResponseEntity<Boolean> checkStatus() {
		final Boolean result = paymentPremiumService.checkStatus();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("paid")
	@PreAuthorize("hasAnyAuthority('"+ RoleStaticConstant.ADM +"', '"+ RoleStaticConstant.MMB +"')")
	public ResponseEntity<Boolean> checkPending() {
		final Boolean result = paymentPremiumService.checkPaid();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("reject")
	@PreAuthorize("hasAnyAuthority('"+ RoleStaticConstant.ADM +"', '"+ RoleStaticConstant.MMB +"')")
	public ResponseEntity<Boolean> checkReject() {
		final Boolean result = paymentPremiumService.checkReject();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
