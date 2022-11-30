package com.lawencon.community.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.dto.login.LoginReqDto;
import com.lawencon.community.dto.login.LoginResDto;
import com.lawencon.community.model.User;
import com.lawencon.community.service.UserService;
import com.lawencon.util.JwtUtil;
import com.lawencon.util.JwtUtil.ClaimKey;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("login")
public class LoginController {
	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping
	public ResponseEntity<LoginResDto> login(@RequestBody final LoginReqDto data) {
		final Authentication auth = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());
		authenticationManager.authenticate(auth);
		final User user = userService.getByEmail(data.getEmail()).get();

		final Map<String, Object> claims = new HashMap<>();
		claims.put(ClaimKey.ID.name(), user.getId());
		claims.put(ClaimKey.ROLE.name(), user.getRole().getRoleCode());

		final LoginResDto res = new LoginResDto();
		res.setId(user.getId());
		res.setEmail(user.getEmail());
		res.setFullname(user.getFullname());
		res.setRoleCode(user.getRole().getRoleCode());
		res.setFileId(user.getFile().getId());
		res.setToken(jwtUtil.generateToken(claims));
		return new ResponseEntity<LoginResDto>(res, HttpStatus.OK);
	}
}