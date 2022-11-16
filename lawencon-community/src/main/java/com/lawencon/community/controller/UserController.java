package com.lawencon.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.dto.InsertRes;
import com.lawencon.community.dto.user.UserInsertReq;
import com.lawencon.community.dto.user.UsersRes;
import com.lawencon.community.service.UserService;

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
}
