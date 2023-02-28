package com.bmc.flow.modules.database.dto;

import io.quarkus.hibernate.reactive.panache.common.ProjectedFieldName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.bmc.flow.modules.utilities.DataUtils.getImage;

@Getter
@Setter
@RegisterForReflection
public class UserDto {

  protected UUID id;

  protected LocalDateTime createdAt;

  private String firstName;

  private String lastName;

  @NotNull
  @Email
  private String email;

  @NotNull
  private String callSign;

  private String avatar;

  private Boolean isActive;

  private UUID seniorityId;

  public UserDto(final UUID id, final String firstName, final String lastName, final String email, final String callSign,
                 final String avatar, final Boolean isActive, final LocalDateTime createdAt,
                 @ProjectedFieldName("seniority.id") final UUID seniorityId) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.callSign = callSign;
    this.email = email;
    this.avatar = getImage(avatar, "USER");
    this.isActive = isActive;
    this.createdAt = createdAt;
    this.seniorityId = seniorityId;
  }

}
