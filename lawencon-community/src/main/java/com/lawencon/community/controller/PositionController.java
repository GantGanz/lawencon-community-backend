package com.lawencon.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.dto.position.PositionsRes;
import com.lawencon.community.service.PositionService;

@RestController
@RequestMapping("positions")
public class PositionController {
	@Autowired
	private PositionService positionService;

	@GetMapping
	public ResponseEntity<PositionsRes> getAll() {
		final PositionsRes res = positionService.getAll();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
