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
import com.lawencon.community.dto.industry.IndustriesRes;
import com.lawencon.community.dto.industry.IndustryInsertReq;
import com.lawencon.community.dto.industry.IndustryRes;
import com.lawencon.community.dto.industry.IndustryUpdateReq;
import com.lawencon.community.service.IndustryService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("industries")
public class IndustryController {
	@Autowired
	private IndustryService industryService;

	@GetMapping
	public ResponseEntity<IndustriesRes> getAll(@RequestParam("offset") final Integer offset, @RequestParam("limit") final Integer limit) {
		final IndustriesRes res = industryService.getAll(offset, limit);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("active")
	public ResponseEntity<IndustriesRes> getAllActive() {
		final IndustriesRes res = industryService.getAllActive();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<InsertRes> insert(@RequestBody final IndustryInsertReq data) {
		final InsertRes res = industryService.insert(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PutMapping
	ResponseEntity<UpdateRes> update(@RequestBody final IndustryUpdateReq data) {
		final UpdateRes res = industryService.update(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<IndustryRes> getById(@PathVariable("id") final String id) {
		final IndustryRes result = industryService.getById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("count")
	public ResponseEntity<Long> countAll() {
		final Long res = industryService.countAll();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
