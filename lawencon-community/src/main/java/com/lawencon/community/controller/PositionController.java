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
import com.lawencon.community.dto.position.PositionInsertReq;
import com.lawencon.community.dto.position.PositionRes;
import com.lawencon.community.dto.position.PositionUpdateReq;
import com.lawencon.community.dto.position.PositionsRes;
import com.lawencon.community.service.PositionService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("positions")
public class PositionController {
	@Autowired
	private PositionService positionService;

	@GetMapping
	public ResponseEntity<PositionsRes> getAll(@RequestParam("offset") final Integer offset, @RequestParam("limit") final Integer limit) {
		final PositionsRes res = positionService.getAll(offset, limit);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<InsertRes> insert(@RequestBody final PositionInsertReq data) {
		final InsertRes res = positionService.insert(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<UpdateRes> update(@RequestBody final PositionUpdateReq data) {
		final UpdateRes res = positionService.update(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<PositionRes> getById(@PathVariable("id") final String id) {
		final PositionRes result = positionService.getById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
