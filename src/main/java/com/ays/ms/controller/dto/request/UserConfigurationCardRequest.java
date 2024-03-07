package com.ays.ms.controller.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @Size(min = 3, max = 3, message = "El CVV debe tener exactamente 3 caracteres")
    private String cvv;

}
