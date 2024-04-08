package com.betrybe.agrix.controllers.dto;

import com.betrybe.agrix.models.entities.Person;
import com.betrybe.agrix.models.security.Role;

/**
 * Create Person Dto.
 */
public record CreatePersonDto(Long id, String username, String password, Role role) {
  public Person toCreatePerson() {
    return new Person(id, username, password, role);
  }
}
