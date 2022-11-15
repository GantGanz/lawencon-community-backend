package com.lawencon.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.dto.role.RolesRes;
import com.lawencon.community.service.RoleService;

@RestController
@RequestMapping("roles")
public class RoleController {
	@Autowired
	private RoleService roleService;

	@GetMapping
	public ResponseEntity<RolesRes> getAll() {
		final RolesRes res = roleService.getAll();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
