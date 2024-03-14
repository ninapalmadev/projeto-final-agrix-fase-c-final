package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.CropDto;
import com.betrybe.agrix.controllers.exceptions.NotFoundException;
import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Fertilizer;
import com.betrybe.agrix.service.CropService;
import com.betrybe.agrix.service.FertilizerService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Crop Controller.
 */
@RestController
@RequestMapping(value = "/crops")
public class CropController {
  private final CropService cropService;
  private final FertilizerService fertilizerService;

  @Autowired
  public CropController(CropService cropService, FertilizerService fertilizerService) {
    this.cropService = cropService;
    this.fertilizerService = fertilizerService;
  }
  /**
   * GET crop METHOD.
   *
   * @return all crops
   */

  @GetMapping
  @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
  public List<CropDto> getAllCrops() {
    List<Crop> crops = cropService.getAllCrops();
    return crops.stream()
        .map(crop -> new CropDto(
            crop.getId(),
            crop.getName(),
            crop.getPlantedArea(),
            crop.getPlantedDate(),
            crop.getHarvestDate(),
            crop.getFarm().getId()))
        .collect(Collectors.toList());
  }

  /**
   * Get cropId METHOD.
   *
   * @param cropId crop id
   * @return all crop by id
   */
  @GetMapping(value = "/{cropId}")
  public ResponseEntity<CropDto> getCropById(@PathVariable Long cropId) {
    Optional<Crop> optionalCrop = cropService.getCropById(cropId);
    if (optionalCrop.isEmpty()) {
      throw new NotFoundException("Plantação não encontrada!");
    }
    Crop crop = optionalCrop.get();
    return ResponseEntity.status(HttpStatus.OK).body(new CropDto(
        crop.getId(),
        crop.getName(),
        crop.getPlantedArea(),
        crop.getPlantedDate(),
        crop.getHarvestDate(),
        crop.getFarm().getId()));
  }

  /**
   * POST fertilizer id METHOD.
   *
   * @param cropId crop id
   * @param fertilizerId fertilizer id
   * @return crop and fertilizer
   */
  @PostMapping(value = "/{cropId}/fertilizers/{fertilizerId}")
  public ResponseEntity<String> searchCropAndFertilizer(@PathVariable Long cropId,
      @PathVariable Long fertilizerId) {
    Optional<Crop> optionalCrop = cropService.getCropById(cropId);
    if (optionalCrop.isEmpty()) {
      throw new NotFoundException("Plantação não encontrada!");
    }

    Optional<Fertilizer> optionalFertilizer = fertilizerService.getFertilizerById(fertilizerId);
    if (optionalFertilizer.isEmpty()) {
      throw new NotFoundException("Fertilizante não encontrado!");
    }
    Crop crop = optionalCrop.get();
    Fertilizer fertilizer = optionalFertilizer.get();
    cropService.searchCropAndFertilizer(crop.getId(), fertilizer.getId());
    return ResponseEntity.status(HttpStatus.CREATED).body(
        "Fertilizante e plantação associados com sucesso!");
  }

  /**
   * GET fertilizers METHOD.
   *
   * @param cropId crop id
   * @return fertilizers by crop
   */
  @GetMapping(value = "{cropId}/fertilizers")
  public List<Fertilizer> getFertilizers(@PathVariable Long cropId) {
    Optional<Crop> optionalCrop = cropService.getCropById(cropId);
    if (optionalCrop.isEmpty()) {
      throw new NotFoundException("Plantação não encontrada!");
    }
    Crop crop = optionalCrop.get();
    return crop.getFertilizers();
  }
}