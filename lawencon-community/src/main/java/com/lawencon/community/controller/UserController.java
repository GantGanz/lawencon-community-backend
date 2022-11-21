package com.lawencon.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.dto.InsertRes;
import com.lawencon.community.dto.UpdateRes;
import com.lawencon.community.dto.user.UserChangePasswordReq;
import com.lawencon.community.dto.user.UserInsertReq;
import com.lawencon.community.dto.user.UserUpdateReq;
import com.lawencon.community.dto.user.UsersRes;
import com.lawencon.community.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("users")
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<UsersRes> getAll() {
		final UsersRes res = userService.getAll();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PostMapping("register")
	public ResponseEntity<InsertRes> register(@RequestBody final UserInsertReq data) {
		final InsertRes res = userService.register(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<InsertRes> registerAdmin(@RequestBody final UserInsertReq data) {
		final InsertRes res = userService.registerAdmin(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<UpdateRes> update(@RequestBody final UserUpdateReq data) {
		final UpdateRes res = userService.update(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PutMapping("change-password")
	public ResponseEntity<UpdateRes> changePassword(@RequestBody final UserChangePasswordReq data) {
		final UpdateRes res = userService.changePassword(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
