package com.ays.ms.controller.dto.request;

import lombok.Data;

@Data
public class UserRegisterRequest {

    private String email;

    private String password;

    private String repeatPassword;

}
