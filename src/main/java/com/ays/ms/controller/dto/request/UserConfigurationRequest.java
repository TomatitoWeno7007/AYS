package com.ays.ms.controller.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserConfigurationRequest {

    private String name;
    private String lastName;
    private String secondLastName;
    private String dateBirth;


}
