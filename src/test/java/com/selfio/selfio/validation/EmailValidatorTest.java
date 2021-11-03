package com.selfio.selfio.validation;

import com.selfio.selfio.validators.EmailValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmailValidatorTest {

    private EmailValidator emailValidator = new EmailValidator();

    @Test
    public void shouldValidateEmail() {
        String e1 = "anna@mail.ru";
        String e2 = "vyumoiseenkov@edu.hse.ru";
        String e3 = "vyumoiseenkov1@hse.ru";
        String e4 = "egor.antonov95@yandex.com";
        String e5 = "u.user134@gmail.com";
        String e6 = "inna@rambler.britni.com";

        assertTrue(emailValidator.isValid(e1,null ) &&
                emailValidator.isValid(e2, null) &&
                emailValidator.isValid(e3, null) &&
                emailValidator.isValid(e4, null) &&
                emailValidator.isValid(e5, null) &&
                emailValidator.isValid(e6, null));

        String wrongE1 = "1[]324]@//";
        String wrongE2 = "annamail.ru";
        String wrongE3 = "@mail.com";
        String wrongE4 = "ann@            fd12";
        String wrongE5 = "vyumoiseenkov@edu";
        String wrongE6 = "12345@com";
        String wrongE7 = "@";
        assertFalse(emailValidator.isValid(wrongE1, null) ||
                emailValidator.isValid(wrongE2, null) ||
                emailValidator.isValid(wrongE3, null) ||
                emailValidator.isValid(wrongE3, null) ||
                emailValidator.isValid(wrongE4, null) ||
                emailValidator.isValid(wrongE5, null) ||
                emailValidator.isValid(wrongE6, null) ||
                emailValidator.isValid(wrongE7, null));
    }
}
