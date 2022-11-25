package com.lawencon.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.dto.posttype.PostTypeRes;
import com.lawencon.community.dto.posttype.PostTypesRes;
import com.lawencon.community.service.PostTypeService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("post-types")
public class PostTypeController {
	@Autowired
	private PostTypeService postTypeService;

	@GetMapping
	public ResponseEntity<PostTypesRes> getAll() {
		final PostTypesRes res = postTypeService.getAll();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<PostTypeRes> getById(@PathVariable("id") final String id) {
		final PostTypeRes result = postTypeService.getById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("code/{code}")
	public ResponseEntity<String> getIdByCode(@PathVariable("code") final String code) {
		final String result = postTypeService.getPostTypeIdByCode(code);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
