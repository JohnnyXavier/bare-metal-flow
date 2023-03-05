package com.bmc.flow.modules.database.entities.resourcing;

import com.bmc.flow.modules.database.entities.base.BaseCatalogEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "shrinkage")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class ShrinkageEntity extends BaseCatalogEntity {


  @Column(nullable = true)
  private Short durationInMin;

  @Column(nullable = true)
  private Short percentage;

  @ManyToMany(mappedBy = "shrinkages")
  private Set<ScheduleEntity> schedules;

}
