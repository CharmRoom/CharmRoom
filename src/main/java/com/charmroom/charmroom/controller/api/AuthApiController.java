package com.charmroom.charmroom.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.charmroom.charmroom.dto.CommonResponseDto;
import com.charmroom.charmroom.dto.SignupDto.SignupRequestDto;
import com.charmroom.charmroom.dto.SignupDto.SignupResponseDto;
import com.charmroom.charmroom.entity.User;
import com.charmroom.charmroom.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApiController {
	private final UserService userService;
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(
			@RequestBody @Valid SignupRequestDto signupRequestDto) {
		User created = userService.create(signupRequestDto.getUsername(), signupRequestDto.getEmail(),
				signupRequestDto.getNickname(), signupRequestDto.getPassword());
		return ResponseEntity.ok(CommonResponseDto.okay(SignupResponseDto.fromEntity(created)));
	}
}