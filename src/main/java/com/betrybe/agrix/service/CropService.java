package com.betrybe.agrix.service;

import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Fertilizer;
import com.betrybe.agrix.models.repositories.CropRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * The Crop Service.
 */
@Service
public class CropService {
  private final CropRepository cropRepository;
  private final FertilizerService fertilizerService;

  public CropService(CropRepository cropRepository, FertilizerService fertilizerService) {
    this.cropRepository = cropRepository;
    this.fertilizerService = fertilizerService;
  }

  public List<Crop> getAllCrops() {
    return cropRepository.findAll();
  }

  public Optional<Crop> getCropById(Long cropId) {
    return cropRepository.findById(cropId);
  }

  /**
   * GET search Crop And Fertilizer.
   *
   * @param cropId crop id
   * @param fertilizerId fertilizer id
   */
  public void searchCropAndFertilizer(Long cropId, Long fertilizerId) {
    Optional<Crop> optionalCrop = getCropById(cropId);
    if (optionalCrop.isEmpty()) {
      return;
    }
    Optional<Fertilizer> optionalFertilizer = fertilizerService.getFertilizerById(fertilizerId);
    if (optionalFertilizer.isEmpty()) {
      return;
    }
    Crop crop = optionalCrop.get();
    Fertilizer fertilizer = optionalFertilizer.get();
    crop.getFertilizers().add(fertilizer);
    cropRepository.save(crop);
  }
}

