package com.lawencon.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.service.LikeService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("likes")
public class LikeController {
	@Autowired
	private LikeService likeService;

	@GetMapping("count-like/{id}")
	public ResponseEntity<Long> countLike(@PathVariable("id") final String postId) {
		final Long res = likeService.countLike(postId);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
