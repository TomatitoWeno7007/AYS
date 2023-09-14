package com.ays.ms.configuration.annotations.validators;

import com.ays.ms.configuration.annotations.PasswordMatch;
import com.ays.ms.controller.dto.request.UserRegisterRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, UserRegisterRequest> {

    @Override
    public void initialize(PasswordMatch p) {}
    @Override
    public boolean isValid(UserRegisterRequest user, ConstraintValidatorContext context) {
        String plainPassword = user.getPassword();
        String repeatPassword = user.getRepeatPassword();

        if(plainPassword == null || !plainPassword.equals(repeatPassword)) {
            return false;
        }

        return true;
    }
}
