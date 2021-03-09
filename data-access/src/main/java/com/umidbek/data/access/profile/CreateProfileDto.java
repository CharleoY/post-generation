package com.umidbek.data.access.profile;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data

public class CreateProfileDto {

    @NotNull
    private String username;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String reEnterPassword;
}
