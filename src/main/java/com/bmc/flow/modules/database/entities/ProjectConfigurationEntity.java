package com.bmc.flow.modules.database.entities;

import com.bmc.flow.modules.database.entities.base.BaseEntity;
import com.bmc.flow.modules.database.entities.records.ProjectEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "project_configuration")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class ProjectConfigurationEntity extends BaseEntity {

  private Integer hoursADay;

  private UUID cardDifficultyDefault;

  private UUID cardStatusDefault;

  private UUID cardTypeDefault;

  @OneToOne
  private ProjectEntity project;

}
