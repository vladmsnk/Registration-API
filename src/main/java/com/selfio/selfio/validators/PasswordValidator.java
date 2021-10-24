package com.selfio.selfio.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private Pattern pattern;
    private Matcher matcher;
    private static final String PASSWORD_PATTERN =
            "^[A-Za-z0-9.?!@#$%]+$";
    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(String userPassword, ConstraintValidatorContext constraintValidatorContext) {
        if (userPassword == null) {
            return false;
        } else return validatePassword(userPassword);
    }

    private boolean validatePassword(String userPassword) {
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(userPassword);
        return matcher.matches();
    }
}

