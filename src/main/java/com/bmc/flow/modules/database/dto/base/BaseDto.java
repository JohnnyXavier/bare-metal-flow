package com.bmc.flow.modules.database.dto.base;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@RegisterForReflection
public abstract class BaseDto {

  protected UUID id;

  protected LocalDateTime createdAt;

  @NotNull
  protected UUID createdBy;

}
