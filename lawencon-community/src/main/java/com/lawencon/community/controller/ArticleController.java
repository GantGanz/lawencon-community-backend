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
import com.lawencon.community.dto.article.ArticleInsertReq;
import com.lawencon.community.dto.article.ArticleRes;
import com.lawencon.community.dto.article.ArticleUpdateReq;
import com.lawencon.community.dto.article.ArticlesRes;
import com.lawencon.community.service.ArticleService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("articles")
public class ArticleController {
	@Autowired
	private ArticleService articleService;

	@GetMapping
	public ResponseEntity<ArticlesRes> getAll(@RequestParam("offset") final Integer offset, @RequestParam("limit") final Integer limit) {
		final ArticlesRes res = articleService.getAll(offset, limit);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("user")
	public ResponseEntity<ArticlesRes> getAllById(@RequestParam("offset") final Integer offset, @RequestParam("limit") final Integer limit) {
		final ArticlesRes res = articleService.getAllById(offset, limit);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("count")
	public ResponseEntity<Long> countAllArticle() {
		final Long res = articleService.countAllArticle();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<InsertRes> insert(@RequestBody final ArticleInsertReq data) {
		final InsertRes res = articleService.insert(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<UpdateRes> update(@RequestBody final ArticleUpdateReq data) {
		final UpdateRes res = articleService.update(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<ArticleRes> getById(@PathVariable("id") final String id) {
		final ArticleRes result = articleService.getById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
