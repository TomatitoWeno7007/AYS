package com.ays.ms.controller.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserLoginRequest {

    @NotNull
    @NotEmpty
    @Email(message = "Email no válido")
    private String email;

    @NotNull
    @NotEmpty
    private String password;

}
