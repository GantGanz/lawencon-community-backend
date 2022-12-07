package com.lawencon.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.constant.RoleStaticConstant;
import com.lawencon.community.dto.role.RoleRes;
import com.lawencon.community.dto.role.RolesRes;
import com.lawencon.community.service.RoleService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("roles")
public class RoleController {
	@Autowired
	private RoleService roleService;

	@GetMapping
@PreAuthorize("hasAuthority('"+ RoleStaticConstant.SA +"')")
	public ResponseEntity<RolesRes> getAll() {
		final RolesRes res = roleService.getAll();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
@PreAuthorize("hasAuthority('"+ RoleStaticConstant.SA +"')")
	public ResponseEntity<RoleRes> getById(@PathVariable("id") final String id) {
		final RoleRes result = roleService.getById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
