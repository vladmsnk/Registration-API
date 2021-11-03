package com.selfio.selfio.validation;

import com.selfio.selfio.validators.PasswordValidator;
import com.selfio.selfio.validators.ValidPassword;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class PasswordValidatorTest {

    private final PasswordValidator passwordValidator = new PasswordValidator();

    @Test
    public void shouldValidatePassword() {

        String p1 = "123AbcHkf32";
        String p2 = "123456";
        String p3 = "abcdef";
        String p4 = "BDDBHFHF";
        String p5 = ".a?b!c@1#2$%4";
        String p6 = "Hje12345Ejk123";

        assertTrue(passwordValidator.isValid(p1,null ) &&
                passwordValidator.isValid(p2, null) &&
                passwordValidator.isValid(p3, null) &&
                passwordValidator.isValid(p4, null) &&
                passwordValidator.isValid(p5, null) &&
                passwordValidator.isValid(p6, null));
        ;

        String wrongP1 = "1[]324]//";
        String wrongP2 = "{Helloqwert12134OOM";
        String wrongP3 = ",1234";
        String wrongP4 = "/a            fd12";
        String wrongP5 = "1234 55";
        String wrongP6 = "&abcdef*****32323";
        String wrongP7 = "";
        assertFalse(passwordValidator.isValid(wrongP1, null) ||
                passwordValidator.isValid(wrongP2, null) ||
                passwordValidator.isValid(wrongP3, null) ||
                passwordValidator.isValid(wrongP3, null) ||
                passwordValidator.isValid(wrongP4, null) ||
                passwordValidator.isValid(wrongP5, null) ||
                passwordValidator.isValid(wrongP6, null) ||
                passwordValidator.isValid(wrongP7, null));
    }

}
