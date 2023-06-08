package com.bmc.flow.modules.database.dto.base;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * this class is the basis for all DTOs
 */
@Getter
@Setter
@RegisterForReflection
public abstract class BaseDto {

    @NotNull
    protected UUID          createdBy;
    protected UUID          id;
    protected LocalDateTime createdAt;

}
