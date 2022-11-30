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
import org.springframework.web.bind.annotation.RequestParam;
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
	public ResponseEntity<ActivitiesRes> getAllEvent(@RequestParam("offset") final Integer offset, @RequestParam("limit") final Integer limit) {
		final ActivitiesRes res = activityService.getAllEvent(offset, limit);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("course")
	public ResponseEntity<ActivitiesRes> getAllCourse(@RequestParam("offset") final Integer offset, @RequestParam("limit") final Integer limit) {
		final ActivitiesRes res = activityService.getAllCourse(offset, limit);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("my-event")
	public ResponseEntity<ActivitiesRes> getAllEventById(@RequestParam("offset") final Integer offset, @RequestParam("limit") final Integer limit) {
		final ActivitiesRes res = activityService.getAllEventById(offset, limit);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("my-course")
	public ResponseEntity<ActivitiesRes> getAllCourseById(@RequestParam("offset") final Integer offset, @RequestParam("limit") final Integer limit) {
		final ActivitiesRes res = activityService.getAllCourseById(offset, limit);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("joined-event")
	public ResponseEntity<ActivitiesRes> getAllJoinedEventById(@RequestParam("offset") final Integer offset, @RequestParam("limit") final Integer limit) {
		final ActivitiesRes res = activityService.getAllJoinedEventById(offset, limit);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("joined-course")
	public ResponseEntity<ActivitiesRes> getAllJoinedCourseById(@RequestParam("offset") final Integer offset, @RequestParam("limit") final Integer limit) {
		final ActivitiesRes res = activityService.getAllJoinedCourseById(offset, limit);
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
	
	@GetMapping("count-my-course")
	public ResponseEntity<Long> countMyCourse() {
		final Long res = activityService.countMyCourse();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("count-my-event")
	public ResponseEntity<Long> countMyEvent() {
		final Long res = activityService.countMyEvent();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
