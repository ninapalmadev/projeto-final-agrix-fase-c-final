package com.betrybe.agrix.service;

import com.betrybe.agrix.exceptions.PersonNotFoundException;
import com.betrybe.agrix.models.entities.Person;
import com.betrybe.agrix.models.repositories.PersonRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service layer class for handling persons business logic.
 */
@Service
public class PersonService implements UserDetailsService {

  private final PersonRepository personRepository;

  @Autowired
  public PersonService(
      PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  /**
   * Returns a person for a given ID.
   */
  public Person getPersonById(Long id) {
    Optional<Person> person = personRepository.findById(id);

    if (person.isEmpty()) {
      throw new PersonNotFoundException();
    }

    return person.get();
  }

  /**
   * Returns a person for a given username.
   */
  public Person getPersonByUsername(String username) {
    Optional<Person> person = personRepository.findByUsername(username);

    if (person.isEmpty()) {
      throw new PersonNotFoundException();
    }

    return person.get();
  }

  /**
   * Creates a new person.
   */
  public Person create(Person person) {
    String hashedPassword = new BCryptPasswordEncoder().encode(person.getPassword());
    person.setPassword(hashedPassword);
    return personRepository.save(person);
  }

  @Override
  public UserDetails loadUserByUsername(String username)
      throws PersonNotFoundException {
    Optional<Person> personOptional = personRepository.findByUsername(username);
    Person person = personOptional
        .orElseThrow(PersonNotFoundException::new);
    List<GrantedAuthority> authorities = person.getAuthorities()
        .stream()
        .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
        .collect(Collectors.toList());
    return new User(person.getUsername(), person.getPassword(), authorities);
  }
}
