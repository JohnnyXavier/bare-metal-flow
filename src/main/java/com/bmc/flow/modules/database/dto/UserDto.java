package com.bmc.flow.modules.database.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.reactive.panache.common.ProjectedFieldName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.bmc.flow.modules.utilities.DataUtils.getImage;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Getter
@Setter
@RegisterForReflection
public class UserDto {

  protected UUID id;

  protected LocalDateTime createdAt;

  @NotNull
  @Email
  private String email;
  @NotNull
  private String callSign;

  private String avatar;

  private Boolean isActive;

  private UUID seniorityId;

    @JsonProperty(access = WRITE_ONLY)
    private String password;

  public UserDto(final UUID id, final String email, final String callSign,
                 final String avatar, final Boolean isActive, final LocalDateTime createdAt,
                 @ProjectedFieldName("seniority.id") final UUID seniorityId, final String password) {
    this.id = id;
    this.callSign = callSign;
    this.email = email;
    this.avatar = getImage(avatar, "USER");
    this.isActive = isActive;
    this.createdAt = createdAt;
    this.seniorityId = seniorityId;
    this.password=password;
  }

}
