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
import com.lawencon.community.dto.comment.CommentInsertReq;
import com.lawencon.community.dto.comment.CommentRes;
import com.lawencon.community.dto.comment.CommentUpdateReq;
import com.lawencon.community.dto.comment.CommentsRes;
import com.lawencon.community.service.CommentService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("comments")
public class CommentController {
	@Autowired
	private CommentService commentService;

	@GetMapping("post/{id}")
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.MMB +"')")
	public ResponseEntity<CommentsRes> getAllByPostId(@PathVariable("id") final String id,
			@RequestParam("offset") final Integer offset, @RequestParam("limit") final Integer limit) {
		final CommentsRes res = commentService.getAllByPostId(id, offset, limit);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("comment/{id}")
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.MMB +"')")
	public ResponseEntity<CommentsRes> getAllByCommentId(@PathVariable("id") final String id) {
		final CommentsRes res = commentService.getAllByCommentId(id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.MMB +"')")
	public ResponseEntity<InsertRes> insert(@RequestBody final CommentInsertReq data) {
		final InsertRes res = commentService.insert(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PutMapping
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.MMB +"')")
	public ResponseEntity<UpdateRes> update(@RequestBody final CommentUpdateReq data) {
		final UpdateRes res = commentService.update(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PutMapping("soft-delete")
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.MMB +"')")
	public ResponseEntity<UpdateRes> softDelete(@RequestBody final CommentUpdateReq data) {
		final UpdateRes res = commentService.softDelete(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.MMB +"')")
	public ResponseEntity<CommentRes> getById(@PathVariable("id") final String id) {
		final CommentRes result = commentService.getById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("count-comment/{id}")
	@PreAuthorize("hasAuthority('"+ RoleStaticConstant.MMB +"')")
	public ResponseEntity<Long> countComment(@PathVariable("id") final String postId) {
		final Long res = commentService.countComment(postId);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
