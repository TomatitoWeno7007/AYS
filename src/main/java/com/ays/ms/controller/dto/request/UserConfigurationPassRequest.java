package com.ays.ms.controller.dto.request;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserConfigurationPassRequest {

    @NotNull
    @NotEmpty
    private String oldPass;

    @NotNull
    @NotEmpty
    private String pass;

    @NotNull
    @NotEmpty
    private String repeatPass;

}
