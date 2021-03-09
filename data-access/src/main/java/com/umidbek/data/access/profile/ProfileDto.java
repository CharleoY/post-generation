package com.umidbek.data.access.profile;

import com.umidbek.data.access.enums.ProfileRole;
import com.umidbek.data.access.validator.annotation.CreateProfileValidation;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class ProfileDto {

    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    @NotNull
    private String username;

    private ProfileRole role;

    @NotNull
    private String email;

    @NotNull
    private String password;
}
