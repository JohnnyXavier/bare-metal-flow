package com.bmc.flow.modules.database.entities;

import com.bmc.flow.modules.database.entities.base.BaseEntity;
import com.bmc.flow.modules.database.entities.records.ProjectEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
