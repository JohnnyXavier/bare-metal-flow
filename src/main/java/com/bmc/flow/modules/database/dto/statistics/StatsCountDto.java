package com.bmc.flow.modules.database.dto.statistics;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

/**
 * this class carries stats count data.
 * <p><br>
 * this class is a testing class for some front end access, will be deleted in a future, do not use elsewhere
 */
@Getter
@Setter
@RegisterForReflection
public class StatsCountDto {

    private String statsName;
    private String statsValue;

}
