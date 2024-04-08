package com.betrybe.agrix.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

/**
 * The Class Farm.
 */

@Entity
@Table(name = "farms")
public class Farm {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private Double size;

  @OneToMany(mappedBy = "farm")
  @JsonIgnore
  private List<Crop> crops;

  /**
   * Get farm.
   */

  public Farm() {}

  /**
   * Get farm.
   *
   * @param id from farm id
   * @param name from farm name
   * @param size from farm size
   */

  public Farm(Long id, String name, Double size, List<Crop> crops) {
    this.id = id;
    this.name = name;
    this.size = size;
    this.crops = crops;
  }

  /**
   * Gets id.
   *
   * @return the id
   */

  public Long getId() {
    return id;
  }

  /**
   * Sets id.
   *
   * @param id the id
   */

  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets size.
   *
   * @return the size
   */

  public Double getSize() {
    return size;
  }

  /**
   * Sets size.
   *
   * @param size the size
   */

  public void setSize(Double size) {
    this.size = size;
  }

  /**
   * Gets name.
   *
   * @return the name
   */

  public String getName() {
    return name;
  }

  /**
   * Sets name.
   *
   * @param name the name
   */

  public void setName(String name) {
    this.name = name;
  }

  public List<Crop> getCrops() {
    return crops;
  }

  public void setCrops(List<Crop> crops) {
    this.crops = crops;
  }

}