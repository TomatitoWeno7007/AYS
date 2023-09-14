package com.ays.ms.controller;

import com.ays.ms.exceptions.AuthenticationAYSException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandleController {

    @ExceptionHandler({AuthenticationAYSException.class})
    public String handleResourceNotFoundException(
            AuthenticationAYSException authenticationAYSException) {

        return authenticationAYSException.getMessage();
    }


}
