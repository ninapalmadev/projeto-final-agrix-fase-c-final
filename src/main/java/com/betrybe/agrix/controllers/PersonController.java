package com.betrybe.agrix.controllers;


import com.betrybe.agrix.controllers.dto.CreatePersonDto;
import com.betrybe.agrix.controllers.dto.PersonDto;
import com.betrybe.agrix.models.entities.Person;
import com.betrybe.agrix.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Person Controller.
 */

@RestController
@RequestMapping(value = "/persons")
public class PersonController {
  private final PersonService personService;

  public PersonController(PersonService personService) {
    this.personService = personService;
  }

  /** POST method. */
  @PostMapping
  public ResponseEntity<PersonDto> createPerson(@RequestBody CreatePersonDto createPersonDto) {
    Person person = personService.create(createPersonDto.toCreatePerson());
    PersonDto createdPerson = new PersonDto(person.getId(),
        person.getUsername(), person.getRole());
    return ResponseEntity.status(HttpStatus.CREATED).body(createdPerson);
  }
}
