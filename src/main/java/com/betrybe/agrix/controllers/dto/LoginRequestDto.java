package com.betrybe.agrix.controllers.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/** Auth Dto. */
public record LoginRequestDto(String username, String password) {
  public UsernamePasswordAuthenticationToken toLogin() {
    return new UsernamePasswordAuthenticationToken(username, password);
  }
}
