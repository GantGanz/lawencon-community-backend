package com.lawencon.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.dto.InsertRes;
import com.lawencon.community.dto.article.ArticleInsertReq;
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
	public ResponseEntity<ArticlesRes> getAll() {
		final ArticlesRes res = articleService.getAll();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<InsertRes> insert(@RequestBody final ArticleInsertReq data) {
		final InsertRes res = articleService.insert(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
