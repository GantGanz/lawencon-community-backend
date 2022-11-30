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
import com.lawencon.community.dto.post.PostInsertReq;
import com.lawencon.community.dto.post.PostRes;
import com.lawencon.community.dto.post.PostUpdateReq;
import com.lawencon.community.dto.post.PostsRes;
import com.lawencon.community.service.PostService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("posts")
public class PostController {
	@Autowired
	private PostService postService;

	@GetMapping
	public ResponseEntity<PostsRes> getAll(@RequestParam("offset") final Integer offset, @RequestParam("limit") final Integer limit) {
		final PostsRes res = postService.getAll(offset, limit);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("regular")
	public ResponseEntity<PostsRes> getAllRegular() {
		final PostsRes res = postService.getAllRegular();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("polling")
	public ResponseEntity<PostsRes> getAllPolling() {
		final PostsRes res = postService.getAllPolling();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("premium")
	public ResponseEntity<PostsRes> getAllPremium() {
		final PostsRes res = postService.getAllRegular();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("user")
	public ResponseEntity<PostsRes> getAllById(@RequestParam("offset") final Integer offset, @RequestParam("limit") final Integer limit) {
		final PostsRes res = postService.getAllById(offset, limit);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("regular/user")
	public ResponseEntity<PostsRes> getAllRegularById() {
		final PostsRes res = postService.getAllRegularById();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("polling/user")
	public ResponseEntity<PostsRes> getAllPollingById() {
		final PostsRes res = postService.getAllPollingById();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("premium/user")
	public ResponseEntity<PostsRes> getAllPremiumById() {
		final PostsRes res = postService.getAllRegularById();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("liked")
	public ResponseEntity<PostsRes> getAllLiked(@RequestParam("offset") final Integer offset, @RequestParam("limit") final Integer limit) {
		final PostsRes res = postService.getAllLiked(offset, limit);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("bookmarked")
	public ResponseEntity<PostsRes> getAllBookmarked(@RequestParam("offset") final Integer offset, @RequestParam("limit") final Integer limit) {
		final PostsRes res = postService.getAllBookmarked(offset, limit);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<InsertRes> insert(@RequestBody final PostInsertReq data) {
		final InsertRes res = postService.insert(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<UpdateRes> update(@RequestBody final PostUpdateReq data) {
		final UpdateRes res = postService.update(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<PostRes> getById(@PathVariable("id") final String id) {
		final PostRes result = postService.getById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("count-all")
	public ResponseEntity<Long> countAll() {
		final Long res = postService.countAll();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("count-my-posts")
	public ResponseEntity<Long> countMyPost() {
		final Long res = postService.countMyPost();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("count-liked")
	public ResponseEntity<Long> countLiked() {
		final Long res = postService.countLiked();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("count-bookmarked")
	public ResponseEntity<Long> countBookmarked() {
		final Long res = postService.countBookmarked();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
