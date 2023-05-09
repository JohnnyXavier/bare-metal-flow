package com.bmc.flow.modules.database.entities.base;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public abstract class BaseCatalogEntity extends BaseEntity {

  @Column(unique = true)
  private String name;

  @Column(columnDefinition = "boolean default false")
  private Boolean isSystem;

  private String description;

}
