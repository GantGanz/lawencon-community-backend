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
import com.lawencon.community.dto.activitytype.ActivityTypeRes;
import com.lawencon.community.dto.activitytype.ActivityTypesRes;
import com.lawencon.community.service.ActivityTypeService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("activity-types")
public class ActivityTypeController {
	@Autowired
	private ActivityTypeService activityTypeService;

	@GetMapping
	@PreAuthorize("hasAuthority('" + RoleStaticConstant.MMB + "')")
	public ResponseEntity<ActivityTypesRes> getAll() {
		final ActivityTypesRes res = activityTypeService.getAll();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('" + RoleStaticConstant.MMB + "')")
	public ResponseEntity<ActivityTypeRes> getById(@PathVariable("id") final String id) {
		final ActivityTypeRes result = activityTypeService.getById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
