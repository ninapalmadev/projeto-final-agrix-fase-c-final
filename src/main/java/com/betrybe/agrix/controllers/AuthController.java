package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.LoginRequestDto;
import com.betrybe.agrix.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Auth Controller.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
  private final AuthenticationManager authenticationManager;
  private final TokenService tokenService;

  @Autowired
  public AuthController(AuthenticationManager authenticationManager,
      TokenService tokenService) {
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
  }

  /** POST method. */
  @PostMapping("/login")
  public ResponseEntity<TokenResponse> login(@RequestBody LoginRequestDto loginRequestDto) {
    UsernamePasswordAuthenticationToken usernamePassword =
        new UsernamePasswordAuthenticationToken(
            loginRequestDto.username(),
            loginRequestDto.password());
    Authentication auth = authenticationManager.authenticate(usernamePassword);
    User person = (User) auth.getPrincipal();
    TokenResponse response = new TokenResponse(tokenService.generateToken(person));
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  private record TokenResponse(String token) {}
}
