package com.lawencon.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.dto.InsertRes;
import com.lawencon.community.dto.userverification.UserVerificationData;
import com.lawencon.community.service.UserVerificationService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("user-verifications")
public class UserVerificationController {
	@Autowired
	private UserVerificationService userVerificationService;

	@PostMapping("send-code")
	public ResponseEntity<InsertRes> sendCode(@RequestBody final String email) {
		final InsertRes res = userVerificationService.sendCode(email);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PostMapping("validate")
	public ResponseEntity<InsertRes> validate(@RequestBody final UserVerificationData data) {
		final InsertRes res = userVerificationService.validate(data.getEmail(), data.getVerificationCode());
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
