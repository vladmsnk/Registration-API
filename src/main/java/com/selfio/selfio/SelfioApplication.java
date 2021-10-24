package com.selfio.selfio;

import com.selfio.selfio.entities.transfer.AuthUserDataObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.validation.*;

import java.util.Arrays;

@SpringBootApplication
public class SelfioApplication {

    public static void main(String[] args) {
        AuthUserDataObject user1 = new AuthUserDataObject("1111", "111111");
        AuthUserDataObject user2 = new AuthUserDataObject(null, "");
        AuthUserDataObject user3 = new AuthUserDataObject("dssamsonov@edu.hse.ru", "111");
        System.out.println(Validation.buildDefaultValidatorFactory().getValidator().validate(user1).size());
        System.out.println(Validation.buildDefaultValidatorFactory().getValidator().validate(user2).size());
        System.out.println(Validation.buildDefaultValidatorFactory().getValidator().validate(user3).size());
        SpringApplication.run(SelfioApplication.class, args);
    }
}
