package com.betrybe.agrix.controllers.dto;

import com.betrybe.agrix.models.entities.Fertilizer;

/**
 * Fertilizer Dto.
 */
public record FertilizerDto(Long id, String name, String brand, String composition) {
  public Fertilizer toFertilizer() {
    return new Fertilizer(id, name, brand, composition, null);
  }
}