package com.lawencon.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.dto.InsertRes;
import com.lawencon.community.dto.pollvote.PollVoteInsertReq;
import com.lawencon.community.service.PollVoteService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("poll-votes")
public class PollVoteController {
	@Autowired
	private PollVoteService pollVoteService;

	@GetMapping("count-all-vote/{id}")
	public ResponseEntity<Long> countPollVote(@PathVariable("id") final String pollId) {
		final Long res = pollVoteService.countVote(pollId);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("count-vote/{id}")
	public ResponseEntity<Long> countPollOptionVote(@PathVariable("id") final String pollOptionId) {
		final Long res = pollVoteService.countVoteByPollOption(pollOptionId);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<InsertRes> insert(@RequestBody final PollVoteInsertReq data) {
		final InsertRes res = pollVoteService.insert(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("voted/{id}")
	public ResponseEntity<Boolean> isPollVoted(@PathVariable("id") final String pollId) {
		final Boolean res = pollVoteService.isVoted(pollId);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
