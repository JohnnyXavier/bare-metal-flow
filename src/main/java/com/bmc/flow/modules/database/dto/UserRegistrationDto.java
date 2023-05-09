package com.bmc.flow.modules.database.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Getter
@Setter
@RegisterForReflection
public class UserRegistrationDto {
  @NotNull
  @Email
  private final String email;
  @NotNull
  private final String callSign;
  @JsonProperty(access = WRITE_ONLY)
  private final String password;
  @JsonProperty(access = WRITE_ONLY)
  private final String passwordCheck;

  public UserRegistrationDto(final String email, final String callSign, final String password, final String passwordCheck) {

    this.callSign      = callSign;
    this.email         = email;
    this.password      = password;
    this.passwordCheck = passwordCheck;
  }

}
