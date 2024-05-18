package com.ays.ms.controller.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserConfigurationRequest {

    private String name;
    private String lastName;
    private String secondLastName;
    private String dateBirth;
    private MultipartFile img;

}
