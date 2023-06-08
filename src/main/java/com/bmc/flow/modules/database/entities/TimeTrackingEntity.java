package com.bmc.flow.modules.database.entities;

import com.bmc.flow.modules.database.entities.base.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.Duration;

@Entity
@Table(name = "time_tracking")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class TimeTrackingEntity extends BaseEntity {

  private Duration months;

  private Duration days;

  private Duration hours;

  private Duration minutes;

}
