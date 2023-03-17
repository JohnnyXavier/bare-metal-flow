package com.bmc.flow.modules.database.dto.statistics;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@RegisterForReflection
public class StatsCountDto {

  private String statsName;
  private String statsValue;

}
