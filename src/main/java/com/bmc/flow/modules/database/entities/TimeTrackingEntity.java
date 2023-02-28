package com.bmc.flow.modules.database.entities;

import com.bmc.flow.modules.database.entities.base.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Duration;

@Entity
@Table(name = "time_tracking")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class TimeTrackingEntity extends BaseEntity {

  public static final String FIELD_NAME = "time-tracking";

  private Duration months;

  private Duration days;

  private Duration hours;

  private Duration minutes;

}
