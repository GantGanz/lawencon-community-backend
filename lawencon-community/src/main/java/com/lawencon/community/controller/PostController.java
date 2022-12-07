package com.lawencon.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.constant.RoleStaticConstant;
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
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.MMB +"')")
	public ResponseEntity<PostsRes> getAll(@RequestParam("offset") final Integer offset, @RequestParam("limit") final Integer limit) {
		final PostsRes res = postService.getAll(offset, limit);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("regular")
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.MMB +"')")
	public ResponseEntity<PostsRes> getAllRegular() {
		final PostsRes res = postService.getAllRegular();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("polling")
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.MMB +"')")
	public ResponseEntity<PostsRes> getAllPolling() {
		final PostsRes res = postService.getAllPolling();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("premium")
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.MMB +"')")
	public ResponseEntity<PostsRes> getAllPremium() {
		final PostsRes res = postService.getAllRegular();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("user")
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.MMB +"')")
	public ResponseEntity<PostsRes> getAllById(@RequestParam("offset") final Integer offset, @RequestParam("limit") final Integer limit) {
		final PostsRes res = postService.getAllById(offset, limit);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("regular/user")
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.MMB +"')")
	public ResponseEntity<PostsRes> getAllRegularById() {
		final PostsRes res = postService.getAllRegularById();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("polling/user")
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.MMB +"')")
	public ResponseEntity<PostsRes> getAllPollingById() {
		final PostsRes res = postService.getAllPollingById();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("premium/user")
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.MMB +"')")
	public ResponseEntity<PostsRes> getAllPremiumById() {
		final PostsRes res = postService.getAllRegularById();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("liked")
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.MMB +"')")
	public ResponseEntity<PostsRes> getAllLiked(@RequestParam("offset") final Integer offset, @RequestParam("limit") final Integer limit) {
		final PostsRes res = postService.getAllLiked(offset, limit);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("bookmarked")
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.MMB +"')")
	public ResponseEntity<PostsRes> getAllBookmarked(@RequestParam("offset") final Integer offset, @RequestParam("limit") final Integer limit) {
		final PostsRes res = postService.getAllBookmarked(offset, limit);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.MMB +"')")
	public ResponseEntity<InsertRes> insert(@RequestBody final PostInsertReq data) {
		final InsertRes res = postService.insert(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PutMapping
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.MMB +"')")
	public ResponseEntity<UpdateRes> update(@RequestBody final PostUpdateReq data) {
		final UpdateRes res = postService.update(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.MMB +"')")
	public ResponseEntity<PostRes> getById(@PathVariable("id") final String id) {
		final PostRes result = postService.getById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("count-all")
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.MMB +"')")
	public ResponseEntity<Long> countAll() {
		final Long res = postService.countAll();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("count-my-posts")
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.MMB +"')")
	public ResponseEntity<Long> countMyPost() {
		final Long res = postService.countMyPost();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("count-liked")
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.MMB +"')")
	public ResponseEntity<Long> countLiked() {
		final Long res = postService.countLiked();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("count-bookmarked")
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.MMB +"')")
	public ResponseEntity<Long> countBookmarked() {
		final Long res = postService.countBookmarked();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
