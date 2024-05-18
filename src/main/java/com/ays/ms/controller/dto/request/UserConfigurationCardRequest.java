package com.ays.ms.controller.dto.request;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class UserConfigurationCardRequest {

    @NotEmpty
    @NotNull
    private String cardUser;

    @NotEmpty
    @NotNull
    @Size(min = 13, max = 19, message = "El numero de la tarjeta tiene como mínimo 13 caracteres y como máximo 19")
    @Pattern(regexp="[0-9]+", message="El número de la tarjeta debe tener solo números")
    private String cardNumber;

    @NotEmpty
    @NotNull
    private String expirationDate;

    @NotEmpty
    @NotNull
    @Size(min = 3, max = 3, message = "El CVV debe tener exactamente 3 caracteres")
    @Pattern(regexp="[0-9]+", message="El CVV debe tener solo números")
    private String cvv;

}
