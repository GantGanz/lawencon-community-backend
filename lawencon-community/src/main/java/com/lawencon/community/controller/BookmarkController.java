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
import com.lawencon.community.dto.bookmark.BookmarkInsertReq;
import com.lawencon.community.dto.bookmark.BookmarkRes;
import com.lawencon.community.dto.bookmark.BookmarkUpdateReq;
import com.lawencon.community.dto.bookmark.BookmarksRes;
import com.lawencon.community.service.BookmarkService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("bookmarks")
public class BookmarkController {
	@Autowired
	private BookmarkService bookmarkService;

	@GetMapping
	public ResponseEntity<BookmarksRes> getAllByUserId() {
		final BookmarksRes res = bookmarkService.getAllByUserId();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<InsertRes> insert(@RequestBody final BookmarkInsertReq data) {
		final InsertRes res = bookmarkService.insert(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PutMapping("soft-delete")
	public ResponseEntity<UpdateRes> softDelete(@RequestBody final BookmarkUpdateReq data) {
		final UpdateRes res = bookmarkService.softDelete(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<BookmarkRes> getById(@PathVariable("id") final String id) {
		final BookmarkRes result = bookmarkService.getById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("bookmarked/{id}")
	public ResponseEntity<Boolean> isBookmarked(@PathVariable("id") final String postId) {
		final Boolean res = bookmarkService.isBookmarked(postId);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
