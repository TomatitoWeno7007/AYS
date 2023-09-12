package com.ays.ms.controller.dto.request;

import lombok.Data;

@Data
public class UserLoginRequest {

    private String email;

    private String password;

}
