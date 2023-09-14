package com.ays.ms.controller;

import com.ays.ms.exceptions.AuthenticationAYSException;
import com.ays.ms.exceptions.ResourceNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandleController {

    @ExceptionHandler({AuthenticationAYSException.class})
    public String handleAuthenticationAYSException(
            AuthenticationAYSException authenticationAYSException) {

        return authenticationAYSException.getMessage();
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public String handleResourceNotFoundException(
            ResourceNotFoundException resourceNotFoundException) {

        return resourceNotFoundException.getMessage();
    }


}
