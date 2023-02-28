package com.bmc.flow.modules.database.entities.base;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public abstract class BaseRecordEntity extends BaseEntity {

  private String name;

  private String description;

  private String coverImage;

}
