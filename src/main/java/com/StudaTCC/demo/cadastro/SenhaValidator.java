package com.StudaTCC.demo.cadastro;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SenhaValidator implements ConstraintValidator<SenhaValidada, String> {

    public static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{6,32}$";

    @Override
    public void initialize(SenhaValidada constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String senha, ConstraintValidatorContext context) {
        if (senha != null) {
            Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
            Matcher matcher = pattern.matcher(senha);
            return matcher.matches();
        }
        return false;
    }
}
