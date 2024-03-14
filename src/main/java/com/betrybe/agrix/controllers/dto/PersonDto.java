package com.betrybe.agrix.controllers.dto;

import com.betrybe.agrix.models.security.Role;

/** Create person DTO record. */
public record PersonDto(Long id, String username, Role role) {
  public PersonDto toPersonDto() {
    return new PersonDto(id, username, role);
  }
}