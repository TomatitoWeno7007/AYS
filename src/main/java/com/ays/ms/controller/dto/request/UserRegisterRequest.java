package com.ays.ms.controller.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserRegisterRequest {

    @NotNull
    @NotEmpty
    @Email(message = "Email no válido")
    private String email;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String repeatPassword;

}
