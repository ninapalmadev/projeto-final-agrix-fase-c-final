package com.betrybe.agrix.controllers.dto;

import com.betrybe.agrix.models.entities.Crop;
import java.time.LocalDate;

/**
 * Crop Dto.
 */
public record CropDto(
    Long id,
    String name,
    Double plantedArea,
    LocalDate plantedDate,
    LocalDate harvestDate,
    Long farmId) {
  public Crop toCrop() {
    return new Crop(id, name, plantedArea, plantedDate, harvestDate, null, null);
  }
}