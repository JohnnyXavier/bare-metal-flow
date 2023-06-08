package com.bmc.flow.modules.database.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Getter
@Setter
@RegisterForReflection
public class UserRegistrationDto {

    @NotNull
    @Email
    private String email;
    @NotNull
    private String callSign;
    @JsonProperty(access = WRITE_ONLY)
    private String password;
    @JsonProperty(access = WRITE_ONLY)
    private String passwordCheck;

    public UserRegistrationDto(final @NotNull String email, final @NotNull String callSign, final String password,
                               final String passwordCheck) {

        this.callSign      = callSign;
        this.email         = email;
        this.password      = password;
        this.passwordCheck = passwordCheck;
    }

}
