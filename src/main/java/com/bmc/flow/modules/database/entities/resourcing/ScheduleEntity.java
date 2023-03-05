package com.bmc.flow.modules.database.entities.resourcing;

import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.base.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "schedule")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class ScheduleEntity extends BaseEntity {

  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  private UserEntity user;

  private short hoursADay;

  @OneToMany(mappedBy = "schedule", cascade = ALL, fetch = FetchType.LAZY)
  private Set<ScheduleEntryEntity> scheduleEntries = new HashSet<>();

  @ManyToMany(cascade = {PERSIST, MERGE}, fetch = FetchType.LAZY)
  @JoinTable(name = "schedule_shrinkages", joinColumns = @JoinColumn(name = "schedule_id"), inverseJoinColumns = @JoinColumn(name = "shrinkage_id"))
  private Set<ShrinkageEntity> shrinkages = new HashSet<>();
}
