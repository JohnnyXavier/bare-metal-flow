package com.bmc.flow.modules.database.dto.base;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * this class is the basis for all catalog related DTOs
 */
@Getter
@Setter
@RegisterForReflection
public abstract class BaseCatalogDto extends BaseDto {

    @NotNull
    protected String  name;
    protected Boolean isSystem;
    protected String  description;

}
