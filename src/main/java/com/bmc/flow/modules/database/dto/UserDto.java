package com.bmc.flow.modules.database.dto;

import io.quarkus.hibernate.reactive.panache.common.ProjectedFieldName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.bmc.flow.modules.utilities.DataUtils.getImage;

/**
 * this class carries user data.
 */
@Getter
@Setter
@RegisterForReflection
public class UserDto {

    private UUID          id;
    private LocalDateTime createdAt;
    private String        avatar;
    @NotNull
    private String        callSign;
    private UUID          departmentId;
    @NotNull
    @Email
    private String        email;
    private Boolean       isActive;
    private UUID          seniorityId;

    public UserDto(final UUID id, final @NotNull String email, final @NotNull String callSign, final String avatar, final Boolean isActive,
                   final LocalDateTime createdAt,
                   @ProjectedFieldName("seniority.id") final UUID seniorityId,
                   @ProjectedFieldName("department.id") final UUID departmentId) {
        this.id           = id;
        this.callSign     = callSign;
        this.email        = email;
        this.avatar       = getImage(avatar, "USER");
        this.isActive     = isActive;
        this.createdAt    = createdAt;
        this.seniorityId  = seniorityId;
        this.departmentId = departmentId;
    }

}
