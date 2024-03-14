package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.FertilizerDto;
import com.betrybe.agrix.controllers.exceptions.NotFoundException;
import com.betrybe.agrix.models.entities.Fertilizer;
import com.betrybe.agrix.service.FertilizerService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The class Fertilizer Controller.
 */
@RestController
@RequestMapping(value = "/fertilizers")
public class FertilizerController {
  private final FertilizerService fertilizerService;


  public FertilizerController(FertilizerService fertilizerService) {
    this.fertilizerService = fertilizerService;
  }

  /**
   * POST fertilizer METHOD.
   *
   * @param fertilizerDto fertilizer dto
   * @return fertilizer dto created
   */
  @PostMapping
  public ResponseEntity<Fertilizer> createFertilizer(@RequestBody FertilizerDto fertilizerDto) {
    Fertilizer newFertilizer = fertilizerService.createFertilizer(fertilizerDto.toFertilizer());

    return ResponseEntity.status(HttpStatus.CREATED).body(newFertilizer);
  }

  /**
   * GET fertilizers METHOD.
   *
   * @return all fertilizers
   */
  @GetMapping
  @Secured("ROLE_ADMIN")
  public List<FertilizerDto> getAllFertilizers() {
    List<Fertilizer> allFertilizers = fertilizerService.getAll();

    return allFertilizers.stream()
        .map(fertilizer -> new FertilizerDto(
            fertilizer.getId(),
            fertilizer.getName(),
            fertilizer.getBrand(),
            fertilizer.getComposition()))
        .collect(Collectors.toList());
  }

  /**
   * GET fertililzerId METHOD.
   *
   * @param fertilizerId fertilizer id
   * @return individual fertilizer
   */
  @GetMapping("/{fertilizerId}")
  public ResponseEntity<Fertilizer> getFertilizerById(@PathVariable Long fertilizerId) {
    Optional<Fertilizer> optionalFertilizer = fertilizerService.getFertilizerById(fertilizerId);

    if (optionalFertilizer.isEmpty()) {
      throw new NotFoundException("Fertilizante não encontrado!");
    }
    return ResponseEntity.status(HttpStatus.OK).body(optionalFertilizer.get());
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<String> handleNotFoundException(NotFoundException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
  }
}