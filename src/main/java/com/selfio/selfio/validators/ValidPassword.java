package com.selfio.selfio.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordValidator.class)
public @interface ValidPassword {
    String message() default "invalid password";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
