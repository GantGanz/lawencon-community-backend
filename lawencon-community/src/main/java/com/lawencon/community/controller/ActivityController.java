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
import com.lawencon.community.dto.activity.ActivitiesRes;
import com.lawencon.community.dto.activity.ActivityInsertReq;
import com.lawencon.community.dto.activity.ActivityRes;
import com.lawencon.community.dto.activity.ActivityUpdateReq;
import com.lawencon.community.service.ActivityService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("activities")
public class ActivityController {
	@Autowired
	private ActivityService activityService;

	@GetMapping("event")
	public ResponseEntity<ActivitiesRes> getAllEvent() {
		final ActivitiesRes res = activityService.getAllEvent();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("course")
	public ResponseEntity<ActivitiesRes> getAllCourse() {
		final ActivitiesRes res = activityService.getAllCourse();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("my-event")
	public ResponseEntity<ActivitiesRes> getAllEventById() {
		final ActivitiesRes res = activityService.getAllEventById();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("my-course")
	public ResponseEntity<ActivitiesRes> getAllCourseById() {
		final ActivitiesRes res = activityService.getAllCourseById();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("joined/event")
	public ResponseEntity<ActivitiesRes> getAllJoinedEventById() {
		final ActivitiesRes res = activityService.getAllJoinedEventById();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("joined/course")
	public ResponseEntity<ActivitiesRes> getAllJoinedCourseById() {
		final ActivitiesRes res = activityService.getAllJoinedCourseById();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<InsertRes> insert(@RequestBody final ActivityInsertReq data) {
		final InsertRes res = activityService.insert(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<UpdateRes> update(@RequestBody final ActivityUpdateReq data) {
		final UpdateRes res = activityService.update(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<ActivityRes> getById(@PathVariable("id") final String id) {
		final ActivityRes result = activityService.getById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
