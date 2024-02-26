package com.ays.ms.controller.dto.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class UserConfigurationCardRequest {

    @NotEmpty
    @NotNull
    private String cardUser;

    @NotEmpty
    @NotNull
    private String cardNumber;

    @NotEmpty
    @NotNull
    private String expirationDate;

    @NotEmpty
    @NotNull
    @Max(3)
    private String cvv;

}
