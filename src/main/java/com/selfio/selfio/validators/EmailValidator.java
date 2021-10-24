package com.selfio.selfio.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    private Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_PATTERN =
            "^[A-Za-z0-9]+(\\.?[A-Za-z0-9]+)*@[A-Za-z0-9]+" +
            "(\\.?[A-Za-z0-9]+)*\\.([A-Za-z]{2,})$";
    @Override
    public void initialize(ValidEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String userEmail, ConstraintValidatorContext constraintValidatorContext) {
        if (userEmail == null) {
            return false;
        } else return validateEmail(userEmail);
    }

    private boolean validateEmail(String userEmail) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(userEmail);
        return matcher.matches();
    }
}
