package com.rclm.blog.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rclm.blog.domain.dtos.AuthResponse;
import com.rclm.blog.domain.dtos.LoginRequest;
import com.rclm.blog.services.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping
  public ResponseEntity<AuthResponse>
  login(@RequestBody LoginRequest loginRequest) {
    UserDetails userDetails = authService.authenticate(
      loginRequest.getEmail(), 
      loginRequest.getPassword()
    );
    String token = authService.generateToken(userDetails);
    AuthResponse authResponse = AuthResponse.builder()
      .token(token)
      .expiresIn(86400)
      .build();
    return(ResponseEntity.ok(authResponse));
  }
}
