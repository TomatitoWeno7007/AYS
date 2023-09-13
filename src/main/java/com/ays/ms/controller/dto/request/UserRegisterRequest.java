package com.ays.ms.controller.dto.request;

import com.ays.ms.annotations.PasswordMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

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
